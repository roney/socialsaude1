package com.socialsaudade.hacker.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.socialsaude.hacker.login.R;
import com.socialsaude.hacker.model.User;
import com.socialsaude.hacker.requests.MyApiEndpointInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class RegisterActivity extends Activity {

    private EditText mPasswordView;
    public static final String BASE_URL = "http://socialsaude.mybluemix.net/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText mNameET = (EditText) findViewById(R.id.name);
        final EditText mEmailET = (EditText) findViewById(R.id.email_register);
        final EditText mPasswordET = (EditText) findViewById(R.id.password_register);

        Button mRegisterButton = (Button) findViewById(R.id.confirm_register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: fazer cadastro

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);

                User user = new User();
                user.setName(mNameET.getText().toString());
                user.setEmail(mEmailET.getText().toString());
                user.setPassword(mPasswordET.getText().toString());

                Log.d("DebugCadastro", "Vai tentar criar usuario: " + user.getName() + ", " + user.getEmail() + ", " + user.getPassword());

                Call<User> call = apiService.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("DebugCadastro", "entrou onResponse");
                        Log.d("DebugCadastro", response.message());
                        Log.d("DebugCadastro", response.toString());

                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("DebugCadastro", "entrou onFailure");
                        Log.d("DebugCadastro", call.toString());
                        Log.d("DebugCadastro", t.getMessage());
                        t.fillInStackTrace();

                    }
                });

//                try {
//                    call.execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });

        TextView mBackTV = (TextView) findViewById(R.id.back_from_register_tv);
        mBackTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

    }


}

