package me.threebears.news.di.component;

import dagger.Component;
import me.threebears.news.di.module.GankModule;
import me.threebears.news.ui.fragment.GankFragment;
import me.threebears.news.ui.fragment.GankGirlFragment;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */
@Component(modules = GankModule.class, dependencies = NetworkComponent.class)
public interface GankComponent {

    void inject(GankFragment fragment);

    void inject(GankGirlFragment fragment);
}
