package com.socialsaude.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialsaude.R;

import org.w3c.dom.Text;

public class UnitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(getString(R.string.item_title));
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView address = (TextView) findViewById(R.id.address);
        address.setText("Rua Frei Vidal, 1756 - Fortaleza");
        TextView openHours = (TextView) findViewById(R.id.openHours);
        openHours.setText("Segunda a sexta-feira das 7h às 19h");
        TextView contact = (TextView) findViewById(R.id.contact);
        contact.setText("(85) 3257-6385");
        TextView email = (TextView) findViewById(R.id.email);
        email.setText("sus@hospital.com");
        TextView steps = (TextView) findViewById(R.id.steps);
        steps.setText("Escolha o exame, a data e o horário e agende e seu procedimento através da nossa " +
                "Central de Agendamento.");
        TextView info = (TextView) findViewById(R.id.info);
        info.setText("É necessário solicitar remarcação ou cancelar a consulta com 24 horas de antecedência. " +
                "Ligue para nossas central de consultas");
        TextView description = (TextView) findViewById(R.id.description);
    }

}
