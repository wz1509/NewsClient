package me.threebears.news.ui.view;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by threebears on 2017/10/11.
 */

public interface BaseView {


    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();

}
