package com.example.android.nytimes.Activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.nytimes.Fragments.NewsSearchFragment;
import com.example.android.nytimes.R;
import com.example.android.nytimes.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String SEARCH_FRAGMENT = "search_fragment";
    NewsSearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        searchFragment = (NewsSearchFragment) fragmentManager.findFragmentByTag(SEARCH_FRAGMENT);

        if(searchFragment == null){
            searchFragment = new NewsSearchFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, searchFragment)
                    .commit();
        }

    }
}
