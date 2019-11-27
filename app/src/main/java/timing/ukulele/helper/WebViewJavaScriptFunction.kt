package timing.ukulele.helper

import android.content.Context
import android.os.Handler
import android.os.Message
import android.webkit.JavascriptInterface
import timing.ukulele.helper.X5WebViewHelper.getUserId
import timing.ukulele.helper.X5WebViewHelper.logout
import timing.ukulele.view.browser.X5WebView

/**
 * js与Android的互相调用的类
 */
class WebViewJavaScriptFunction {
    /**
     * webview
     */
    private var mWebView: X5WebView
    /**
     * 上下文
     */
    private var context: Context?
    /**
     * ui处理
     */
    private var handler: Handler? = null

    constructor(mWebView: X5WebView, context: Context?) {
        this.mWebView = mWebView
        this.context = context
    }

    constructor(mWebView: X5WebView, context: Context?, handler: Handler?) {
        this.mWebView = mWebView
        this.context = context
        this.handler = handler
    }

    /**
     * 退出登录
     */
    @JavascriptInterface
    fun logout() {
        logout(context)
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    @get:JavascriptInterface
    val userId: String
        get() = getUserId(context)

    @JavascriptInterface
    fun getTitle(title: String?) {
        val message = Message()
        message.what = MESSAGE_TITLE
        message.obj = title
        handler!!.sendMessage(message)
    }

    companion object {
        /**
         * 设置title
         */
        const val MESSAGE_TITLE = 0
        /**
         * 返回上一页
         */
        const val MESSAGE_BACK = 1
    }
}