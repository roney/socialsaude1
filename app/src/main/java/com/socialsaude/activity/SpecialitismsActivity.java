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

public class SpecialitismsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialitsms);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle("Pediatra");
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.specialisms_example);
        TextView info = (TextView) findViewById(R.id.info);
        info.setText("O pediatra é o médico especializado na assistência a crianças e adolescentes," +
                " seja no aspecto preventivo ou curativo. O pediatra realiza consultas de rotina e " +
                "acompanha o crescimento, mede e pesa a criança, para comparar com exames anteriores," +
                " além de prevenir e tratar as possíveis enfermidades. ");
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
