package com.example.android.nytimes.fragments;

import android.content.Context;
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

public class FilterFragment extends DialogFragment {
    private FragmentFilterBinding binding;
    FilterSubmit listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final CheckBox chkArts = binding.checkboxArt;
        final CheckBox chkFashion = binding.checkboxFashionAndStyle;
        final CheckBox chkSports = binding.checkboxSports;
        final CheckBox chkHealth = binding.checkboxHealth;
        final CheckBox chkEducation = binding.checkboxEducation;
        Button btnSave = binding.btnSave;
        listener = (FilterSubmit) this.getParentFragment();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("art", false)) {
            chkArts.setChecked(true);
        }
        if (sharedPreferences.getBoolean("fashion", false)) {
            chkFashion.setChecked(true);
        }
        if (sharedPreferences.getBoolean("sports", false)) {
            chkSports.setChecked(true);
        }
        if (sharedPreferences.getBoolean("health", false)) {
            chkHealth.setChecked(true);
        }
        if (sharedPreferences.getBoolean("education", false)) {
            chkEducation.setChecked(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newsDeskBoolean = (chkArts.isChecked() || chkFashion.isChecked() || chkSports.isChecked() || chkEducation.isChecked() || chkHealth.isChecked());
                String newsDesk = "news_desk:(";
                if (chkArts.isChecked()) {
                    newsDesk = newsDesk + "\"Art\" ";
                }
                if (chkFashion.isChecked()) {
                    newsDesk = newsDesk + "\"Fashion\" ";
                }
                if (chkSports.isChecked()) {
                    newsDesk = newsDesk + "\"Sports\" ";
                }
                if (chkEducation.isChecked()) {
                    newsDesk = newsDesk + "\"Education\" ";
                }
                if (chkHealth.isChecked()) {
                    newsDesk = newsDesk + "\"Health\" ";
                }
                newsDesk = newsDesk + ")";
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("newsDeskBoolean", newsDeskBoolean);
                if (newsDeskBoolean) {
                    editor.putString("newsDesk", newsDesk);
                }
                editor.putBoolean("art", chkArts.isChecked());
                editor.putBoolean("fashion", chkFashion.isChecked());
                editor.putBoolean("sports", chkSports.isChecked());
                editor.putBoolean("health", chkHealth.isChecked());
                editor.putBoolean("education", chkEducation.isChecked());
                editor.commit();
                listener.onSubmit();
                getDialog().dismiss();
            }
        });
    }

    public interface FilterSubmit {
        void onSubmit();
    }
}
