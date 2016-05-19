package com.socialsaude.api;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socialsaude.api.response.MedicationsResponse;
import com.socialsaude.api.response.ProfessionalsReponse;
import com.socialsaude.api.response.SpecialismsResponse;
import com.socialsaude.api.response.UnitsResponse;
import com.socialsaude.api.response.UsersResponse;
import com.socialsaude.model.User;
import com.socialsaude.socialsaudecommons.model.Article;
import com.socialsaude.socialsaudecommons.model.HealthProfessional;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Medication;
import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by reis on 16/05/16.
 */
public class SocialSaudeApi {

    public static MyApiEndpointInterface getClient(final Activity context) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(MyApiEndpointInterface.class);
    }

    public interface MyApiEndpointInterface {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

        @GET("users/verify")
        Call<Void> verifyLogin(@Query("email") String email, @Query("password") String password);

        @POST("users/add")
        Call<String> createUser(@Body User user);

        @GET("users")
        Call<List<com.socialsaude.socialsaudecommons.model.User>> getUsers();

        @GET("professionals")
        Call<List<HealthProfessional>> getProfessionals();

        @GET("professionals/all")
        Call<List<UsersResponse>> getAll();

        @GET("articles")
        Call<List<Article>> getNews();

        @GET("units")
        Call<List<HealthUnit>> getUnits();

        @GET("units/medication/{id}")
        Call<List<HealthUnit>> getUnitsByMedication(@Path("id") String id);

        @GET("units/specialism/{id}")
        Call<List<HealthUnit>> getUnitsBySpecialism(@Path("id") String id);

        @GET("units/professional/{id}")
        Call<List<HealthUnit>> getUnitsByProfessional(@Path("id") String stringId);

        @GET("medications")
        Call<List<Medication>> getMedications();

        @GET("specialisms")
        Call<List<Specialism>> getSpecialisms();
    }
}