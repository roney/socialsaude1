package com.socialsaude.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.socialsaude.R;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.medical_example);
        TextView info = (TextView) findViewById(R.id.info);
        ListView listview =(ListView) findViewById(R.id.listview);
        // Set title of Detail page
        if(getIntent().hasExtra("object")){
            User user = (User)getIntent().getSerializableExtra("object");

            collapsingToolbar.setTitle(user.getName());

            info.setText("Pediatra");
            ;
            List<String> units = new ArrayList<String>();
            units.add("Hospital I");
            units.add("Hospital II");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, units);
            listview.setAdapter(adapter);
        }


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
