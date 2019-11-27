package timing.ukulele.lib.http.manage

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * okHttp3 管理类，封装了okHttp的网络连接方法,  开启Stetho听诊器, 增加了isLog参数判断是否显示log
 */
class OkHttpManager private constructor() {
    private var mTimeOut = 60
    private var headerKey: String? = null
    private var headerValue: String? = null
    private var isLog = true
    /**
     * 设置header
     * 暂时不支持多个header
     */
    fun setHeader(headerKey: String?, headerValue: String?): OkHttpManager? {
        this.headerKey = headerKey
        this.headerValue = headerValue
        return instance
    }

    /**
     * 设置超时时间
     *
     * @param timeOut 数值
     */
    fun setTimeOut(timeOut: Int): OkHttpManager? {
        mTimeOut = timeOut
        return instance
    }

    /**
     * 是否显示Log 默认为true
     *
     * @param isLog true 显示
     */
    fun setLog(isLog: Boolean): OkHttpManager? {
        this.isLog = isLog
        return instance
    }


    fun build(interceptors: List<Interceptor?>?): OkHttpManager? {
        if (client == null) {
            builder = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                            .setLevel(if (isLog) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                    .retryOnConnectionFailure(true)
                    .connectTimeout(mTimeOut.toLong(), TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        var response = chain.request()
                        if (headerKey != null) {
                            response = chain.request()
                                    .newBuilder()
                                    .addHeader(headerKey, headerValue)
                                    .build()
                        }
                        chain.proceed(response)
                    }
            if (interceptors != null) {
                for (interceptor in interceptors) {
                    builder?.addInterceptor(interceptor)
                }
            }
            client = builder?.build()
        }
        return instance
    }

    companion object {
        private var instance: OkHttpManager? = null
        private var mContext: Context? = null

        fun getInstance(context: Context?): OkHttpManager? {
            if (instance == null) {
                synchronized(OkHttpManager::class.java) {
                    if (instance == null) {
                        instance = OkHttpManager()
                    }
                }
            }
            if (mContext == null) {
                mContext = context
            }
            return instance
        }

        /**
         * @return OkHttp3 Client
         */
        var client: OkHttpClient? = null
            private set
        private var builder: OkHttpClient.Builder? = null
    }
}