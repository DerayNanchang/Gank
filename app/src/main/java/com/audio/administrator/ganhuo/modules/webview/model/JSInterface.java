package com.audio.administrator.ganhuo.modules.webview.model;

import android.content.Intent;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.alibaba.fastjson.JSON;
import com.audio.administrator.ganhuo.modules.webview.WebViewActivity;
import com.audio.administrator.ganhuo.modules.webview.config.IWebPageView;

/**
 * Created by Jodie on 2018/9/20.
 * Java 与  JS 交互中间类
 */
public class JSInterface {
    public static final String OAUTH_USER_INFO = "oauth_user_info";
    private WebView webView;
    WebViewActivity webViewActivity;

    public JSInterface(WebView webView, IWebPageView webViewActivity) {
        this.webViewActivity = (WebViewActivity) webViewActivity;
        this.webView = webView;
    }

    /**
     * 获取网页内容
     *
     * @param msg
     */
    @JavascriptInterface
    public void getInfoFromJS(String msg) {
        /*try {
            FiiOAuthLoginInfo fiiOAuthLoginInfo = JSON.parseObject(msg, FiiOAuthLoginInfo.class);
            if (fiiOAuthLoginInfo.getStatus() == 0) {
                Intent intent1 = new Intent("com.foxconn.beacon.oauth");
                intent1.setData(Uri.parse("fiichat://oauth_login"));
                intent1.putExtra(OAUTH_USER_INFO, fiiOAuthLoginInfo.getPayload());
                webViewActivity.startActivity(intent1);
            } else {
                webViewActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ToastUtils.toast(webViewActivity, fiiOAuthLoginInfo.getErrmsg());
                    }
                });
            }
            webViewActivity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @JavascriptInterface
    public String getCurrentUser() {
        //TODO
        //String autograph = LiteServiceApp.context.getUser().getAutograph();
        //return autograph;
        return "";
    }

    @JavascriptInterface
    public void callAC() {
        /*ArrayMap<String, String> user = SPCache.get().getACUser();
        String name = user.get("name");
        String password = user.get("password");

        AcLoginModel model = new AcLoginModel();
        model.loginAC(name, password, new HttpSimpleResponse<UserInfoBean, Throwable>() {
            @Override
            public void onAccept(UserInfoBean userInfoBean) {
                // 更新了 token数据
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        String autograph = SampleApplicationLike.getInstance().getUser().getAutograph();
                        String data = "javascript:setNewData('"+autograph+"')";
                        webView.loadUrl(data);
                        //webView.loadUrl("javascript:test('" + autograph + "')");
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {

                AccountManager.logout(new ICallback() {
                    @Override
                    public void onSuccess() {
                        toAcLogin();
                    }
                    @Override
                    public void onFailure(String errMsg) {
                        toAcLogin();
                    }
                });
            }
        });*/
    }

    private void toAcLogin() {
        /*ToastUtils.toast(webViewActivity.getApplicationContext(),"AC账号密码已经失效，请重新登录AC");
        clearWebViewCache();
        webViewActivity.startActivity(new Intent(webViewActivity,AcLoginActivity.class));
        String account = SampleApplicationLike.getInstance().getUserAccount();
        if (!"".equals(account)) {
            SampleApplicationLike.getInstance().close(account);
        }
        SampleApplicationLike.getInstance().clearUser();
        webViewActivity.finish();*/
    }

    private void clearWebViewCache() {
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(webViewActivity);
        CookieManager.getInstance().removeAllCookie();
    }

}
