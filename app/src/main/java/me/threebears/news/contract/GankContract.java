package me.threebears.news.contract;

import java.util.List;

import me.threebears.news.model.entity.GankEntity;
import me.threebears.news.ui.view.BaseView;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

public interface GankContract {

    interface View extends BaseView {

        /**
         * 成功
         *
         * @param list 集合
         */
        void onResultGankList(List<GankEntity> list);

        /**
         * 失败
         *
         * @param errorMsg 错误描述
         */
        void onFailed(String errorMsg);
    }


    interface Presenter {

        /**
         * 请求 gank.io list
         *
         * @param category 类别
         * @param count    数量
         * @param page     页码
         */
        void getGankList(String category, int count, int page);

    }

}
