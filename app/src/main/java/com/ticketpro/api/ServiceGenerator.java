
package com.ticketpro.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticketpro.model.Feature;
import com.ticketpro.util.HttpClientService;
import com.ticketpro.util.TPConstant;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ServiceGenerator {

    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .addInterceptor(new Interceptor() {
                @NonNull
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            })
            .addInterceptor(loggingInterceptor)
            .connectionSpecs(Collections.singletonList(ConnectionSpec.COMPATIBLE_TLS));

    private static final OkHttpClient.Builder httpClient1 = new OkHttpClient.Builder()
            .readTimeout(250, TimeUnit.SECONDS)
            .connectTimeout(250, TimeUnit.SECONDS)
            .writeTimeout(250, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor);



    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(HttpClientService.getUnsafeOkHttpClient())
                    .baseUrl(TPConstant.API_BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create());
    //.addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    public static <S> S createFBService(Class<S> serviceClass, final String authToken) {
        Retrofit.Builder builder_gson =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.FIREBASE_DB_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient1.interceptors().contains(interceptor)) {
                httpClient1.addInterceptor(interceptor);
                //httpClient1.addInterceptor(logging);
                builder_gson.client(httpClient1.build());
            }
        }
        return builder_gson.build().create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder_gson =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.FIREBASE_DB_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        //builder_gson.client(httpClient1.build());
        Retrofit retrofit_gson = builder_gson.build();
        return retrofit_gson.create(serviceClass);
    }

    static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();


    public static <S> S createRxService(Class<S> serviceClass) {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(250, TimeUnit.SECONDS)
                .connectTimeout(250, TimeUnit.SECONDS)
                .writeTimeout(250, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit.Builder builder_rx =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.RX_SERVICE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));
        builder_rx.client(client);
        Retrofit retrofit_gson = builder_rx.build();
        return retrofit_gson.create(serviceClass);
    }

    public static <S> S createRx2Service(Class<S> serviceClass) {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(250, TimeUnit.SECONDS)
                .connectTimeout(250, TimeUnit.SECONDS)
                .writeTimeout(250, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit.Builder builder_rx =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.RX_SERVICE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));

        builder_rx.client(client);
        Retrofit retrofit_gson = builder_rx.build();
        return retrofit_gson.create(serviceClass);
    }




    /*public static <S> S createRxService(Class<S> serviceClass) {
        Retrofit.Builder builder_rx =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.RX_SERVICE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));

        //builder_rx.client(httpClient.build());
        Retrofit retrofit_gson = builder_rx.build();
        return retrofit_gson.create(serviceClass);
    }*/

}


/*
package com.ticketpro.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ticketpro.model.Feature;
import com.ticketpro.util.HttpClientService;
import com.ticketpro.util.TPConstant;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;implementation 'com.squareup.okhttp3:logging-interceptor:4.0.0-alpha02'
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ServiceGenerator {

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @NonNull
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            })
            .connectionSpecs(Collections.singletonList(ConnectionSpec.COMPATIBLE_TLS));

    private static final OkHttpClient.Builder httpClient1 = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS);



    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(HttpClientService.getUnsafeOkHttpClient())
                    .baseUrl(TPConstant.API_BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create());
    //.addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    public static <S> S createFBService(Class<S> serviceClass, final String authToken) {
        Retrofit.Builder builder_gson =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.FIREBASE_DB_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient1.interceptors().contains(interceptor)) {
                httpClient1.addInterceptor(interceptor);
                //httpClient1.addInterceptor(logging);
                builder_gson.client(httpClient1.build());
            }
        }
        return builder_gson.build().create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder_gson =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.FIREBASE_DB_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        //builder_gson.client(httpClient1.build());
        Retrofit retrofit_gson = builder_gson.build();
        return retrofit_gson.create(serviceClass);
    }

    static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();


    public static <S> S createRxService(Class<S> serviceClass) {
        Retrofit.Builder builder_rx =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.RX_SERVICE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));
        //builder_rx.client(httpClient.build());
        Retrofit retrofit_gson = builder_rx.build();
        return retrofit_gson.create(serviceClass);
    }

    */
/*public static <S> S createRxService(Class<S> serviceClass) {
        Retrofit.Builder builder_rx =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.RX_SERVICE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));

        //builder_rx.client(httpClient.build());
        Retrofit retrofit_gson = builder_rx.build();
        return retrofit_gson.create(serviceClass);
    }*//*

    public static <S> S createRx2Service(Class<S> serviceClass) {
        Retrofit.Builder builder_rx =
                new Retrofit.Builder()
                        .client(HttpClientService.getUnsafeOkHttpClient())
                        .baseUrl(TPConstant.RX_SERVICE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));

        //builder_rx.client(httpClient.build());
        Retrofit retrofit_gson = builder_rx.build();
        return retrofit_gson.create(serviceClass);
    }
}
*/
