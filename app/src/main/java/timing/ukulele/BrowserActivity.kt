package timing.ukulele

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import timing.ukulele.helper.X5WebViewHelper
import timing.ukulele.view.browser.X5WebView
import com.tencent.smtt.sdk.WebChromeClient as WebChromeClient1

class BrowserActivity : AppCompatActivity() {
    /**
     * 进度条
     */
    @BindView(R.id.progressBar)
    lateinit var progressBar: ProgressBar
    /**
     * webview
     */
    @BindView(R.id.m_web_view)
    lateinit var mWebView: X5WebView
    /**
     * url
     */
    private lateinit var url: String
    private var viewBinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_web_x5)
        viewBinder = ButterKnife.bind(this)
        url = "https://fengxici.github.io"
        initWebListener()
        initWebSetting()
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebView.destroy()
        viewBinder?.unbind()

    }


    /**
     * 初始化web监听
     */
    private fun initWebListener() {
        mWebView.setOnLongClickListener { true }
        mWebView.webViewClient = object : WebViewClient() {
            //重写此方法才能够处理在浏览器中的按键事件。
            override fun shouldOverrideKeyEvent(webView: WebView?, keyEvent: KeyEvent?): Boolean {
                return if (keyEvent?.action == KeyEvent.KEYCODE_BACK) {
                    onBack()
                    true
                } else {
                    super.shouldOverrideKeyEvent(webView, keyEvent)
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                /**
                 * 防止加载网页时调起系统浏览器
                 */
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
//                showDialog()
                progressBar.visibility = View.VISIBLE
            }

            override fun onReceivedError(webView: WebView?, i: Int, s: String?, s1: String?) {
                super.onReceivedError(webView, i, s, s1)
                progressBar.visibility = View.GONE
            }

        }
        mWebView.webChromeClient = object : WebChromeClient1() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 10) {
                    progressBar.visibility = View.VISIBLE
                } else if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                }
                progressBar.progress = newProgress
                mWebView.loadUrl("javascript:getTitle()")
            }


            var myVideoView: View? = null
            var myNormalView: View? = null
            var callback: IX5WebChromeClient.CustomViewCallback? = null
            /**
             * 全屏播放配置
             */
            override fun onShowCustomView(view: View?,
                                          customViewCallback: IX5WebChromeClient.CustomViewCallback?) {
                val viewGroup = mWebView.parent as LinearLayout
                viewGroup.removeView(mWebView)
                viewGroup.addView(view)
                myVideoView = view
                myNormalView = mWebView
                callback = customViewCallback
            }

            override fun onHideCustomView() {
                if (callback != null) {
                    callback!!.onCustomViewHidden()
                    callback = null
                }
                if (myVideoView != null) {
                    val viewGroup = myVideoView!!.parent as ViewGroup
                    viewGroup.removeView(myVideoView)
                    viewGroup.addView(myNormalView)
                }
            }

            override fun onJsAlert(arg0: WebView?, arg1: String?, arg2: String?,
                                   arg3: JsResult?): Boolean {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3)
            }

        }
        mWebView.setDownloadListener { _, _, _, _, _ ->
            AlertDialog.Builder(this@BrowserActivity)
                    .setTitle("allow to download？")
                    .setPositiveButton("yes"
                    ) { _, _ ->
                        Toast.makeText(
                                this@BrowserActivity,
                                "fake message: i'll download...",
                                Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton("no"
                    ) { _, _ ->
                        Toast.makeText(
                                this@BrowserActivity,
                                "fake message: refuse download...",
                                Toast.LENGTH_SHORT).show()
                    }
                    .setOnCancelListener {
                        Toast.makeText(
                                this@BrowserActivity,
                                "fake message: refuse download...",
                                Toast.LENGTH_SHORT).show()
                    }.show()
        }
    }

    /**
     * 初始化webSetting
     */
    private fun initWebSetting() {
        X5WebViewHelper.initWebSetting(mWebView, this@BrowserActivity, url, null)
    }

    /**
     *  当按返回键的时候
     */
    private fun onBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack()
            mWebView.loadUrl("javascript:getTitle()")
        } else {
            finish()
        }

    }
}