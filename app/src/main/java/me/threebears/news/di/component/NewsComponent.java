package me.threebears.news.di.component;

import dagger.Component;
import me.threebears.news.di.module.NewsModule;
import me.threebears.news.ui.fragment.NewsFragment;
import me.threebears.news.ui.fragment.PhotoFragment;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */
@Component(modules = NewsModule.class, dependencies = NetworkComponent.class)
public interface NewsComponent {

    /**
     * dagger2注入到fragment
     * @param fragment 新闻fragment
     */
    void inject(NewsFragment fragment);

    /**
     * dagger2注入到fragment
     * @param fragment 图片fragment
     */
    void inject(PhotoFragment fragment);
}
