
        (function(){
           var m = document.getElementById("mobile");
           m.innerHTML=document.cookie+new Date().getTime();
        })()

        function gotoLogin(){

        window.WebViewJavascriptBridge.callHandler(
                        'gotologin'
                        , {}
                        , function(responseData) {
                            //document.getElementById("show").innerHTML = "send get responseData from java, data = " + responseData
                        }
                    );
        }

        function submitClick() {
            var str1 = document.getElementById("username").value;
            var str2 = document.getElementById("pwd").value;

            //call native method
            window.WebViewJavascriptBridge.callHandler(
                'submitFromWeb'
                , {'username': str1,'pwd':str2}
                , function(responseData) {
                    document.getElementById("show").innerHTML = "send get responseData from java, data = " + responseData
                }
            );
        }

        function connectWebViewJavascriptBridge(callback) {
            if (window.WebViewJavascriptBridge) {
                callback(WebViewJavascriptBridge)
            } else {
                document.addEventListener(
                    'WebViewJavascriptBridgeReady'
                    , function() {
                        callback(WebViewJavascriptBridge)
                    },
                    false
                );
            }
        }
         connectWebViewJavascriptBridge(function(bridge) {
            bridge.init(function(message, responseCallback) {
                console.log('JS got a message', message);
                var data = {
                    'Javascript Responds': '测试中文!'
                };

                if (responseCallback) {
                    console.log('JS responding with', data);
                    responseCallback(data);
                }
            });

            bridge.registerHandler("functionInJs", function(data, responseCallback) {
                document.getElementById("show").innerHTML = ("data from Java: = " + data);
                if (responseCallback) {
                    var responseData = "Javascript Says Right back aka!";
                    responseCallback(responseData);
                }
            });
        })