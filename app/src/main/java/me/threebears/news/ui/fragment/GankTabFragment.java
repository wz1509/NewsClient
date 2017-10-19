package me.threebears.news.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.threebears.news.R;
import me.threebears.news.ui.adapter.ViewPagerAdapter;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */
public class GankTabFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initData(ViewGroup container, Bundle savedInstanceState) {

        List<String> categoryList = Arrays.asList("Android", "iOS", "前端", "休息视频", "拓展资源");
        List<Fragment> fragmentList = new ArrayList<>();
        for (String category : categoryList) {
            fragmentList.add(GankFragment.newGankFragment(category));
        }
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), categoryList, fragmentList));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(fragmentList.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
