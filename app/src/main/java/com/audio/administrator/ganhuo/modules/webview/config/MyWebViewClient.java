package com.audio.administrator.ganhuo.modules.webview.config;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.fastjson.JSONObject;
import com.audio.administrator.ganhuo.modules.webview.WebViewActivity;
import com.audio.administrator.ganhuo.net.config.HttpManager;
import com.audio.administrator.ganhuo.utils.BaseTools;
import com.audio.administrator.ganhuo.utils.NetUtil;

import java.util.Set;


/**
 * Created by jingbin on 2016/11/17.
 * 监听网页链接:
 * - 优酷视频直接跳到自带浏览器
 * - 根据标识:打电话、发短信、发邮件
 * - 进度条的显示
 * - 添加javascript监听
 */
public class MyWebViewClient extends WebViewClient {

    private IWebPageView mIWebPageView;
    private WebViewActivity mActivity;

    public MyWebViewClient(WebViewActivity mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        mActivity =  mIWebPageView;

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        // 解析影像大数据的视频数据
        if (uri.getScheme().equals("fiichat")){
            if (uri.getAuthority().equals("playVideo")){
                // 获取参数
                //Toast.makeText(getApplicationContext(),"JS 调用了 Android 了",Toast.LENGTH_SHORT).show();
                Set<String> args = uri.getQueryParameterNames();
                //ArrayList<String> datas = new ArrayList<>();
                JSONObject jsonObject = new JSONObject();

                for (String arg : args){
                    jsonObject.put(arg,uri.getQueryParameter(arg));
                    //datas.put(arg,uri.getQueryParameter(arg));
                    //datas.add(uri.getQueryParameter(arg));
                }

                //TODO 视频播放
                /*Intent intent = new Intent(mActivity, PlayVideoActivity.class);
                intent.putExtra("data",jsonObject.toString());
                mActivity.startActivity(intent);*/
            }
            return true;
        }


        if (url.startsWith("http:") || url.startsWith("https:")) {
            // 可能有提示下载Apk文件
            if (url.contains(".apk")) {
                handleOtherwise(mActivity, url);
                return true;
            }
            return false;
        }
        handleOtherwise(mActivity, url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.e("fzy", "onPageFinished" + url + view.getOriginalUrl() + view.getTitle() + view.getUrl());
        if (!NetUtil.isNetworkConnected(mActivity)) {
            mIWebPageView.hindProgressBar();
        }
        // html加载完成之后，添加监听图片的点击js函数
        /*if (url.startsWith(HttpManager.Url.LITE_SERVICE_DEBUG_URL)) {
            view.loadUrl("javascript:window.js_imp.getInfoFromJS(document.getElementsByTagName('body')[0].innerText)");
        }*/
        mIWebPageView.addImageClickListener();
        super.onPageFinished(view, url);
    }

    // 视频全屏播放按返回页面被放大的问题
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }

    /**
     * 网页里可能唤起其他的app
     */
    private void handleOtherwise(Activity activity, String url) {
        String appPackageName = "";
        // 支付宝支付
        if (url.contains("alipays")) {
            appPackageName = "com.eg.android.AlipayGphone";

            // 微信支付
        } else if (url.contains("weixin://wap/pay")) {
            appPackageName = "com.tencent.mm";

            // 京东产品详情
        } else if (url.contains("openapp.jdmobile")) {
            appPackageName = "com.jingdong.app.mall";
        }
        if (BaseTools.isApplicationAvilible(activity, appPackageName)) {
            startActivity(url);
        }
    }

    private void startActivity(String url) {
        try {
            Intent intent1 = new Intent();
            intent1.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent1.setData(uri);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
