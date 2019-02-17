package com.audio.administrator.ganhuo.net.api;

import com.audio.administrator.ganhuo.annotation.CachePath;
import com.audio.administrator.ganhuo.modules.android.bean.EveryDayEntity;
import com.audio.administrator.ganhuo.net.config.HttpManager;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/21
 * Description
 */
public interface Api {

    // http://gank.io/api/data/Android/10/1
    @CachePath()
    @GET(HttpManager.Interfaces.ANDROID + "/{count}/{page}")
    Observable<EveryDayEntity> getAndroid(@Path("count") int count, @Path("page") int page);

    //"http://gank.io/api/data/iOS/$count/$page"
    @GET(HttpManager.Interfaces.IOS + "/{count}/{page}")
    Observable<EveryDayEntity> getIOS(@Path("count") int count, @Path("page") int page);

    //return "http://gank.io/api/data/福利/$count/$page"
    @GET(HttpManager.Interfaces.WELFARE + "/{count}/{page}")
    Observable<EveryDayEntity> getWelfare(@Path("count") int count, @Path("page") int page);

    //return "http://gank.io/api/data/休息视频/$count/$page"
    @GET(HttpManager.Interfaces.VIDEO + "/{count}/{page}")
    Observable<EveryDayEntity> getVideo(@Path("count") int count, @Path("page") int page);

}
