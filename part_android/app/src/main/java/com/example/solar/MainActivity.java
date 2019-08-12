package com.example.solar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.solar.fragment.CalcHelper;
import com.example.solar.fragment.EcoHelper;
import com.example.solar.tabPager.CustomViewPager;
import com.example.solar.tabPager.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
        displayMenu();
    }

    private void setupViewPager() {
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new CalcHelper());
        adapter.addFragment(new EcoHelper());

        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(10);
    }


    private void displayMenu() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tabLayout.addTab(tabLayout.newTab().setText("계산도우미"));
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_calc);
        tabLayout.addTab(tabLayout.newTab().setText("친환경도우미"));
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_green);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
