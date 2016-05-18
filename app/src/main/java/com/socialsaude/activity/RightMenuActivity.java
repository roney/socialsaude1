package com.socialsaude.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.socialsaude.R;
import com.socialsaude.utils.Constants;


public class RightMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

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
                intent.putExtra("extra",Constants.GET_USERS);
                startActivity(intent);
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightMenuActivity.this, ListActivity.class);
                intent.putExtra("extra",Constants.GET_SPECIALISMS);
                startActivity(intent);
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightMenuActivity.this, ListActivity.class);
                intent.putExtra("extra",Constants.GET_UPAS);
                startActivity(intent);
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightMenuActivity.this, ListActivity.class);
                intent.putExtra("extra",Constants.GET_PDS);
                startActivity(intent);
            }
        });
        op5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightMenuActivity.this, ListActivity.class);
                intent.putExtra("extra",Constants.GET_MEDICATIONS);
                startActivity(intent);
            }
        });
        op6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightMenuActivity.this, ListActivity.class);
                intent.putExtra("extra",Constants.GET_HOSPITALS);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                super.onBackPressed();  // optional depending on your needs
                return super.onOptionsItemSelected(item);
        }
    }


}
