package com.example.movieapp.data.source.remote;

import com.example.movieapp.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApiInstance {

    public static Retrofit getRetrofit() {
        return getRetrofitForUrl(Constants.BASE_URL);
    }

    public static Retrofit getRetrofitForUrl(String baseUrl) {
        OkHttpClient client = getHttpClient();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor interceptorLogging = new HttpLoggingInterceptor();
        interceptorLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptorLogging)
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .build();
    }

    public static AppApiService getApi() {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(AppApiService.class);
    }
}
