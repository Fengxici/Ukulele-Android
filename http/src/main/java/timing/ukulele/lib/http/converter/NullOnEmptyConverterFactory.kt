package timing.ukulele.lib.http.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * 自定义ConverterFactory
 */
class NullOnEmptyConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit): Converter<ResponseBody, Any>? {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
        return Converter { body -> if (body.contentLength() == 0L) null else delegate.convert(body) }
    }
}

