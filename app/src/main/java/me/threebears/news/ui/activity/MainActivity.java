package me.threebears.news.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import butterknife.BindView;
import me.threebears.news.R;
import me.threebears.news.ui.fragment.GankGirlFragment;
import me.threebears.news.ui.fragment.GankTabFragment;
import me.threebears.news.ui.fragment.NewsTabFragment;
import me.threebears.news.ui.fragment.PhotoFragment;

/**
 * @author threeBears
 */
public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Fragment mNewsTabFragment, mPhotoFragment, mGankTabFragment, mGankGirlFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    localLayoutParams.flags);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.drawer_hot_news);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setDefaultFragment(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hot_news) {
            setDefaultFragment(0);
        } else if (id == R.id.nav_day_image) {
            setDefaultFragment(1);
        } else if (id == R.id.nav_gank_io) {
            setDefaultFragment(2);
        } else if (id == R.id.nav_gank_io_girl) {
            setDefaultFragment(3);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        mToolbar.setTitle(item.getTitle());
        return true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (mNewsTabFragment == null && fragment instanceof NewsTabFragment) {
            mNewsTabFragment = fragment;
        }
        if (mPhotoFragment == null && fragment instanceof PhotoFragment) {
            mPhotoFragment = fragment;
        }
        if (mGankTabFragment == null && fragment instanceof GankTabFragment) {
            mGankTabFragment = fragment;
        }
        if (mGankGirlFragment == null && fragment instanceof GankGirlFragment) {
            mGankGirlFragment = fragment;
        }
    }

    /**
     * 设置默认的Fragment
     *
     * @param index 选项卡的标号：id
     */
    private void setDefaultFragment(int index) {
        //开启事务
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
                if (mNewsTabFragment == null) {
                    mNewsTabFragment = new NewsTabFragment();
                    fragmentTransaction.add(R.id.fragment_content, mNewsTabFragment);
                } else {
                    // 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(mNewsTabFragment);
                }
                break;
            case 1:
                if (mPhotoFragment == null) {
                    mPhotoFragment = PhotoFragment.newDayImageFragment();
                    fragmentTransaction.add(R.id.fragment_content, mPhotoFragment);
                } else {
                    fragmentTransaction.show(mPhotoFragment);
                }
                break;
            case 2:
                if (mGankTabFragment == null) {
                    mGankTabFragment = new GankTabFragment();
                    fragmentTransaction.add(R.id.fragment_content, mGankTabFragment);
                } else {
                    fragmentTransaction.show(mGankTabFragment);
                }
                break;
            case 3:
                if (mGankGirlFragment == null) {
                    mGankGirlFragment = new GankGirlFragment();
                    fragmentTransaction.add(R.id.fragment_content, mGankGirlFragment);
                } else {
                    fragmentTransaction.show(mGankGirlFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();   // 事务提交
    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction 事务
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mNewsTabFragment != null) {
            fragmentTransaction.hide(mNewsTabFragment);
        }

        if (mPhotoFragment != null) {
            fragmentTransaction.hide(mPhotoFragment);
        }

        if (mGankTabFragment != null) {
            fragmentTransaction.hide(mGankTabFragment);
        }

        if (mGankGirlFragment != null) {
            fragmentTransaction.hide(mGankGirlFragment);
        }
    }


}
