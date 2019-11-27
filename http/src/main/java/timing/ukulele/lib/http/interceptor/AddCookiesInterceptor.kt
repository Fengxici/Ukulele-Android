package timing.ukulele.lib.http.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * 在okhttp的请求头上增加cookie的拦截器
 */
class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("UUID", UUID.randomUUID().toString())
        builder.addHeader("User-Agent", "android").addHeader("Content-Type", "text/html; charset=utf-8").addHeader("Connection", "Keep-Alive")
        // TODO do something to builder
        return chain.proceed(builder.build())
    }

    companion object {
        private var instance: AddCookiesInterceptor? = null
        private var mContext: Context? = null
        private var mHttpUrl: String? = null
        fun getInstance(context: Context?, url: String?): AddCookiesInterceptor? {
            if (instance == null) {
                synchronized(AddCookiesInterceptor::class.java) {
                    if (instance == null) {
                        instance = AddCookiesInterceptor()
                    }
                }
            }
            if (mContext == null) {
                mContext = context
            }
            if (mHttpUrl == null) {
                mHttpUrl = url
            }
            return instance
        }
    }
}