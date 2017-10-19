package me.threebears.news.model.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by threebears on 2017/10/11.
 *
 * @author threebears
 *         network client
 */

public class RetrofitClient {

    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10 * 1000;

    /**
     * 服务器地址url
     */
    private static final String BASE_URL = "http://api.tianapi.com/";

    private static NewsService sNewsService;

    /**
     * api service -->> gank.io
     */
    private static final String BASE_GANK_URL = "http://gank.io/api/";

    /**
     * gankService对象
     */
    private static GankService mGankApiService;

    public static NewsService getNewsService() {
        if (null == sNewsService) {
            synchronized (RetrofitClient.class) {
                if (null == sNewsService) {
                    //手动创建一个OkHttpClient并设置超时时间
                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
                    //设置超时时间
                    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
                    httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
                    httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
                    sNewsService = new Retrofit.Builder()
                            .client(httpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(BASE_URL)
                            .build()
                            .create(NewsService.class);
                }
            }
        }
        return sNewsService;
    }

    /**
     * 单例方式获取gankService对象
     *
     * @return gankService对象
     */
    public static GankService getGankService() {
        if (mGankApiService == null) {
            synchronized (RetrofitClient.class) {
                if (mGankApiService == null) {
                    //手动创建一个OkHttpClient并设置超时时间
                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
                    //设置超时时间
                    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
                    httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
                    httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
                    mGankApiService = new Retrofit.Builder()
                            .client(httpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(BASE_GANK_URL)
                            .build()
                            .create(GankService.class);
                }
            }
        }
        return mGankApiService;
    }

}
