package com.socialsaude.hacker.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.gustavo.myapplication.R;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);

        SearchView mSearchView = (SearchView) menu.findItem(R.id.search);
        //setupSearchView(mSearchView);
        return true;

    }

}
