package com.socialsaude.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socialsaude.R;


import com.socialsaude.activity.MainScreenSSActivity;
import com.socialsaude.api.MyApiEndpointInterface;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {

    public static final String BASE_URL = "http://socialsaude.mybluemix.net/api/";
    private EditText mPasswordView;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loginEditText = (EditText)findViewById(R.id.email_login);
        passwordEditText = (EditText)findViewById(R.id.password);
        Button mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);


                String email = null;
                if(loginEditText != null) {
                    email = loginEditText.getText().toString();
                }
                String password = passwordEditText.getText().toString();

                Log.d("DebugLogin", "vai verificar login: "+ email + ", senha: "+ password);

                Call<Void> call = apiService.verifyLogin(email,md5(password));
                call.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("DebugLogin", "entrou onResponse");
                        Log.d("DebugLogin", response.message());
//                        Log.d("DebugLogin", response.body());
                        if(response.message().equals("OK")) {
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putBoolean("isLoggedSharedPrefs", true);
                            editor.commit();

                            Intent intent = new Intent(LoginActivity.this, MainScreenSSActivity.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(getApplicationContext(), "Usuário ou senha incorretos.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("DebugLogin", "entrou onFailure");
                        Log.d("DebugLogin", t.getMessage());
                    }
                });


            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        TextView mForgotPasswordButton = (TextView) findViewById(R.id.forgot_password_tv);
        mForgotPasswordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: fazer login
                Toast.makeText(getApplicationContext(), "Esqueceu a senha", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}

