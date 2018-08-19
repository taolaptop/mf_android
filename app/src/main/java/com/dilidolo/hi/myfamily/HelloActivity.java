package com.dilidolo.hi.myfamily;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

public class HelloActivity extends Activity {

    TextView pass;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=this;
        setContentView(R.layout.activity_hello);
        if (!PermissionsUtil.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    //用户授予了访问摄像头的权限
                    Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                }


                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了访问摄像头的申请
                    Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show();
                }
            }, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});

        }

        FamilyApplication familyApplication =(FamilyApplication) this.getApplication();
//        FamilyApplication familyApplication = (FamilyApplication) this.getApplication();
        if (familyApplication.isWelcome_load()){
//            Intent intent = new Intent(SampleActivity.this, HelloActivity.class);
//            startActivity(intent);
            goToHome();
        }
        familyApplication.setWelcome_load(true);
        pass = findViewById(R.id.pass_tv);
        pass.setOnClickListener((v)->{
            goToHome();
        });
        handler.sendEmptyMessageDelayed(0, 2000);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {

               goToHome();
            }

            //super.handleMessage(msg);
        }
    };

    void goToHome(){
        Intent intent = new Intent(HelloActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
