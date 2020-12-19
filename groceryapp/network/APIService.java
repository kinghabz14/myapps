package com.myapp.groceryapp.network;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key = AAAAwg8PTZg:APA91bEY2M-GHRhnKptu3VuNaRK3daxSdLf1S8p5ltF8wCvQ5ylGFmSXNCeXnTfymzN5VqWpucoYn8T6wq2ffR6i45sFRO2GE7c8bsVt9M7p5xNQgHqzAJhxpwSYg1WfvQyuXBjiZ_Yv"
            }
    )

    @POST ("fcm/send")
    Call<MyResponse> sendNotification (@Body NotificationSender Body);
}
