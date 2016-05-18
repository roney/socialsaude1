package com.socialsaude.login;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socialsaude.R;
import com.socialsaude.model.User;
import com.socialsaude.api.MyApiEndpointInterface;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        final EditText mPassword2ET = (EditText) findViewById(R.id.repeat_password);
        Button mRegisterButton = (Button) findViewById(R.id.confirm_register_button);

        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mNameET.getText().toString();
                String email = mEmailET.getText().toString();
                String password = mPasswordET.getText().toString();
                String password2 = mPassword2ET.getText().toString();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);

                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(md5(password));

                Log.d("DebugCadastro", "Vai tentar criar usuario: " + user.getName() + ", " + user.getEmail() + ", " + user.getPassword());

                //Verifica se algum campo está em branco
                if (isAnyEmpty(name, email, password, password2)) {
                    Log.d("DebugCadastro", "algum campo em branco");
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show();
                } else {
                    //Verifica se o email é inválido
                    if (!isValidEmail(email)) {
                        Log.d("DebugCadastro", "email inválido");
                        Toast.makeText(getApplicationContext(), "Insira um email válido!", Toast.LENGTH_LONG).show();
                    } else {
                        //Verifica se as senhas são válidas
                        if (isValidPassword(password, password2)) {

                            Call<String> call = apiService.createUser(user);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.d("DebugCadastro", "entrou onResponse");
                                    Log.d("DebugCadastro", response.message());

                                    if (response.message().equals("Conflict")) {
                                        Log.d("DebugCadastro", "deu conflito");
                                        Toast.makeText(getApplicationContext(), "O email escolhido já está sendo utilizado. Por gentileza, insira outro email.", Toast.LENGTH_LONG).show();

                                    } else {
                                        if(response.message().equals("OK")) {
                                            String idUsuarioCadastrado = response.body();
                                            Log.d("DebugCadastro", "tudo OK, id: "+idUsuarioCadastrado);

                                            Intent intent = new Intent();
                                            intent.setClass(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("DebugCadastro", "entrou onFailure");
                                    Log.d("DebugCadastro", t.getMessage());

                                    Intent intent = new Intent();
                                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            });

                        }
                    }
                }
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

    public final static boolean isAnyEmpty(String name, String email, String password, String password2) {
        if (name.equals("") || email.equals("") || password.equals("") || password2.equals("")) {
            return true;
        }
        return false;
    }

    public final boolean isValidPassword(String password, String password2) {

        if (password.length() < 6) {
            Log.d("DebugCadastro", "senha muito curta");
            Toast.makeText(getApplicationContext(), "Insira uma senha com pelo menos 6 dígitos", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(password2)) {
            Log.d("DebugCadastro", "senha1 e senha2 diferentes entre si");
            Toast.makeText(getApplicationContext(), "As senhas informadas não coincidem", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
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

