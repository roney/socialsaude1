package com.socialsaude.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.socialsaude.adapter.NewsAdapter;
import com.socialsaude.adapter.ProfessionalAdapter;
import com.socialsaude.adapter.SpecialismsAdapter;
import com.socialsaude.adapter.UnitsAdapter;
import com.socialsaude.adapter.UsersAdapter;
import com.socialsaude.api.SocialSaudeApi;
import com.socialsaude.api.response.UsersResponse;
import com.socialsaude.socialsaudecommons.model.Article;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Medication;
import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.socialsaudecommons.model.User;
import com.socialsaude.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{

    private boolean mSearchOpened;
    private String mSearchQuery;
    private BaseAdapter adapter;
    private ListView list;
    private List<Article> news;

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
        getAll();

    }


    private void getAll(){
        Call<List<Article>> call = SocialSaudeApi.getClient(NewsActivity.this).getNews();
        call.enqueue(new Callback<List<Article>>() {

            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                Log.i("onResponse", response.message());
                news = response.body();
                if (news != null) {
                    adapter = new NewsAdapter(NewsActivity.this, news);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Article article = news.get(position);
                            Intent intent = new Intent(NewsActivity.this, NewActivity.class);
                            intent.putExtra("object", article);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.i("DebugLogin", "entrou onFailure");
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
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("Script", "onQueryTextSubmit");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
