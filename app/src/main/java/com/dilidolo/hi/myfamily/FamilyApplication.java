package com.dilidolo.hi.myfamily;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class FamilyApplication extends Application {

    boolean welcome_load = false;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public boolean isWelcome_load() {
        return welcome_load;
    }

    public void setWelcome_load(boolean welcome_load) {
        this.welcome_load = welcome_load;
    }
}
