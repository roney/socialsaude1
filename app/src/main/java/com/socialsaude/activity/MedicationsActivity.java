package com.socialsaude.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.socialsaude.R;

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
        // Set title of Detail page
        collapsingToolbar.setTitle("TYLENOL® 750mg");
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.medications_example);
        TextView info = (TextView) findViewById(R.id.info);
        info.setText("O TYLENOL® 750mg é indicado para o alívio temporário da dor de leve a moderada, " +
                "como aquelas associadas a gripes, resfriados, " +
                "artrites e cólicas, e para a redução da febre.");
        ListView listview =(ListView) findViewById(R.id.listview);
       // List<String> units = new ArrayList<String>();
        String[] units = new String[10];
        units[0] = ("Hospital Geral");
        units[1] = ("Centro Medico Geral");
        units[2] = ("IJF");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, units);
        listview.setAdapter(adapter);

    }

}
