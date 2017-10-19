package me.threebears.news.di.module;

import dagger.Module;
import dagger.Provides;
import me.threebears.news.contract.GankContract;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */
@Module
public class GankModule {

    private GankContract.View mView;

    public GankModule(GankContract.View view) {
        mView = view;
    }

    @Provides
    public GankContract.View provideView() {
        return mView;
    }
}
