package com.socialsaude.hacker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.socialsaude.hacker.login.R;

public class RightMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout op1 = (LinearLayout) findViewById(R.id.menuOption1);
        LinearLayout op2 = (LinearLayout) findViewById(R.id.menuOption2);
        LinearLayout op3 = (LinearLayout) findViewById(R.id.menuOption3);
        LinearLayout op4 = (LinearLayout) findViewById(R.id.menuOption4);
        LinearLayout op5 = (LinearLayout) findViewById(R.id.menuOption5);
        LinearLayout op6 = (LinearLayout) findViewById(R.id.menuOption6);
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightMenuActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }


}
