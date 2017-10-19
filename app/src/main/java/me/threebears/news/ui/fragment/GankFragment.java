package me.threebears.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.threebears.news.R;
import me.threebears.news.contract.GankContract;
import me.threebears.news.di.component.DaggerGankComponent;
import me.threebears.news.di.component.DaggerNetworkComponent;
import me.threebears.news.di.module.GankModule;
import me.threebears.news.di.module.NetworkModule;
import me.threebears.news.listener.OnItemClickListener;
import me.threebears.news.listener.OnReloadClickListener;
import me.threebears.news.model.entity.GankEntity;
import me.threebears.news.presenter.GankPresenter;
import me.threebears.news.ui.activity.NewsDetailActivity;
import me.threebears.news.ui.adapter.GankAdapter;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

public class GankFragment extends BaseLazyFragment implements GankContract.View {

    private static final String TAG = "GankFragment";

    private final static String CATEGORY = "category";
    private final static int COUNT = 15;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private boolean isRefresh;
    private boolean isLoadMore;

    private int page = 1;

    private GankAdapter mAdapter;

    @Inject
    GankPresenter mPresenter;

    public static GankFragment newGankFragment(String category) {
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        GankFragment gankFragment = new GankFragment();
        gankFragment.setArguments(args);
        return gankFragment;
    }

    @Override
    protected void initPrepare(@Nullable Bundle savedInstanceState) {
        page = 1;
        isRefresh = true;
        isLoadMore = false;

        initPresenter();
        initRecyclerView();
    }

    private void initPresenter() {
        DaggerGankComponent.builder()
                .networkComponent(DaggerNetworkComponent.builder()
                        .networkModule(new NetworkModule())
                        .build())
                .gankModule(new GankModule(this))
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshLayoutColor));
        // 下拉刷新事件
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mPresenter.getGankList(getCategory(), COUNT, page);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GankAdapter(getContext());
        // 加载更多 异常处理
        mAdapter.setOnReloadClickListener(new OnReloadClickListener() {
            @Override
            public void onClick() {
                mAdapter.setLoading();
                mPresenter.getGankList(getCategory(), COUNT, page);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<GankEntity>() {
            @Override
            public void onItemClick(View view, GankEntity data) {
                NewsDetailActivity.startActivity(getActivity(), data.getDesc(), data.getUrl());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // 滑到底部监听事件
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();
                if (!isRefresh && !isLoadMore && newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItemPosition == totalItemCount - 1 && visibleItemCount > 0) {
                    isLoadMore = true;
                    isRefresh = false;
                    mAdapter.setLoading();
                    mPresenter.getGankList(getCategory(), COUNT, page);
                }
            }
        });
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        mPresenter.getGankList(getCategory(), COUNT, page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh_rv;
    }

    private String getCategory() {
        return getArguments().getString(CATEGORY);
    }

    @Override
    public void onResultGankList(List<GankEntity> list) {
        closeRefreshing();
        if (isRefresh) {
            if (list != null && list.size() > 0) {
                mAdapter.setData(list);
                page++;
            } else {
                Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (list != null && list.size() > 0) {
                Log.d("wz","加载更多");
                mAdapter.addMoreData(list);
                page++;
            } else {
                mAdapter.setNotMore();
            }
        }
        isRefresh = false;
        isLoadMore = false;
    }

    @Override
    public void onFailed(String errorMsg) {
        closeRefreshing();
        if (mAdapter.getItemCount() == 0) {
            Toast.makeText(getContext(), "加载出错：" + errorMsg, Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.setNetError();
        }
    }

    /**
     * 关闭 SwipeRefreshLayout 下拉动画
     */
    private void closeRefreshing() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
