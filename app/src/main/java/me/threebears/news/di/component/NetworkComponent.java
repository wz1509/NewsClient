package me.threebears.news.di.component;

import dagger.Component;
import me.threebears.news.di.module.NetworkModule;
import me.threebears.news.model.network.GankService;
import me.threebears.news.model.network.NewsService;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    GankService getGankService();

    NewsService getNewsService();

}