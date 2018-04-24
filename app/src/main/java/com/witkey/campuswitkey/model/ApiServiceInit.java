package com.witkey.campuswitkey.model;

import com.witkey.campuswitkey.utils.Url;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 12554 on 2018/4/10.
 */

public class ApiServiceInit {
    private static ApiService SERVICE;
    public static final int DEFAULT_TIMEOUT = 10000;

    public static ApiService getDefault(){
        if(SERVICE == null){
            //手动创建一个okhttpClient并设置时间
            OkHttpClient.Builder http = new OkHttpClient.Builder();
            http.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            SERVICE = new Retrofit.Builder().client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Url.Base_URL)
                    .build().create(ApiService.class);


        }
        return SERVICE;
    }


}
