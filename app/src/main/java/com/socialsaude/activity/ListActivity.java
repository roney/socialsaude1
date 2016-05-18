package com.socialsaude.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.socialsaude.R;
import com.socialsaude.adapter.MedicationsAdapter;
import com.socialsaude.adapter.ProfessionalAdapter;
import com.socialsaude.adapter.SpecialismsAdapter;
import com.socialsaude.adapter.UnitsAdapter;
import com.socialsaude.adapter.UsersAdapter;
import com.socialsaude.api.SocialSaudeApi;
import com.socialsaude.api.response.UsersResponse;
import com.socialsaude.api.response.UsersResponse;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Medication;
import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.socialsaudecommons.model.User;
import com.socialsaude.utils.Constants;

import java.util.ArrayList;
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
    private List<UsersResponse> allMedicals;
    private List<Specialism> specialisms; //Especialidades
    private List<HealthUnit> hospitals = new ArrayList<HealthUnit>();
    private List<Medication> medications; //Postos de Saúde

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;


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
                    getAll();
                    break;
                case Constants.GET_SPECIALISMS:
                    getSupportActionBar().setTitle("Especialidades");
                    getSpecialisms();
                    break;
                case Constants.GET_UPAS:
                    getSupportActionBar().setTitle("UPAs");
                    getUnits("UPA");
                    break;
                case Constants.GET_PDS:
                    getSupportActionBar().setTitle("Postos de Saúde");
                    getUnits("PS");
                    break;
                case Constants.GET_MEDICATIONS:
                    getSupportActionBar().setTitle("Remédios");
                    getMedications();
                    break;
                case Constants.GET_HOSPITALS:
                    getSupportActionBar().setTitle("Hospitais");
                    getUnits("HOSPITAL");
                    break;
            }

        }

    }


    private void getAll(){
        Call<List<UsersResponse>> call = SocialSaudeApi.getClient(ListActivity.this).getAll();
        call.enqueue(new Callback<List<UsersResponse>>() {

            @Override
            public void onResponse(Call<List<UsersResponse>> call, Response<List<UsersResponse>> response) {
                Log.i("onResponse", response.message());
                allMedicals =  response.body();
                Log.i("onResponse", allMedicals.get(0).getHealthProfessional().toString());
                Log.i("onResponse", allMedicals.get(0).getSpecialism().toString());

                adapter = new ProfessionalAdapter(ListActivity.this,allMedicals );
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UsersResponse usersResponse = allMedicals.get(position);
                        Intent intent = new Intent(ListActivity.this, ProfessionalActivity.class);
                        intent.putExtra("object", usersResponse.getUser());
                        intent.putExtra("object_aux", usersResponse.getSpecialism());
                        intent.putExtra("object_aux_aux", usersResponse.getHealthProfessional());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<UsersResponse>> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
            }
        });
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
                        intent.putExtra("object", user);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
                adapter = new MedicationsAdapter(ListActivity.this, medications);
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
    private void getUnits(final String specification){
        Call<List<HealthUnit>> call = SocialSaudeApi.getClient(ListActivity.this).getUnits();
        call.enqueue(new Callback<List<HealthUnit>>() {

            @Override
            public void onResponse(Call<List<HealthUnit>> call, Response<List<HealthUnit>> response) {
                Log.i("DebugLogin", response.message());
                for(HealthUnit u:response.body()){
                    if(u.getSpecification().equals(specification)){
                        hospitals.add(u);
                    }
                }
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
                        intent.putExtra("object", specialism);
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

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(ListActivity.this, "onOptionsItemSelected: ", Toast.LENGTH_SHORT).show();

        switch (id) {
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @   Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar
        Toast.makeText(ListActivity.this, "kkkkkk: ", Toast.LENGTH_SHORT).show();

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search_white));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Toast.makeText(ListActivity.this, "Digitado: ", Toast.LENGTH_SHORT).show();
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_call_black));

            isSearchOpened = true;
        }
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }
    private void doSearch() {
        Toast.makeText(ListActivity.this, "Digitado: ", Toast.LENGTH_SHORT).show();
    }


}
