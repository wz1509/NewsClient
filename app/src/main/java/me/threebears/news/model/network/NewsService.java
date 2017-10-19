package me.threebears.news.model.network;

import android.support.v4.util.ArrayMap;

import io.reactivex.Observable;
import me.threebears.news.model.entity.HttpResultEntity;
import me.threebears.news.model.entity.NewsEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by threebears on 2017/10/11.
 * api url
 *
 * @author threebears
 */

public interface NewsService {


//    @GET("{category}/?key=" + ConfigConstant.API_KEY + "&num={count}&page={page}")
//    Observable<HttpResultEntity<NewsEntity>> listNews(@Query("category") String category,
//                                                      @Query("count") int count,
//                                                      @Query("page") int page);

    @GET("{category}")
    Observable<HttpResultEntity<NewsEntity>> listNews(@Path("category") String category,
                                                      @QueryMap ArrayMap<String, Object> arrayMap);

}
