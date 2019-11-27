package timing.ukulele.lib.http.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import timing.ukulele.lib.http.converter.NullOnEmptyConverterFactory;
import timing.ukulele.lib.http.converter.Utf8GsonConverterFactory;

/**
 * Retrofit框架的管理类
 */
public final class RetrofitManager {
    private static RetrofitManager instance;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    private static String sBaseUrl;

    private Retrofit retrofit;

    private RetrofitManager() {
    }

    public RetrofitManager setBaseUrl(String url) {
        sBaseUrl = url;
        return instance;
    }

    public String getBaseUrl() {
        return sBaseUrl;
    }

    /**
     * Retrofit的初始化设置
     */
    public void build(OkHttpClient okHttpClient) {
        if (sBaseUrl == null)
            //noinspection ThrowableNotThrown
            new IllegalAccessException("need setBaseUrl");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(sBaseUrl)
                .addConverterFactory(new NullOnEmptyConverterFactory())
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(Utf8GsonConverterFactory.Companion.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        if (retrofit == null)
            //noinspection ThrowableNotThrown
            new IllegalAccessException("Please build");
        return retrofit;
    }

}
