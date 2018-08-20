package com.dilidolo.hi.myfamily;

import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dilidolo.hi.myfamily.model.ZoneItem;
import com.dilidolo.hi.myfamily.naviPages.MapFragment;
import com.dilidolo.hi.myfamily.naviPages.ZoneFragment;

public class MainActivity extends AppCompatActivity implements MyFragment.OnFragmentInteractionListener, ZoneFragment.OnListFragmentInteractionListener {

    BottomNavigationView bottomNavigationView;
    RelativeLayout map_layout;
    Fragment map_Fragment;
    Fragment zone_Fragment;
    Fragment my_Fragment;


    void init() {
        bottomNavigationView = findViewById(R.id.navigationBar);
        map_layout = findViewById(R.id.fragment_map);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);SYSTEM_UI_FLAG_VISIBLE


        }

        //设置view高度，通过获取 bottom navi bar事件 获取bar 高度， 再用屏幕高度减去bar高度
        ViewTreeObserver viewTreeObserver = bottomNavigationView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottomNavigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this::onGlobalLayout);
                int height = bottomNavigationView.getHeight();
                Display display = getWindowManager().getDefaultDisplay();
//                heigth = display.getWidth();
                Point point = new Point();
                display.getSize(point);
                ViewGroup.LayoutParams params = map_layout.getLayoutParams();
                params.height = point.y - height;
                map_layout.setLayoutParams(params);

                ViewGroup.LayoutParams params1 = findViewById(R.id.fragment_group).getLayoutParams();
                params1.height = point.y - height;
                findViewById(R.id.fragment_group).setLayoutParams(params1);

                ViewGroup.LayoutParams params2 = findViewById(R.id.fragment_my).getLayoutParams();
                params2.height = point.y - height;
                findViewById(R.id.fragment_my).setLayoutParams(params2);

            }
        });
//        viewTreeObserver.addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
//            @Override
//            public void onDraw() {
//                viewTreeObserver.removeOnDrawListener(this);
//                int height = bottomNavigationView.getHeight();
//                ViewGroup.LayoutParams params = map_layout.getLayoutParams();
//                params.height = height;
//                map_layout.setLayoutParams(params);
//            }
//
//
//        });
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        initBtmNav();
    }

    void initBtmNav() {
        if (map_Fragment == null) {
            map_Fragment = new MapFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_map, (Fragment) map_Fragment).commit();
        }
        if (zone_Fragment == null) {
            zone_Fragment = new ZoneFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_group, (Fragment) zone_Fragment).commit();
        }
        if (my_Fragment == null) {
            my_Fragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my, (Fragment) my_Fragment).commit();
        }
        updateFragmentLayoutStatus(R.id.fragment_map);
    }

    BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_1) {
                if (map_Fragment == null) {
                    map_Fragment = new MapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_map, (Fragment) map_Fragment).commit();
                }
                updateFragmentLayoutStatus(R.id.fragment_map);
            } else if (item.getItemId() == R.id.navigation_2) {
                if (zone_Fragment == null) {
                    zone_Fragment = new ZoneFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_group, (Fragment) zone_Fragment).commit();
                }
                updateFragmentLayoutStatus(R.id.fragment_group);

            } else {
                if (my_Fragment == null) {
                    my_Fragment = new MyFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my, (Fragment) my_Fragment).commit();
                }
                updateFragmentLayoutStatus(R.id.fragment_my);

            }

            return true;
        }
    };

    void updateFragmentLayoutStatus(int fragment_id) {
        findViewById(R.id.fragment_map).setVisibility(View.GONE);
        findViewById(R.id.fragment_group).setVisibility(View.GONE);
        findViewById(R.id.fragment_my).setVisibility(View.GONE);
        findViewById(fragment_id).setVisibility(View.VISIBLE);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(ZoneItem.ZoneSubItem item) {

    }
}
