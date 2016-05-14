package com.socialsaude.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.socialsaude.R;



public class ForgotPasswordActivity extends Activity {

    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button mRegisterButton = (Button) findViewById(R.id.send_new_password_button);
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

        TextView mBackTV = (TextView) findViewById(R.id.back_from_forgot_pass_tv);
        mBackTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ForgotPasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

    }


}

