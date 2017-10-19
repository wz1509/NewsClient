package me.threebears.news.contract;

import java.util.List;

import me.threebears.news.model.entity.NewsEntity;
import me.threebears.news.ui.view.BaseView;

/**
 * Created by threebears on 2017/10/11.
 * @author threebears
 */

public interface NewsContract {

    interface View extends BaseView {

        /**
         * 请求成功
         * @param list 集合
         */
        void onResultListNews(List<NewsEntity> list);

        /**
         * 失败
         * @param errorMsg 描述
         */
        void onFailed(String errorMsg);
    }

    interface Presenter {

        /**
         * 请求新闻list
         * @param category 类别
         * @param count 数量
         * @param page 页码
         */
        void requestListNews(String category, int count, int page);

    }

}
