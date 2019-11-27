package timing.ukulele.helper

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import com.blankj.utilcode.util.StringUtils
import com.tencent.smtt.sdk.CookieManager
import com.tencent.smtt.sdk.CookieSyncManager
import timing.ukulele.view.browser.X5WebView

/**
 * x5webview的帮助类
 */
object X5WebViewHelper {
    /**
     * 退出登录
     *
     * @param context
     */
    @JvmStatic
    fun logout(context: Context?) {
    }

    /**
     * 返回上一页
     *
     * @param context
     */
    fun onBack(mWebView: X5WebView?, context: Context) {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack()
            mWebView.loadUrl("javascript:getTitle()")
        } else {
            (context as Activity).finish()
        }
    }

    /**
     * 获取机构ID
     *
     * @param context
     * @return
     */
    fun getOrgId(context: Context?): String {
        return ""
    }

    /**
     * 获取科室
     *
     * @param context
     * @return
     */
    fun getDeptId(context: Context?): String {
        return ""
    }

    /**
     * 获取用户
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getUserId(context: Context?): String {
        return ""
    }

    /**
     * 设置webview及session
     */
    fun initWebSetting(mWebView: X5WebView, context: Context?, url: String, handler: Handler?) {
        if (handler == null) {
            mWebView.addJavascriptInterface(WebViewJavaScriptFunction(mWebView, context), "Mobile")
        } else {
            mWebView.addJavascriptInterface(WebViewJavaScriptFunction(mWebView, context, handler), "Mobile")
        }
        initWebSetting(mWebView, context, url)
    }

    /**
     * 设置webview及session
     */
    private fun initWebSetting(mWebView: X5WebView, context: Context?, url: String) {
        CookieSyncManager.createInstance(context)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        //cookieManager.removeSessionCookie();// 移除
        cookieManager.removeAllCookie()
        val session = ""
        val rememberMe = ""
        if (!StringUtils.isEmpty(session)) {
            val sessions = session.split(";").toTypedArray()
            if (sessions != null && sessions.size > 0) {
                for (i in sessions.indices) {
                    if (sessions[i].contains("SESSION")) {
                        cookieManager.setCookie(url, sessions[i]) // cookies是在HttpClient中获得的cookie
                        break
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(rememberMe)) {
            val rememberMes = rememberMe.split(";").toTypedArray()
            if (rememberMes != null && rememberMes.size > 0) {
                for (i in rememberMes.indices) {
                    if (rememberMes[i].contains("rememberMe")) {
                        cookieManager.setCookie(url, rememberMes[i]) // cookies是在HttpClient中获得的cookie
                        break
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync()
        } else {
            CookieManager.getInstance().flush()
        }
        loadUrl(mWebView, url)
    }

    /**
     * 如果直接传完整的url则直接访问该地址，否则则其前面加上含有#的angular的网址前缀
     *
     * @param mWebView
     * @param url
     */
    private fun loadUrl(mWebView: X5WebView, url: String) {
        mWebView.loadUrl(if (url.contains("http")) url else "https://fengxici.github.io/$url")
    }
}