package com.audio.administrator.ganhuo.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/17
 * Description 拦截 token 失效
 */
class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        /*val chainRequest = chain.request()
        // 在 请求失败的时候已经被 RxJava 拦截掉了，所以无法获取到 chainResponse 的响应值
        val chainResponse = chain.proceed(chainRequest)
        val body = chainResponse.body()
        val bodyStr = body?.string()
        bodyStr?.let {
            val jsonObject = JSONObject(bodyStr)
            val status = jsonObject.getInt("status")
            if (status == Constant.NET.TOKEN_FAILED) {
                // token 失效
                EventManager.event.tokenFailedSend()
            }
        }

        // body 读取一次后就会 closed 掉，所以需要新创建一个
        return chainResponse.newBuilder()
                .body(ResponseBody.create(MediaType.parse("UTF-8"), bodyStr))
                .build()*/
        return chain.proceed(chain.request().newBuilder().build())
    }

}