package com.witkey.campuswitkey.model;

import com.witkey.campuswitkey.model.entity.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 12554 on 2018/4/10.
 */

public interface ApiService {
    //@Body对应请求体 这里请求体的内容是json字符串（由user转化而来）
    @POST("user/checkuser")
    Call<HttpResult<User>> checkUser(@Body User user);

    @POST("user")
    Call<HttpResult<User>> updateUser(@Body User user);

    @Multipart
    @POST("user/head")
    Call<HttpResult<User>> updateUserAndHead(@Part MultipartBody.Part file,@Part("user") String userJson);

    //@path对应请求路径占位符{}里面的内容， 通过@path可动态配置请求路径
    @GET("user/{id}")
    Call<HttpResult<User>> getUserInfo(@Path("id") Integer id);

    //@Query 以建值对的方式添加到请求路径 如下组合成的路径为 user/checkcode?email=email
    @GET("user/checkcode")
    Call<HttpResult<String>> getCheckcode(@Query("email") String email);

    @PUT("user")
    Call<HttpResult<User>> addUser(@Body User user);

    @POST("user/pwd")
    Call<HttpResult<String>> resetPwd(@Query("email") String email, @Query("pwd") String pwd);
}
