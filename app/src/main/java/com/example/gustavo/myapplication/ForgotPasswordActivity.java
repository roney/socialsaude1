package com.example.gustavo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {

    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button mRegisterButton = (Button) findViewById(R.id.finish_register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: enviar senha por email
                Toast.makeText(getApplicationContext(), "Nova senha eviada!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }


}

