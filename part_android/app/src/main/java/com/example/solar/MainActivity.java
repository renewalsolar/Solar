package com.example.solar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.solar.Models.UserInfo;
import com.example.solar.fragment.DontHasPV;
import com.example.solar.fragment.HasPV;
import com.example.solar.tabPager.CustomViewPager;
import com.example.solar.tabPager.PagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private UserInfo user;

    private CustomViewPager viewPager;
    private TabLayout tabLayout;


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user = (UserInfo)intent.getSerializableExtra("USER_INFO");



        setupToolBarMenu();
        setupNavigationDrawerMenu();

        setupViewPager();
       // displayMenu();
    }

    private void setupToolBarMenu(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Navigation View");
        Log.e("ERRRRRR","SETNAVU");
    }

    private void setupNavigationDrawerMenu(){
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setupViewPager() {
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        if(user.isHasPV()){
            adapter.addFragment(new HasPV());
        }
        else{
            adapter.addFragment(new DontHasPV());
        }

        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(10);
    }


//    private void displayMenu() {
//
//        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        tabLayout.addTab(tabLayout.newTab().setText("계산도우미"));
//        tabLayout.getTabAt(0).setIcon(R.drawable.icon_calc);
//        tabLayout.addTab(tabLayout.newTab().setText("친환경도우미"));
//        tabLayout.getTabAt(1).setIcon(R.drawable.icon_green);
//
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }

}
