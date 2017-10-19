package me.threebears.news.di.module;

import dagger.Module;
import dagger.Provides;
import me.threebears.news.contract.NewsContract;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */
@Module
public class NewsModule {

    private NewsContract.View mView;

    public NewsModule(NewsContract.View view) {
        mView = view;
    }

    @Provides
    public NewsContract.View provideView() {
        return mView;
    }
}
