package com.audio.administrator.ganhuo.utils

object URLUtil{
    /*http://gank.io/api/data/Android/10/1
    http://gank.io/api/data/福利/10/1
    http://gank.io/api/data/iOS/20/2
    http://gank.io/api/data/all/20/2*/

    fun getAndroid(page:Int,count:Int) : String{
        return "http://gank.io/api/data/Android/$count/$page"
    }

    fun getIOS(page:Int,count:Int) : String{
        return "http://gank.io/api/data/IOS/$count/$page"
    }
    fun getWelfare(page:Int,count:Int) : String{
        return "http://gank.io/api/data/福利/$count/$page"
    }

    fun getVideo(page:Int,count:Int) : String{
        return "http://gank.io/api/data/休息视频/$count/$page"
    }

}