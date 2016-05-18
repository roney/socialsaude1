package com.socialsaude.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.socialsaude.R;
import com.socialsaude.adapter.MedicationsAdapter;
import com.socialsaude.adapter.SpecialismsAdapter;
import com.socialsaude.adapter.UnitsAdapter;
import com.socialsaude.adapter.UsersAdapter;
import com.socialsaude.api.SocialSaudeApi;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Medication;
import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.socialsaudecommons.model.User;
import com.socialsaude.utils.Constants;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private boolean mSearchOpened;
    private String mSearchQuery;
    private BaseAdapter adapter;
    private ListView list;
    private List<User> medicals;
    private List<Specialism> specialisms; //Especialidades
    private List<HealthUnit> hospitals;
    private List<HealthUnit> upas;
    private List<HealthUnit> pds; //Postos de Saúde
    private List<Medication> medications; //Postos de Saúde


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
        Log.i("Script", "---------onCreate--------------------");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        list=(ListView)findViewById(R.id.list);

        if(getIntent().hasExtra("extra")){
            String params = getIntent().getStringExtra("extra");
            switch (params) {
                case Constants.GET_USERS:
                    getSupportActionBar().setTitle("Médicos");
                    getProfessionals();
                    break;
                case Constants.GET_SPECIALISMS:
                    getSupportActionBar().setTitle("Especialidades");
                    getSpecialisms();
                    break;
                case Constants.GET_UPAS:
                    getSupportActionBar().setTitle("UPAs");
                    getUnits();
                    break;
                case Constants.GET_PDS:
                    getSupportActionBar().setTitle("Postos de Saúde");
                    getUnits();
                    break;
                case Constants.GET_MEDICATIONS:
                    getSupportActionBar().setTitle("Remédios");
                    getMedications();
                    break;
                case Constants.GET_HOSPITALS:
                    getSupportActionBar().setTitle("Hospitais");
                    getUnits();
                    break;
            }

        }

    }

    private void getProfessionals(){
        Call<List<User>> call = SocialSaudeApi.getClient(ListActivity.this).getUsers();
        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.i("onResponse", response.message());
                medicals = response.body();
                adapter = new UsersAdapter(ListActivity.this, medicals);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User user = medicals.get(position);
                        Intent intent = new Intent(ListActivity.this, ProfessionalActivity.class);
                        intent.putExtra("object", (Serializable) user);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
            }
        });
    }

    private void getMedications(){
        Call<List<Medication>> call = SocialSaudeApi.getClient(ListActivity.this).getMedications();
        call.enqueue(new Callback<List<Medication>>() {

            @Override
            public void onResponse(Call<List<Medication>> call, Response<List<Medication>> response) {
                Log.i("DebugLogin", response.message());
                medications = response.body();
                adapter = new MedicationsAdapter(ListActivity.this,medications );
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Medication medication = (Medication) medications.get(position);
                        Intent intent = new Intent(ListActivity.this, MedicationsActivity.class);
                        intent.putExtra("object", (Serializable) medication);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Medication>> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
            }
        });
    }
    private void getUnits(){
        Call<List<HealthUnit>> call = SocialSaudeApi.getClient(ListActivity.this).getUnits();
        call.enqueue(new Callback<List<HealthUnit>>() {

            @Override
            public void onResponse(Call<List<HealthUnit>> call, Response<List<HealthUnit>> response) {
                Log.i("DebugLogin", response.message());
                hospitals = response.body();
                adapter = new UnitsAdapter(ListActivity.this, hospitals);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HealthUnit hospital = hospitals.get(position);
                        Intent intent = new Intent(ListActivity.this, UnitsActivity.class);
                        intent.putExtra("object", (Serializable) hospital);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<HealthUnit>> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
            }
        });
    }

    private void getSpecialisms(){
        Call<List<Specialism>> call = SocialSaudeApi.getClient(ListActivity.this).getSpecialisms();
        call.enqueue(new Callback<List<Specialism>>() {

            @Override
            public void onResponse(Call<List<Specialism>> call, Response<List<Specialism>> response) {
                Log.i("DebugLogin", response.message());
                specialisms = response.body();
                adapter = new SpecialismsAdapter(ListActivity.this, specialisms);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Specialism specialism = specialisms.get(position);
                        Intent intent = new Intent(ListActivity.this, SpecialitismsActivity.class);
                        intent.putExtra("object", (Serializable) specialism);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Specialism>> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);

        SearchManager searchManager =
               (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menu_search = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menu_search.getActionView();
        searchView.setIconified(true);
        searchView.setQueryHint("Procurar...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("Script", "Digitado:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("Script", "Entrou :onQueryTextChange");
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Script", "Entrou :setOnClickListener");
                InputMethodManager imm = (InputMethodManager) ListActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v.findFocus(), 0);
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Script", "Entrou :setOnSearchClickListener");
                InputMethodManager imm = (InputMethodManager) ListActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v.findFocus(), 0);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQuery(null, false);
                searchView.setIconified(false);
                return false;
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("Script", "Entrou :onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }
    /*    @Override
    public boolean onMenuItemActionExpand(MenuItem item)
    {
        if (sm.isMenuShowing()) {
            sm.toggle();
        }
        searchView.setIconified(true);
        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, 32);
        searchView.onKeyDown(32, event);
        return true;
    }*/



}
