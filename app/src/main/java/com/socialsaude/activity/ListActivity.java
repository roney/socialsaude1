package com.socialsaude.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private boolean mSearchOpened;
    private String mSearchQuery;
    private ProfessionalAdapter professionalAdapter;
    private UnitsAdapter unitsAdapter;
    private SpecialismsAdapter specialismsAdapter;
    private MedicationsAdapter medicationsAdapter;
    private ListView list;
    private List<UsersResponse> allMedicals;
    private List<Specialism> specialisms; //Especialidades
    private List<HealthUnit> hospitals = new ArrayList<HealthUnit>();
    private List<Medication> medications; //Postos de Saúde
    private String params = "";



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
            params = getIntent().getStringExtra("extra");
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

                professionalAdapter = new ProfessionalAdapter(ListActivity.this,allMedicals );
                list.setAdapter(professionalAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UsersResponse usersResponse = allMedicals.get(position);
                        Intent intent = new Intent(ListActivity.this, ProfessionalActivity.class);
                        intent.putExtra("object", usersResponse.getUser());
                        intent.putExtra("object_aux", usersResponse.getSpecialism());
                        intent.putExtra("object_aux_aux",  usersResponse.getHealthProfessional());
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
    private void getMedications(){

        Call<List<Medication>> call = SocialSaudeApi.getClient(ListActivity.this).getMedications();
        call.enqueue(new Callback<List<Medication>>() {

            @Override
            public void onResponse(Call<List<Medication>> call, Response<List<Medication>> response) {
                Log.i("DebugLogin", response.message());
                medications = response.body();
                medicationsAdapter = new MedicationsAdapter(ListActivity.this, medications);
                list.setAdapter(medicationsAdapter);
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
                for (HealthUnit u : response.body()) {
                    if (u.getSpecification().equals(specification)) {
                        hospitals.add(u);
                    }
                }
                unitsAdapter = new UnitsAdapter(ListActivity.this, hospitals);
                list.setAdapter(unitsAdapter);
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
                specialismsAdapter = new SpecialismsAdapter(ListActivity.this, specialisms);
                list.setAdapter(specialismsAdapter);
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
        // Get the SearchView and set the searchable configuration
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, ListActivity.class)));
        searchView.setIconifiedByDefault(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.i("Script", "onClose:" + params);
                switch (params) {
                    case Constants.GET_USERS:
                        professionalAdapter.removeAllItems();
                        professionalAdapter.addMoreItems(allMedicals);
                        return false;
                    case Constants.GET_SPECIALISMS:
                        specialismsAdapter.removeAllItems();
                        specialismsAdapter.addMoreItems(specialisms);
                        return false;
                    case Constants.GET_MEDICATIONS:
                        medicationsAdapter.removeAllItems();
                        medicationsAdapter.addMoreItems(medications);
                        return false;
                    default:
                        unitsAdapter.removeAllItems();
                        unitsAdapter.addMoreItems(hospitals);
                        return false;
                }
            }
        });
        return true;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        switch (params) {
            case Constants.GET_USERS:
                searchUser(query);
                break;
            case Constants.GET_SPECIALISMS:
                searchSpecialism(query);
                break;
            case Constants.GET_MEDICATIONS:
                searchMedications(query);
                break;
            default:
                searchUnit(query);
                break;
        }
        return false;
    }

    private void searchUser(String query){
        List<UsersResponse> users = new ArrayList<UsersResponse>();
        Log.i("Script", "searchUser");
        if(query!=null && query.length()>0 && professionalAdapter!=null){
            for(int i=0 ; i<professionalAdapter.getCount() ; i++){
                View v = getViewByPosition(i);
                TextView name = (TextView)v.findViewById(R.id.title);
                if(name.getText().toString().trim().contains(query.trim())){
                    Log.i("Script", "text: " + name.getText());
                    users.add(allMedicals.get(i));
                }
            }
            if(users.size()>0){
                professionalAdapter.removeAllItems();
                professionalAdapter.addMoreItems(users);
            }else{
                professionalAdapter.removeAllItems();
                professionalAdapter.addMoreItems(allMedicals);
            }

        }

    }
    private void searchSpecialism(String query){
        List<Specialism> sp = new ArrayList<Specialism>();
        if(query!=null && query.length()>0 && specialismsAdapter!=null){
            for(int i=0 ; i<specialismsAdapter.getCount() ; i++){
                View v = getViewByPosition(i);
                TextView name = (TextView)v.findViewById(R.id.title);
                if(name.getText().toString().trim().contains(query.trim())){
                    sp.add(specialisms.get(i));
                }
            }
            if(sp.size()>0){
                specialismsAdapter.removeAllItems();
                specialismsAdapter.addMoreItems(sp);
            }else{
                specialismsAdapter.removeAllItems();
                specialismsAdapter.addMoreItems(specialisms);
            }

        }
        if(query==null){
            specialismsAdapter.removeAllItems();
            specialismsAdapter.addMoreItems(specialisms);
        }
    }
    private void searchMedications(String query){
        List<Medication> md = new ArrayList<Medication>();
        if(query!=null && query.length()>0 && medicationsAdapter!=null){
            for(int i=0 ; i<medicationsAdapter.getCount() ; i++){
                View v = getViewByPosition(i);
                TextView name = (TextView)v.findViewById(R.id.title);
                if(name.getText().toString().trim().contains(query.trim())){
                    md.add(medications.get(i));
                }
            }
            if(md.size()>0){
                medicationsAdapter.removeAllItems();
                medicationsAdapter.addMoreItems(md);
            }else{
                medicationsAdapter.removeAllItems();
                medicationsAdapter.addMoreItems(medications);
            }
        }
        if(query==null){
            medicationsAdapter.removeAllItems();
            medicationsAdapter.addMoreItems(medications);
        }
    }
    private void searchUnit(String query){
        List<HealthUnit> healthUnits = new ArrayList<HealthUnit>();
        if(query!=null && query.length()>0 && unitsAdapter!=null){
            for(int i=0 ; i<unitsAdapter.getCount() ; i++){
                View v = getViewByPosition(i);
                TextView name = (TextView)v.findViewById(R.id.title);
                if(name.getText().toString().trim().contains(query.trim())){
                    healthUnits.add(hospitals.get(i));
                }
            }
            if(healthUnits.size()>0){
                unitsAdapter.removeAllItems();
                unitsAdapter.addMoreItems(healthUnits);
            }else{
                unitsAdapter.removeAllItems();
                unitsAdapter.addMoreItems(hospitals);
            }

        }
        if(query==null){
            unitsAdapter.removeAllItems();
            unitsAdapter.addMoreItems(hospitals);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    public View getViewByPosition(int pos) {
        final int firstListItemPosition = list.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + list.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return list.getAdapter().getView(pos, null, list);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return list.getChildAt(childIndex);
        }
    }
}
