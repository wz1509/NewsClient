package me.threebears.news.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.threebears.news.R;
import me.threebears.news.contract.NewsContract;
import me.threebears.news.di.component.DaggerNetworkComponent;
import me.threebears.news.di.component.DaggerNewsComponent;
import me.threebears.news.di.module.NetworkModule;
import me.threebears.news.di.module.NewsModule;
import me.threebears.news.listener.OnItemClickListener;
import me.threebears.news.listener.OnReloadClickListener;
import me.threebears.news.model.entity.NewsEntity;
import me.threebears.news.presenter.NewsPresenter;
import me.threebears.news.ui.activity.NewsDetailActivity;
import me.threebears.news.ui.adapter.NewsAdapter;

/**
 * Created by wz on 2017/10/10 19:01.
 *
 * @author threebears
 *         desc: news ui
 */
public class NewsFragment extends BaseLazyFragment implements NewsContract.View {

    private final static String CATEGORY = "category";
    private final static int COUNT = 15;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private boolean isRefresh;
    private boolean isLoadMore;

    private int page = 1;
    private NewsAdapter mAdapter;

    @Inject
    NewsPresenter mPresenter;

    public static NewsFragment newNewsFragment(String category) {
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    protected void initPrepare(@Nullable Bundle savedInstanceState) {

        page = 1;
        isRefresh = true;
        isLoadMore = false;

        initPresenter();
        initRecyclerView();
    }

    @Override
    protected void onInvisible() {
    }

    @Override
    protected void initData() {
        mPresenter.requestListNews(getCategory(), COUNT, page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh_rv;
    }

    private String getCategory() {
        return getArguments().getString(CATEGORY);
    }

    private void initPresenter() {
        DaggerNewsComponent.builder()
                .networkComponent(DaggerNetworkComponent.builder()
                        .networkModule(new NetworkModule())
                        .build())
                .newsModule(new NewsModule(this))
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
                mPresenter.requestListNews(getCategory(), COUNT, page);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NewsAdapter(getContext());
        // 加载更多 异常处理
        mAdapter.setOnReloadClickListener(new OnReloadClickListener() {
            @Override
            public void onClick() {
                mAdapter.setLoading();
                mPresenter.requestListNews(getCategory(), COUNT, page);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<NewsEntity>() {
            @Override
            public void onItemClick(View view, NewsEntity data) {
                NewsDetailActivity.startActivity(getActivity(), data.getTitle(), data.getUrl());
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
                    mPresenter.requestListNews(getCategory(), COUNT, page);
                }
            }
        });
    }

    @Override
    public void onResultListNews(List<NewsEntity> list) {
        closeRefreshing();
        if (isRefresh) {
            if (list != null && list.size() > 0) {
                mAdapter.newDataItem(list);
                page++;
            } else {
                Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (list != null && list.size() > 0) {
                mAdapter.addMoreItem(list);
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
