package com.socialsaude.hacker.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.socialsaude.hacker.adapter.ListAdapter;
import com.socialsaude.hacker.login.R;
import com.socialsaude.hacker.model.Unidade;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        ListView list=(ListView)findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        List<Unidade> items = new ArrayList<Unidade>();
        items.add(new Unidade("Hospital Geral de Fortaleza", "Rua Frei Vidal, 1756", "seg a sex - das 7h as 18h"));
        items.add(new Unidade("Posto de Saúde", "Rua Frei Vidal, 1756", "domingo a domingo - das 7h as 18h"));
        items.add(new Unidade("IJF", "Rua Sem Número", "24hs"));
        ListAdapter adapter=new ListAdapter(this, items);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);

        //SearchView mSearchView = (SearchView) menu.findItem(R.id.search);
        //setupSearchView(mSearchView);
        return true;

    }

}
