package com.socialsaude.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialsaude.R;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Specialism;

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
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView address = (TextView) findViewById(R.id.address);
        TextView openHours = (TextView) findViewById(R.id.openHours);
        TextView steps = (TextView) findViewById(R.id.steps);
        TextView contact = (TextView) findViewById(R.id.contact);
        TextView email = (TextView) findViewById(R.id.email);
        TextView info = (TextView) findViewById(R.id.info);
        TextView description = (TextView) findViewById(R.id.description);
        if(getIntent().hasExtra("object")) {
            HealthUnit unit = (HealthUnit) getIntent().getSerializableExtra("object");
            collapsingToolbar.setTitle(unit.getName());
            address.setText(unit.getStreet() + " - " + unit.getCity());
            openHours.setText(unit.getOpenhours());

            contact.setText((unit.getPhone() == null? "":unit.getPhone()) + " - " + (unit.getOptionalPhone()==null?"":unit.getOptionalPhone()));
            email.setText(unit.getEmail());
            steps.setText(unit.getProceedings());
            info.setText(unit.getInfo());
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
