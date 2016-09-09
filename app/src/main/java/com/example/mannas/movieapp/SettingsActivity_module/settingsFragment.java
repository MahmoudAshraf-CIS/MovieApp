package com.example.mannas.movieapp.SettingsActivity_module;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.example.mannas.movieapp.R;

import java.util.ArrayList;

/**
 * Created by mannas on 8/26/2016.
 */
public class settingsFragment extends PreferenceFragmentCompat {

    ImageListPreference Mode_selctor;
    ListPreference SortBy_selector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences SP = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

        Mode_selctor = getImageListPreference_Mode(SP.getString(PreferencesConstants.Pref_Mode_Key,PreferencesConstants.Pref_Mode_DefVal));

        SortBy_selector = getListPreference_SortBy(SP.getString(PreferencesConstants.pref_SortBy_Key,PreferencesConstants.pref_SortBy_DefVal));


        PreferenceScreen preferenceScreen = (PreferenceScreen) findPreference("PreferenceScreen");

        preferenceScreen.addPreference(Mode_selctor);
        preferenceScreen.addPreference(SortBy_selector);


    }

    public ImageListPreference getImageListPreference_Mode( String summary ){
        ArrayList<ImageListPreference.Movie_itemMode> ls = new ArrayList<>();
        ls.add(new ImageListPreference.Movie_itemMode(R.drawable.mode1,"Cards"));
        ls.add(new ImageListPreference.Movie_itemMode(R.drawable.mode2,"Grid"));
        ImageListPreference imageListPreference= new ImageListPreference(getContext(),ls);

        imageListPreference.setKey(PreferencesConstants.Pref_Mode_Key);
        imageListPreference.setTitle("Movies Gallery Mode");
        imageListPreference.setSummary(summary); //// TODO: 8/23/2016  read from Preferences

        return imageListPreference ;
    }
    public ListPreference getListPreference_SortBy( String summary ){
        ListPreference listPreference = new ListPreference(getContext());

        listPreference.setKey(PreferencesConstants.pref_SortBy_Key);
        listPreference.setTitle("Sort by");
        listPreference.setSummary(summary); //// TODO: 8/23/2016  set from the Preferences
        listPreference.setValue(summary);

        listPreference.setEntries(new CharSequence[]{"most popular","top rated","Up Coming"});
        listPreference.setEntryValues(new CharSequence[]{"popular","top_rated","upcoming"});

        listPreference.setPersistent(true);
        listPreference.setDialogTitle("Sort The Movies By");
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary(o.toString());
                return false;
            }
        });

        listPreference.setDefaultValue(PreferencesConstants.pref_SortBy_DefVal);
        return listPreference;
    }


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if(preference instanceof ImageListPreference){
            ImageListPreference imageListPreference = (ImageListPreference) preference;
            imageListPreference.StartList(getActivity().getSupportFragmentManager(),"some Tag");
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor sharedPreferences =   getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE).edit();
        sharedPreferences.putString(PreferencesConstants.Pref_Mode_Key,
                Mode_selctor.getSummary().toString()
        );
        sharedPreferences.putString(PreferencesConstants.pref_SortBy_Key  ,
                SortBy_selector.getSummary().toString()
        );
        sharedPreferences.apply();

        Toast.makeText(getContext(),Mode_selctor.getSummary().toString() + "---" +SortBy_selector.getSummary().toString() , Toast.LENGTH_LONG).show();

    }

//    @Override
//    public void onStop() {
//        SharedPreferences.Editor sharedPreferences =   getActivity().getSharedPreferences(getActivity().getPackageName(),Context.MODE_PRIVATE).edit();
//        sharedPreferences.putString(PreferencesConstants.Pref_Mode_Key,
//                Mode_selctor.getSummary().toString()
//        );
//        sharedPreferences.putString(PreferencesConstants.pref_SortBy_Key  ,
//                SortBy_selector.getSummary().toString()
//        );
//        sharedPreferences.apply();
//
//        Toast.makeText(getContext(),Mode_selctor.getSummary().toString() + "---" +SortBy_selector.getSummary().toString() ,Toast.LENGTH_LONG).show();
//
//        super.onStop();
//    }
}
