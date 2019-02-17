package com.audio.administrator.ganhuo.net.config;


import com.audio.administrator.ganhuo.config.Config;
import com.audio.administrator.ganhuo.net.api.Api;
import com.audio.administrator.ganhuo.net.interceptor.AuthorizationInterceptor;
import com.audio.administrator.ganhuo.net.interceptor.ResponseInterceptor;
import com.audio.administrator.ganhuo.net.interceptor.UrlInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by Chris on 2018/4/18.
 */

public class HttpManager {

    private String retrofitUrl = "";
    private static final Boolean IS_TOKEN = false;
    private static final String GAN_HUO = Api.class.getSimpleName();

    public interface Tag {
        String GAN_HUO = "GAN_HUO";
    }

    private interface IP {

    }

    private interface Port {

    }

    private interface DoMain {
        String GAN_HUO_IP = "http://gank.io";
    }

    public interface Interfaces {
        String ANDROID = "/api/data/Android";
        String IOS = "/api/data/iOS";
        String WELFARE = "api/data/福利";
        String VIDEO = "/api/data/休息视频";
    }

    public interface Url {
        String GAN_HUO_DEBUG = DoMain.GAN_HUO_IP;
        String GAN_HUO_RELEASE = DoMain.GAN_HUO_IP;
    }


    public interface Apis {
        String API = GAN_HUO;
    }

    public <T> T get(Class<T> clazz) {
        return get(clazz, IS_TOKEN);
    }

    public <T> T get(Class<T> clazz, boolean isToken) {
        return get(clazz, Tag.GAN_HUO, isToken);
    }

    public <T> T get(Class<T> clazz, String tag) {
        return get(clazz, tag, IS_TOKEN);
    }


    // 可能会有很多不同 的 IP , 请求路径
    public <T> T get(Class<T> clazz, String tag, boolean isAddToken) {
        switch (tag) {
            case Tag.GAN_HUO:
                if (Config.isDebug) {
                    // 测试版地址/api/data/Android   /api/data/Android
                    retrofitUrl = Url.GAN_HUO_DEBUG;
                } else {
                    // 发布版的地址
                    retrofitUrl = Url.GAN_HUO_RELEASE;
                }
                break;
        }
        if (isAddToken) {
            return getBaseManager(retrofitUrl, true).create(clazz);
        } else {
            return getBaseManager(retrofitUrl, false).create(clazz);
        }
    }


    public <T> T post(Class<T> clazz) {
        return post(clazz, true);
    }

    public <T> T post(Class<T> clazz, boolean isToken) {
        return post(clazz, Tag.GAN_HUO, isToken);
    }

    public <T> T post(Class<T> clazz, String tag) {
        return post(clazz, tag, false);
    }


    public <T> T post(Class<T> clazz, String tag, boolean isAddToken) {

        switch (tag) {
            case Tag.GAN_HUO:
                if (Config.isDebug) {
                    retrofitUrl = Url.GAN_HUO_DEBUG;
                } else {
                    retrofitUrl = Url.GAN_HUO_RELEASE;
                }
                break;
        }
        if (isAddToken) {
            return getBaseManager(retrofitUrl, true).create(clazz);
        } else {
            return getBaseManager(retrofitUrl, false).create(clazz);
        }
    }

    public Retrofit getBaseManager(String baseUrl, boolean isToken) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (isToken) {
            builder.addInterceptor(new AuthorizationInterceptor());     // 添加 token
            builder.addInterceptor(new ResponseInterceptor());          // 检测 token 失效
        }
        builder.addInterceptor(new UrlInterceptor());
        OkHttpClient build = builder.build();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        return getBaseManager(baseUrl, build);
    }

    /**
     * 默认添加 token
     *
     * @param baseUrl
     * @return
     */
    private Retrofit getBaseManager(String baseUrl) {
        return getBaseManager(baseUrl, true);
    }


    private Retrofit getBaseManager(String baseUrl, OkHttpClient client) {
        return setBaseUrl(baseUrl)
                .client(client)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private Retrofit.Builder getBaseRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    private Retrofit.Builder setBaseUrl(String baseUrl) {
        return getBaseRetrofitBuilder().baseUrl(baseUrl);
    }

    private HttpManager() {

    }


    private static class HttpManagerInstance {
        private static HttpManager manager = new HttpManager();
    }

    public static HttpManager request() {
        return HttpManagerInstance.manager;
    }


    public String getBaseUrl(){
        return retrofitUrl;
    }
}
