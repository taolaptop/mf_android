package com.dilidolo.hi.myfamily;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.dilidolo.hi.library.BridgeHandler;
import com.dilidolo.hi.library.BridgeWebView;
import com.dilidolo.hi.library.CallBackFunction;
import com.google.gson.Gson;

import java.time.Duration;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MyFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    BridgeWebView webview;
    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        webview.reload();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        webview = (BridgeWebView)view.findViewById(R.id.webview);
        //获取webSettings
        WebSettings settings = webview.getSettings();
        //让webView支持JS
        settings.setJavaScriptEnabled(true);
        //加载网页
        //webview.loadUrl("file:///android_asset/my.html");
//        webview.loadUrl("file:///android_asset/demo.html");
        webview.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("MY_Fragment", "handler = submitFromWeb, data from web = " + data);
                Map map = new Gson().fromJson(data, Map.class);
                Toast.makeText(getContext(),"run java method user:"+map.get("username"), Toast.LENGTH_SHORT).show();
                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
            }

        });

        webview.registerHandler("gotologin" ,new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        webview.callHandler("functionInJs", "{\"result\":\"OK\"}", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Toast.makeText(getContext(),data, Toast.LENGTH_SHORT).show();

            }
        });

//        webview.loadUrl("file:///android_asset/my.html");
        webview.loadUrl("http://192.168.3.81:8080");
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
