package me.threebears.news.model.network;

import io.reactivex.Observable;
import me.threebears.news.model.entity.GankEntity;
import me.threebears.news.model.entity.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

public interface GankService {

    /**
     * api gank.io
     *
     * @param category 类型
     * @param count    数量
     * @param page     页码
     * @return 列表
     */
    @GET("data/{category}/{count}/{page}")
    Observable<HttpResult<GankEntity>> getGankList(@Path("category") String category,
                                                         @Path("count") int count,
                                                         @Path("page") int page);

}
