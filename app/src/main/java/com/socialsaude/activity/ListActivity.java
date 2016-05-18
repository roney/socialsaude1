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
import com.socialsaude.api.response.MedicationsResponse;
import com.socialsaude.api.response.SpecialismsResponse;
import com.socialsaude.api.response.UnitsResponse;
import com.socialsaude.api.response.UsersResponse;
import com.socialsaude.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private boolean mSearchOpened;
    private String mSearchQuery;
    private BaseAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
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
                    getSupportActionBar().setTitle("Medicamentos");
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
        Call<UsersResponse> call = SocialSaudeApi.getClient(ListActivity.this).getUsers();
        call.enqueue(new Callback<UsersResponse>() {

            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                Log.i("DebugLogin", response.message());
                adapter = new UsersAdapter(ListActivity.this, response.body().getBody());
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ListActivity.this, ProfessionalActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
            }
        });
    }

    private void getMedications(){
        Call<MedicationsResponse> call = SocialSaudeApi.getClient(ListActivity.this).getMedications();
        call.enqueue(new Callback<MedicationsResponse>() {

            @Override
            public void onResponse(Call<MedicationsResponse> call, Response<MedicationsResponse> response) {
                Log.i("DebugLogin", response.message());
                adapter = new MedicationsAdapter(ListActivity.this, response.body().getBody());
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ListActivity.this, MedicationsActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<MedicationsResponse> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
            }
        });
    }
    private void getUnits(){
        Call<UnitsResponse> call = SocialSaudeApi.getClient(ListActivity.this).getUnits();
        call.enqueue(new Callback<UnitsResponse>() {

            @Override
            public void onResponse(Call<UnitsResponse> call, Response<UnitsResponse> response) {
                Log.i("DebugLogin", response.message());
                adapter = new UnitsAdapter(ListActivity.this, response.body().getBody());
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ListActivity.this, UnitsActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<UnitsResponse> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
                Log.i("DebugLogin", t.getMessage());
            }
        });
    }

    private void getSpecialisms(){
        Call<SpecialismsResponse> call = SocialSaudeApi.getClient(ListActivity.this).getSpecialisms();
        call.enqueue(new Callback<SpecialismsResponse>() {

            @Override
            public void onResponse(Call<SpecialismsResponse> call, Response<SpecialismsResponse> response) {
                Log.i("DebugLogin", response.message());
                Log.i("DebugLogin", response.body().getBody().get(0).getName());
                adapter = new SpecialismsAdapter(ListActivity.this, response.body().getBody());
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ListActivity.this, SpecialitismsActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<SpecialismsResponse> call, Throwable t) {
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
