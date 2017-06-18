package com.example.android.nytimes.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.nytimes.fragments.NewsSearchFragment;
import com.example.android.nytimes.R;
import com.example.android.nytimes.databinding.ActivityMainBinding;

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
                    .add(R.id.fragmentContainer, searchFragment, SEARCH_FRAGMENT)
                    .commit();
        }

    }
}
