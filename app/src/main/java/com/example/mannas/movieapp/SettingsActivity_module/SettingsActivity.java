package com.example.mannas.movieapp.SettingsActivity_module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mannas.movieapp.R;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_settings,new settingsFragment()).commit();
    }

}














