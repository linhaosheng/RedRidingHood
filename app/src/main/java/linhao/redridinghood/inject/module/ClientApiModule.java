package linhao.redridinghood.inject.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import linhao.redridinghood.model.api.ApiClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linhao on 2016/8/14.
 */
@Module
public class ClientApiModule {

    String BASE_URL = "https://hltm-api.tomoya.cn/";

    @Singleton
    @Provides
    public OkHttpClient providerOkhttpClient(HttpLoggingInterceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = builder.build();
    //    okHttpClient.interceptors().add(interceptor);
        return okHttpClient;
    }

    @Provides
    @Singleton
    public Gson providerGson(){
        GsonBuilder builder=new GsonBuilder();
        return builder.serializeNulls().create();
    }

    @Singleton
    @Provides
    public Converter.Factory providerConvert(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    public Retrofit provider(OkHttpClient okHttpClient,Converter.Factory factory){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(factory)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    public ApiClient providerApiClient(Retrofit retrofit){
        ApiClient apiClient=retrofit.create(ApiClient.class);
        return apiClient;
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor providerInterceptor(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

}
