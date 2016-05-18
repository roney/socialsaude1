package com.socialsaude.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.socialsaude.R;
import com.socialsaude.api.SocialSaudeApi;
import com.socialsaude.api.response.MedicationsResponse;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Medication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicationsActivity extends AppCompatActivity {

    private ListView listview;

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
            listview =(ListView) findViewById(R.id.listview);
            getUnits(medication.getId());
            // List<String> units = new ArrayList<String>();
        }

    }
    private void getUnits(String id){
        Call<List<HealthUnit>> call = SocialSaudeApi.getClient(MedicationsActivity.this).getUnitsByMedication(id);
        call.enqueue(new Callback<List<HealthUnit>>() {

            @Override
            public void onResponse(Call<List<HealthUnit>> call, Response<List<HealthUnit>> response) {
                Log.i("DebugLogin", response.message());
                List<String> units = new ArrayList<String>();
                for(HealthUnit unit:response.body()){
                    units.add(unit.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicationsActivity.this, android.R.layout.simple_list_item_1, units);
                listview.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<HealthUnit>> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
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
