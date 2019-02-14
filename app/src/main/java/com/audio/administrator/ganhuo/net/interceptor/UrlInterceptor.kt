package com.audio.administrator.ganhuo.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/23
 * Description
 */
class UrlInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        return chain.proceed(request)
    }
}