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

import com.socialsaude.R;
import com.socialsaude.api.SocialSaudeApi;
import com.socialsaude.socialsaudecommons.model.Article;
import com.socialsaude.socialsaudecommons.model.HealthProfessional;
import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewActivity extends AppCompatActivity {

    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.news_example);
        TextView info = (TextView) findViewById(R.id.info);
        TextView content = (TextView) findViewById(R.id.content);
        // Set title of Detail page
        if(getIntent().hasExtra("object")){
            Article article = (Article)getIntent().getSerializableExtra("object");
            collapsingToolbar.setTitle(article.getTitle());
            content.setText(article.getContent());
            info.setText(article.getLink());


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
