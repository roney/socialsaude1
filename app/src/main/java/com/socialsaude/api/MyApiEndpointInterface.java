package com.socialsaude.api;

import com.socialsaude.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by gustavo on 13/05/2016.
 */
public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

//    @GET("users/{username}")
//    Call<User> getUser(@Path("username") String username);
//
//    @GET("group/{id}/users")
//    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @GET("users/verify")
    Call<Void> verifyLogin(@Query("email")String email, @Query("password")String password);

    @POST("users/add")
    Call<String> createUser(@Body User user);

    @GET("users")
    Call<Void> getUsers();
}