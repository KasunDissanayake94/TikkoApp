package com.example.tikkoapp.activity;

import com.example.tikkoapp.Notifications.MyResponse;
import com.example.tikkoapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAzqkE-4Q:APA91bFBs5Q6MNGZz8tIBxkDTtFpEVg6D_lOWMg0e4UMo-74qSqyLGMd7bfrSyMIuc61N7XplGsz6CVfn7dWEAoqfOz1q0ZG7bAHwv2sSXxQ-_7VBtmNYFU4k_eNPi444FLEfy0gkTB7",

            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
