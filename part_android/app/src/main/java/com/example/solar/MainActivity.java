package com.example.solar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.solar.Models.UserInfo;
import com.example.solar.SubThread.PersonalGeneration;
import com.example.solar.fragment.DontHasPV;
import com.example.solar.fragment.HasPV;
import com.example.solar.pannelManage.PersonnalActivity;
import com.example.solar.pannelManage.RegisterActivity;
import com.example.solar.personManage.DeleteFunc;
import com.example.solar.personManage.EditActivity;
import com.example.solar.personManage.LoginActivity;
import com.example.solar.tabPager.CustomViewPager;
import com.example.solar.tabPager.PagerAdapter;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private UserInfo user;

    private CustomViewPager viewPager;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private View headerView;
    private TextView nave_tv_name;

    private  Fragment fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user = (UserInfo) intent.getSerializableExtra("USER_INFO");

        setupToolBarMenu();
        setupNavigationDrawerMenu();

        setupViewPager();

//        if (((LocationManager) this.getSystemService(LOCATION_SERVICE))
//                .isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
//            showSettingsAlert();
//        } // 사용자에게 GPS 활성화 요청, 밑에 깔림
        //getLocationPermission();
        // 사용자에게 위치 권한 요청. 위에 보임
    }

    private void setupToolBarMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
    }

    private void setupNavigationDrawerMenu() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);

        nave_tv_name = headerView.findViewById(R.id.navi_tv_name);
        nave_tv_name.setText(user.getName() + " 님");

        navigationView.getMenu().clear();

        if (user.getId().equals("NONE"))
            navigationView.inflateMenu(R.menu.nonregister_menu);
        else
            navigationView.inflateMenu(R.menu.register_menu);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        String itemName = (String) menuItem.getTitle();

        closeDrawer();

        switch (menuItem.getItemId()) {
            case R.id.menu_pannel_create:
                Intent i1 = new Intent(this, RegisterActivity.class);
                i1.putExtra("USER_INFO", user);
                startActivity(i1);
                break;

            case R.id.menu_pannel_read:
                Intent i2 = new Intent(this, PersonnalActivity.class);
                i2.putExtra("USER_INFO", user);
                startActivity(i2);
                break;

            case R.id.menu_user_logout:
                if(fa != null) {
                    Log.e("HERELOGERR" , fa.getClass().toString());
                    ((HasPV) fa).threadStop();
                }
                Intent i3 = new Intent(this, LoginActivity.class);
                startActivity(i3);
                finish();
                break;

            case R.id.menu_user_update:
                Intent i4 = new Intent(this, EditActivity.class);
                i4.putExtra("USER_INFO", user);
                startActivityForResult(i4, 1006);
                break;

            case R.id.menu_user_unregister:
                if(fa != null)
                    ((HasPV) fa).threadStop();
                new DeleteFunc(this, user.getId());
                Intent i5 = new Intent(this, LoginActivity.class);
                startActivity(i5);
                finish();
                break;

            case R.id.menu_user_register:
                Intent i6 = new Intent(this, LoginActivity.class);
                startActivity(i6);
                finish();
                break;
        }

        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void showDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }

    private void setupViewPager() {
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        if (user.isHasPV()) {
            fa = new HasPV(user);
            adapter.addFragment(fa);
        } else {
            adapter.addFragment(new DontHasPV());
        }

        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1006 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("NAME");

            user.setName(name);
            nave_tv_name.setText(user.getName() + " 님");
        }
    }

}
