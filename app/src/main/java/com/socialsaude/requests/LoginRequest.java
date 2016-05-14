package com.socialsaude.requests;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoginRequest extends AsyncTask<String, Void, Boolean> {

    String email;
    String password;
    ProgressDialog dialog;
    Context context;

    public LoginRequest(Context ctx, String email, String password) {
        this.context = ctx;
        this.email = email;
        this.password = password;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        Boolean isUserExists = false;
        String result = "";
        String urlString = params[0];
        return isUserExists;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }


}
