package com.example.xh;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.xh.permission.PermissionTestActivity;
import com.example.xh.ui.BaiduLocationFragment;
import com.example.xh.ui.BaseActivity;
import com.example.xh.ui.GreenDaoFragment;
import com.example.xh.ui.RecycleViewFragment;
import com.example.xh.ui.RetrofitFragment;
import com.example.xh.ui.TestRxJavaFragment;
import com.example.xh.ui.UpLoadFileFragment;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigation_view;
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("APP");
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(MainActivity.this, "11111111", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(MainActivity.this, "111122221", Toast.LENGTH_LONG).show();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        navigation_view.setItemIconTintList(null);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectNavigation(0);
                        break;
                    case R.id.navigation_cal:
                        selectNavigation(1);
                        break;
                    case R.id.navigation_about:
                        selectNavigation(2);
                        break;
                    case R.id.navigation_setting:
                        selectNavigation(3);
                        break;
                    case R.id.navigation_greendao:
                        selectNavigation(4);
                        break;
                    case R.id.navigation_recycleview:
                        selectNavigation(5);
                        break;
                    case R.id.navigation_permission:
                        startActivity(new Intent(MainActivity.this, PermissionTestActivity.class));
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        selectNavigation(0);
    }

    public void selectNavigation(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new UpLoadFileFragment();
                break;
            case 1:
                fragment = new TestRxJavaFragment();
                break;
            case 2:
                fragment = new RetrofitFragment();
                break;
            case 3:
                fragment = new BaiduLocationFragment();
                break;
            case 4:
                fragment = new GreenDaoFragment();
                break;
            case 5:
                fragment = new RecycleViewFragment();
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();
            setTitle("文件上传");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
