package timing.ukulele.lib.http.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 在okhttp的返回的数据头上获取cookie并存储到缓存中的拦截器
 */
class ReceivedCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //TODO  do something about save
        return chain.proceed(chain.request())
    }

    companion object {
        private var instance: ReceivedCookiesInterceptor? = null
        private var mContext: Context? = null
        private var mHttpUrl: String? = null
        fun getInstance(context: Context?, url: String?): ReceivedCookiesInterceptor? {
            if (instance == null) {
                synchronized(ReceivedCookiesInterceptor::class.java) {
                    if (instance == null) {
                        instance = ReceivedCookiesInterceptor()
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