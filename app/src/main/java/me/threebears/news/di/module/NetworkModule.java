package me.threebears.news.di.module;

import dagger.Module;
import dagger.Provides;
import me.threebears.news.model.network.GankService;
import me.threebears.news.model.network.NewsService;
import me.threebears.news.model.network.RetrofitClient;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

@Module
public class NetworkModule {

    @Provides
    public GankService provideGankService() {
        return RetrofitClient.getGankService();
    }


    @Provides
    public NewsService provideNewsService() {
        return RetrofitClient.getNewsService();
    }
}
