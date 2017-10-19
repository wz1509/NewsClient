package me.threebears.news.presenter;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.threebears.news.contract.GankContract;
import me.threebears.news.model.entity.GankEntity;
import me.threebears.news.model.entity.HttpResult;
import me.threebears.news.model.network.GankService;
import me.threebears.news.model.network.RxSchedulers;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

public class GankPresenter implements GankContract.Presenter {

    private GankContract.View mView;
    private GankService mService;

    @Inject
    public GankPresenter(GankContract.View view, GankService service) {
        mView = view;
        mService = service;
    }

    @Override
    public void getGankList(String category, int count, int page) {
        mService.getGankList(category, count, page)
                .compose(RxSchedulers.<HttpResult<GankEntity>>io_main())
                .compose(mView.<HttpResult<GankEntity>>bindToLife())
                .subscribe(new Consumer<HttpResult<GankEntity>>() {
                    @Override
                    public void accept(@NonNull HttpResult<GankEntity> result) throws Exception {
                        if (result.isSuccess()) {
                            mView.onResultGankList(result.getResults());
                        } else {
                            mView.onFailed("null");
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
