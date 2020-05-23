package com.panky.foodapp.RESTapi;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodClient {

// LẤY API từ themealDB
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    // lấy dữ liệu từ đường link trên, phân tích cú pháp phản hồi api
    public static Retrofit getFoodClient() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(provideOkHttp())// tạo ra 1 client có gắn baseUrl kèm theo giao thức mạng http
                .addConverterFactory(GsonConverterFactory.create()) // gắn 1 bộ chuyển đổi dữ liệu dùng thư viện chuyển Gson để chuyển dữ liệu Json từ CSDL trên mạng về thành dữ liệu chuyển vào Model
                .build();
    }

    /*đưa ra giá trị Log code status */
    private static Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /*trả vê code trạng thái của internet 404, 200,...*/
    private static OkHttpClient provideOkHttp() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(provideLoggingInterceptor())
                .build();
    }
}
