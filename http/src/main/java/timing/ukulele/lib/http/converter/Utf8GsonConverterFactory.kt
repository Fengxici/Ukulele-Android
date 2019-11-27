package timing.ukulele.lib.http.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class Utf8GsonConverterFactory private constructor(private val gson: Gson?) : Converter.Factory() {

    init {
        if (gson == null) throw NullPointerException("gson is null")
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val adapter = gson?.getAdapter(TypeToken.get(type!!))
        return Utf8GsonResponseBodyConverter(gson!!, adapter!!)
    }

    // 在这里创建 从自定类型到ResponseBody 的Converter,不能处理就返回null，
    // 主要用于对Part、PartMap、Body注解的处理
    override fun requestBodyConverter(type: Type?,
                                      parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        val adapter = gson?.getAdapter(TypeToken.get(type!!))
        return Utf8GsonRequestBodyConverter(gson!!, adapter!!)
    }

    // 这里用于对Field、FieldMap、Header、Path、Query、QueryMap注解的处理
    // Retrfofit对于上面的几个注解默认使用的是调用toString方法
    override fun stringConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<Any, String>? {
        return Utf8StringConverter()
    }

    companion object {

        @JvmOverloads
        fun create(gson: Gson = Gson()): Utf8GsonConverterFactory {
            return Utf8GsonConverterFactory(gson)
        }
    }
}
