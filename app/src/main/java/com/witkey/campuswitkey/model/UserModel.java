package com.witkey.campuswitkey.model;

import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.witkey.campuswitkey.model.entity.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 12554 on 2018/4/10.
 */

public class UserModel implements IUserModel {
    @Override
    public void checkUser(String user_email, String pwd, final LoadTasksCallBack callBack) {
        User user = new User();
        user.setUser_email(user_email);
        user.setUser_pwd(pwd);
        Log.d("Login m", user_email);
        Log.d("Login m", pwd);
        ApiService apiService = ApiServiceInit.getDefault();
        Call<HttpResult<User>> call = apiService.checkUser(user);
        call.enqueue(new Callback<HttpResult<User>>() {
            @Override
            public void onResponse(Call<HttpResult<User>> call, Response<HttpResult<User>> response) {
                HttpResult<User> resRestult = response.body();
                callBack.onSuccess(resRestult);
            }

            @Override
            public void onFailure(Call<HttpResult<User>> call, Throwable t) {
                callBack.onFailed();
                call.cancel();

            }

        });

    }

    @Override
    public void getCheckCode(String email, final LoadTasksCallBack callBack) {
        ApiService apiService = ApiServiceInit.getDefault();
        Call<HttpResult<String>> call = apiService.getCheckcode(email);
        call.enqueue(new Callback<HttpResult<String>>() {
            @Override
            public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                HttpResult<String> resRestult = response.body();
                callBack.onSuccess(resRestult);
            }

            @Override
            public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                callBack.onFailed();
                call.cancel();

            }

        });

    }

    @Override
    public void resetPwd(User user, final LoadTasksCallBack callBack) {
        ApiService apiService = ApiServiceInit.getDefault();
        Call<HttpResult<String>> call = apiService.resetPwd(user.getUser_email(), user.getUser_pwd());
        call.enqueue(new Callback<HttpResult<String>>() {
            @Override
            public void onResponse(Call<HttpResult<String>> call, Response<HttpResult<String>> response) {
                HttpResult<String> resRestult = response.body();
                callBack.onSuccess(resRestult);
            }

            @Override
            public void onFailure(Call<HttpResult<String>> call, Throwable t) {
                callBack.onFailed();
                call.cancel();

            }

        });

    }

    @Override
    public void addUser(User user, final LoadTasksCallBack callBack) {
        ApiService apiService = ApiServiceInit.getDefault();
        Call<HttpResult<User>> call = apiService.addUser(user);
        call.enqueue(new Callback<HttpResult<User>>() {
            @Override
            public void onResponse(Call<HttpResult<User>> call, Response<HttpResult<User>> response) {
                HttpResult<User> resRestult = response.body();
                callBack.onSuccess(resRestult);
            }

            @Override
            public void onFailure(Call<HttpResult<User>> call, Throwable t) {
                callBack.onFailed();
                call.cancel();

            }

        });

    }

    @Override
    public void getUserInfo(int id, final LoadTasksCallBack callBack) {
        ApiService apiService = ApiServiceInit.getDefault();
        Call<HttpResult<User>> call = apiService.getUserInfo(id);
        call.enqueue(new Callback<HttpResult<User>>() {
            @Override
            public void onResponse(Call<HttpResult<User>> call, Response<HttpResult<User>> response) {
                HttpResult<User> resRestult = response.body();
                callBack.onSuccess(resRestult);
            }

            @Override
            public void onFailure(Call<HttpResult<User>> call, Throwable t) {
                callBack.onFailed();
                call.cancel();

            }

        });

    }

    @Override
    public void updateUser(User user, final LoadTasksCallBack callBack) {
        ApiService apiService = ApiServiceInit.getDefault();
        Call<HttpResult<User>> call = apiService.updateUser(user);
        call.enqueue(new Callback<HttpResult<User>>() {
            @Override
            public void onResponse(Call<HttpResult<User>> call, Response<HttpResult<User>> response) {
                HttpResult<User> resRestult = response.body();
                callBack.onSuccess(resRestult);
            }

            @Override
            public void onFailure(Call<HttpResult<User>> call, Throwable t) {
                callBack.onFailed();
                call.cancel();

            }

        });


    }

    @Override
    public void updateUserAndHead(User user, final LoadTasksCallBack callBack) {
        ApiService apiService = ApiServiceInit.getDefault();
        String filePath = user.getUser_head();
        File file = new File(filePath);
        Log.i("updateUser filePath:",filePath );
        if (!file.exists()) {
            updateUser(user, callBack);
            return;

        }
        String[] paths= filePath.split("/");
        String imgName = paths[paths.length-1];
        user.setUser_head(imgName);
        RequestBody photoBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", imgName , photoBody);
        Gson gson = new Gson();
        String content = gson.toJson(user, User.class);
        Log.i("updateUserAndHead", content);
        Call<HttpResult<User>> call = apiService.updateUserAndHead(photo, content);
        call.enqueue(new Callback<HttpResult<User>>() {
            @Override
            public void onResponse(Call<HttpResult<User>> call, Response<HttpResult<User>> response) {
                HttpResult<User> resRestult = response.body();
                callBack.onSuccess(resRestult);
            }

            @Override
            public void onFailure(Call<HttpResult<User>> call, Throwable t) {
                callBack.onFailed();
                call.cancel();

            }

        });


    }


}
