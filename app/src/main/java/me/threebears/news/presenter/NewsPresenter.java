package me.threebears.news.presenter;

import android.support.v4.util.ArrayMap;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.threebears.news.config.ConfigConstant;
import me.threebears.news.contract.NewsContract;
import me.threebears.news.model.entity.HttpResultEntity;
import me.threebears.news.model.entity.NewsEntity;
import me.threebears.news.model.network.NewsService;
import me.threebears.news.model.network.RxSchedulers;

/**
 * Created by threebears on 2017/10/11.
 *
 * @author threebears
 */

public class NewsPresenter implements NewsContract.Presenter {

    private NewsContract.View mView;
    private NewsService mNewsService;

    @Inject
    public NewsPresenter(NewsContract.View view, NewsService newsService) {
        mView = view;
        mNewsService = newsService;
    }

    @Override
    public void requestListNews(String category, int count, int page) {
        ArrayMap<String, Object> arrayMap = new ArrayMap<>();
        arrayMap.put("key", ConfigConstant.API_KEY);
        arrayMap.put("num", count);
        arrayMap.put("page", page);

        mNewsService.listNews(category, arrayMap)
                .compose(RxSchedulers.<HttpResultEntity<NewsEntity>>io_main())
                .compose(mView.<HttpResultEntity<NewsEntity>>bindToLife())
                .subscribe(new Consumer<HttpResultEntity<NewsEntity>>() {
                    @Override
                    public void accept(@NonNull HttpResultEntity<NewsEntity> result) throws Exception {
                        if (result.isSuccess()) {
                            mView.onResultListNews(result.getListNews());
                        } else {
                            mView.onFailed(result.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.onFailed(throwable.getMessage());
                    }
                });
    }

}
