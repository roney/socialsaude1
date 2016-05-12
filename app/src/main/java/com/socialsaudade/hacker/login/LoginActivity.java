package com.socialsaudade.hacker.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.socialsaude.hacker.login.R;

import com.socialsaude.hacker.activity.MainScreenSSActivity;
import com.socialsaude.hacker.requests.LoginRequest;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {

    private EditText mPasswordView;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = (EditText)findViewById(R.id.login);
        passwordEditText = (EditText)findViewById(R.id.password);
        Button mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: fazer login
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                String email = null;
                if(loginEditText != null) {
                    email = loginEditText.getText().toString();
                }
                String password = passwordEditText.getText().toString();

                try {
                    boolean userExist = new LoginRequest(LoginActivity.this, email, password).execute("http://socialsaude.mybluemix.net/api/users/login").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LoginActivity.this, MainScreenSSActivity.class);
                startActivity(intent);

            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: cadastrar
                Toast.makeText(getApplicationContext(), "Cadastrar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        Button mForgotPasswordButton = (Button) findViewById(R.id.forgot_password_button);
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


}

