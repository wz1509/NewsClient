package me.threebears.news.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import me.threebears.news.ui.activity.PhotoDetailActivity;
import me.threebears.news.ui.adapter.PhotoAdapter;

/**
 * Created time 2017/10/12.
 *
 * @author threeBears
 * @describe 每日看图
 */

public class PhotoFragment extends BaseFragment implements NewsContract.View {

    private final static String CATEGORY = "meinv";
    private final static int COUNT = 15;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private boolean isRefresh;
    private boolean isLoadMore;
    private int page = 1;

    @Inject
    NewsPresenter mPresenter;
    private PhotoAdapter mAdapter;

    public static PhotoFragment newDayImageFragment() {
        return new PhotoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh_rv;
    }

    @Override
    protected void initData(ViewGroup container, Bundle savedInstanceState) {
        page = 1;
        isRefresh = true;
        isLoadMore = false;

        initPresenter();
        initRecyclerView();

        mPresenter.requestListNews(CATEGORY, COUNT, page);
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
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                mPresenter.requestListNews(CATEGORY, COUNT, page);
            }
        });
        mAdapter = new PhotoAdapter(getActivity());
        mAdapter.setOnReloadClickListener(new OnReloadClickListener() {
            @Override
            public void onClick() {
                mAdapter.setLoading();
                mPresenter.requestListNews(CATEGORY, COUNT, page);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<NewsEntity>() {
            @Override
            public void onItemClick(View view, NewsEntity data) {
                ImageView imageView = view.findViewById(R.id.photo_item_image);
                Intent intent = PhotoDetailActivity.newIntent(getActivity(), data.getTitle(), data.getPicUrl());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                            getActivity(), imageView, getString(R.string.transition_photos));
                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                } else {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
                            imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                }
            }
        });
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int[] lastVisibleItemPosition = ((StaggeredGridLayoutManager) layoutManager)
                        .findLastVisibleItemPositions(null);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                boolean isMore = !isRefresh && !isLoadMore && visibleItemCount > 0 &&
                        (newState == RecyclerView.SCROLL_STATE_IDLE) &&
                        ((lastVisibleItemPosition[0] >= totalItemCount - 1) ||
                                (lastVisibleItemPosition[1] >= totalItemCount - 1));
                if (isMore) {
                    isLoadMore = true;
                    isRefresh = false;
                    mPresenter.requestListNews(CATEGORY, COUNT, page);
                    mAdapter.setLoading();
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
