package com.socialsaude.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.socialsaude.R;
import com.socialsaude.adapter.UnitsAdapter;
import com.socialsaude.api.SocialSaudeApi;
import com.socialsaude.socialsaudecommons.model.HealthProfessional;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessionalActivity extends AppCompatActivity {

    private ListView listview;
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
        listview =(ListView) findViewById(R.id.listview);
        // Set title of Detail page
        if(getIntent().hasExtra("object")){
            User user = (User)getIntent().getSerializableExtra("object");

            collapsingToolbar.setTitle(user.getName());
            if(getIntent().hasExtra("object_aux")){
                Specialism specialism = (Specialism) getIntent().getSerializableExtra("object_aux");
                info.setText(specialism.getName());
            }
            if(getIntent().hasExtra("object_aux_aux")){
                HealthProfessional specialism = (HealthProfessional) getIntent().getSerializableExtra("object_aux_aux");
                getUnits(specialism.getId());
            }


        }


    }

    private void getUnits(String id){
        Log.i("Script", "ID:" +  id);
        Call<List<HealthUnit>> call = SocialSaudeApi.getClient(ProfessionalActivity.this).getUnitsByProfessional(id);
        call.enqueue(new Callback<List<HealthUnit>>() {

            @Override
            public void onResponse(Call<List<HealthUnit>> call, Response<List<HealthUnit>> response) {
                Log.i("DebugLogin", response.message());
                List<String> units = new ArrayList<String>();
                if(response.body()!=null){
                    for(HealthUnit unit:response.body()){
                        units.add(unit.getName());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfessionalActivity.this, android.R.layout.simple_list_item_1, units);
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
