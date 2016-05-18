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
import android.widget.Toast;

import com.socialsaude.R;
import com.socialsaude.socialsaudecommons.model.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.medications_example);
        TextView info = (TextView) findViewById(R.id.info);
        // Set title of Detail page
        if(getIntent().hasExtra("object")){
            Medication medication = (Medication)getIntent().getSerializableExtra("object");
            collapsingToolbar.setTitle(medication.getName());
            info.setText(medication.getDescription());
            ListView listview =(ListView) findViewById(R.id.listview);
            // List<String> units = new ArrayList<String>();
            String[] units = new String[10];
            units[0] = ("Hospital I");
            units[1] = ("Hospital II");
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
