package com.example.android.nytimes.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.android.nytimes.R;
import com.example.android.nytimes.databinding.FragmentFilterBinding;
import com.example.android.nytimes.utils.SharedPref;

public class FilterFragment extends DialogFragment {
    private FragmentFilterBinding binding;
    FilterSubmit listener;
    CheckBox chkArts, chkFashion, chkSports, chkHealth, chkEducation;
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chkArts = binding.checkboxArt;
        chkFashion = binding.checkboxFashionAndStyle;
        chkSports = binding.checkboxSports;
        chkHealth = binding.checkboxHealth;
        chkEducation = binding.checkboxEducation;
        btnSave = binding.btnSave;
        listener = (FilterSubmit) this.getParentFragment();

        setupFilters();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newsDeskBoolean = (chkArts.isChecked() || chkFashion.isChecked() || chkSports.isChecked() || chkEducation.isChecked() || chkHealth.isChecked());
                String newsDesk = SharedPref.createNewsDeskString(chkArts.isChecked(), chkFashion.isChecked(), chkSports.isChecked(), chkEducation.isChecked(), chkHealth.isChecked());
                storeInSharedPref(newsDeskBoolean, newsDesk);
                listener.onSubmit();
                getDialog().dismiss();
            }
        });
    }

    private void storeInSharedPref(boolean newsDeskBoolean, String newsDesk) {
        SharedPreferences sharedPref = SharedPref.getSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SharedPref.NEWS_DESK_BOOLEAN, newsDeskBoolean);
        if (newsDeskBoolean) {
            editor.putString(SharedPref.NEWS_DESK, newsDesk);
        }
        editor.putBoolean(SharedPref.ART, chkArts.isChecked());
        editor.putBoolean(SharedPref.FASHION, chkFashion.isChecked());
        editor.putBoolean(SharedPref.SPORTS, chkSports.isChecked());
        editor.putBoolean(SharedPref.HEALTH, chkHealth.isChecked());
        editor.putBoolean(SharedPref.EDUCATION, chkEducation.isChecked());
        editor.commit();
    }

    private void setupFilters() {
        Activity context = getActivity();
        if (SharedPref.getBoolean(context, SharedPref.ART)) {
            chkArts.setChecked(true);
        }
        if (SharedPref.getBoolean(context, SharedPref.FASHION)) {
            chkFashion.setChecked(true);
        }
        if (SharedPref.getBoolean(context, SharedPref.SPORTS)) {
            chkSports.setChecked(true);
        }
        if (SharedPref.getBoolean(context, SharedPref.HEALTH)) {
            chkHealth.setChecked(true);
        }
        if (SharedPref.getBoolean(context, SharedPref.EDUCATION)) {
            chkEducation.setChecked(true);
        }
    }

    public interface FilterSubmit {
        void onSubmit();
    }
}
