package com.example.mannas.movieapp.MainActivity_module;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mannas.movieapp.DetailActivity_module.MovieDetailActivity;
import com.example.mannas.movieapp.MainActivity_module.MainAdapters.RecyclerViewAdapter;
import com.example.mannas.movieapp.MainActivity_module.MainAdapters.RecyclerViewAdapter_Cards;
import com.example.mannas.movieapp.MainActivity_module.MainAdapters.RecyclerViewAdapter_img;
import com.example.mannas.movieapp.MovieObject;
import com.example.mannas.movieapp.R;
import com.example.mannas.movieapp.SettingsActivity_module.PreferencesConstants;
import com.example.mannas.movieapp.SettingsActivity_module.SettingsActivity;
import com.example.mannas.movieapp.data.Contract;
import com.example.mannas.movieapp.data.MoviesLoader;

import java.util.ArrayList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public static final String LOG_TAG = MainActivity.class.getName();
    private final int  DB_Updater_ID  = 0;
    private final int  DB_CursorLoaderID = 1;
    public static final int DB_FavLoader =2;

    RecyclerView recyclerView ;
    TextView RecyclerTitle;
    ImageView RecyclerLoading;
    FloatingActionButton fab;
    boolean FavIsDisplayed=false;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager manager;
    public ArrayList<MovieObject> Data_ls;


    String MODE,SortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RecyclerTitle = (TextView) findViewById(R.id.RecyclerTitle);
        RecyclerLoading = (ImageView) findViewById(R.id.RecyclerLoading);

        fab = (FloatingActionButton) findViewById(R.id.main_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FavIsDisplayed){//display the Fav + show RecyclerTitle
                    DisplayHome();
                }
                else{//display the main
                    DisplayFav();
                }
            }
        });

        MODE   = getSharedPreferences(getPackageName(),Context.MODE_PRIVATE).getString(PreferencesConstants.Pref_Mode_Key,PreferencesConstants.Pref_Mode_DefVal);
        SortBy = getSharedPreferences(getPackageName(),Context.MODE_PRIVATE).getString(PreferencesConstants.pref_SortBy_Key, PreferencesConstants.pref_SortBy_DefVal);
        recyclerView = (RecyclerView) findViewById(R.id.movie_list);
        Data_ls = new ArrayList<>();
        setupRecyclerView();

        if(savedInstanceState == null){
            if(CheckConnection(getBaseContext())){
                //if the device is connected will update the DB if possible in the BackGround
                //keep the Local DB updated in the beginning
                getSupportLoaderManager().initLoader(DB_Updater_ID,null,this);
            }
            //sync the RecyclerView with the Local DB
            DisplayHome();

        } else {
            Data_ls =  savedInstanceState.getParcelableArrayList(MovieObject.ArrayListKey);
            if( FavIsDisplayed = savedInstanceState.getBoolean("FavIsDisplayed")) {
                DisplayFav();
            }else {
                DisplayHome();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MovieObject.ArrayListKey,Data_ls );
        outState.putBoolean("FavIsDisplayed",FavIsDisplayed);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //compare the Preferences with the MODE,SortBy - and refresh the Fragment with the Required change
        String Preference_Mode =  getSharedPreferences(getPackageName(),Context.MODE_PRIVATE).getString(PreferencesConstants.Pref_Mode_Key,PreferencesConstants.Pref_Mode_DefVal);
        String Preference_SortBy =  getSharedPreferences(getPackageName(),Context.MODE_PRIVATE).getString(PreferencesConstants.pref_SortBy_Key,PreferencesConstants.pref_SortBy_DefVal);

        if(! SortBy.equals(Preference_SortBy)){ // refresh the Data with the New SortBy
            SortBy = Preference_SortBy;
            getSupportLoaderManager().restartLoader(DB_Updater_ID,null,this);
            getSupportLoaderManager().restartLoader(DB_CursorLoaderID,null,this);
        }
        if( ! MODE.equals(Preference_Mode)){ //refresh the Mode
            MODE = Preference_Mode;
            setupRecyclerView();
        }
        if(FavIsDisplayed){
            getSupportLoaderManager().restartLoader(DB_FavLoader,null,this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar pref_mode_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getBaseContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * initiate the RecyclerView with the Adapter and manager according to the Preferences
     */

    private void setupRecyclerView() {
        if(MODE.equals(PreferencesConstants.Mode_Card)){
            recyclerViewAdapter = new RecyclerViewAdapter_Cards(Data_ls);
            manager = new StaggeredGridLayoutManager(1,1);
        }
        else if(MODE.equals(PreferencesConstants.Mode_Grid)){
            recyclerViewAdapter = new RecyclerViewAdapter_img(Data_ls);
            if(isTwoPane()) {
                manager = new StaggeredGridLayoutManager(3,1);
            }
            else{
                manager = new StaggeredGridLayoutManager(4,1);
            }
        }

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(manager);

    }
    public boolean CheckConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     *  Loads Favs from DB , set the Header , set fab icon
     */
    public void DisplayFav(){
        FavIsDisplayed = true;
        recyclerViewAdapter.notifyDataSetChanged(new ArrayList<MovieObject>());
        RecyclerLoading.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(DB_FavLoader,null,this);
        RecyclerTitle.setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_home);
    }

    /**
     *  Loads home from DB, sets header,fab icon
     */
    public void DisplayHome(){
        recyclerViewAdapter.notifyDataSetChanged(new ArrayList<MovieObject>());
        RecyclerLoading.setVisibility(View.VISIBLE);
        FavIsDisplayed = false;
        getSupportLoaderManager().initLoader(DB_CursorLoaderID,null,this);
        RecyclerTitle.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_fav);
    }



    public ArrayList<MovieObject> CursorToArrayList(Cursor cursor){
        ArrayList<MovieObject> ls = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                MovieObject m = new MovieObject();
                m.adult = Boolean.getBoolean(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.adult)));
                m.original_language = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.original_language));
                m.release_date = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.release_date));
                m.overview = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.overview));
                m.backdrop_path = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.backdrop_path));
                m.id = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.ID));
                m.original_title = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.original_title));
                m.popularity = Float.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.popularity)));
                m.poster_path = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.poster_path));
                m.title = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.title));
                m.video = Boolean.getBoolean(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.video)));
                m.vote_count = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.vote_count));
                m.vote_avg = cursor.getFloat(cursor.getColumnIndexOrThrow(Contract.Movie_Entry.columns.vote_avg));
                ls.add(m);
            } while (cursor.moveToNext());
        }
            return ls;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id){
            case DB_Updater_ID:
                return new MoviesLoader(getBaseContext());
            case DB_CursorLoaderID:
                return new CursorLoader( getBaseContext() , Contract.Movie_Entry.uri,null,null,null,null);
            case DB_FavLoader:
                return new CursorLoader( getBaseContext() , Contract.Fav_Entry.uri,null,null,null,null);
        }
        return null;
    }
    @Override
    public void onLoadFinished(Loader loader, Object data) {
                if(loader.getId()==DB_Updater_ID){
                    // The DB is updated from the Internet
                }
                else if(loader.getId() == DB_CursorLoaderID && !FavIsDisplayed ){ //update the Home
                    ArrayList<MovieObject> DB_ls =  CursorToArrayList(((Cursor) data));
                    RecyclerLoading.setVisibility(View.GONE);
                    recyclerViewAdapter.notifyDataSetChanged( this.Data_ls = DB_ls);
                }
                else if( loader.getId() == DB_FavLoader && FavIsDisplayed){
                    ArrayList<MovieObject> DB_ls =  CursorToArrayList(((Cursor) data));
                    RecyclerLoading.setVisibility(View.GONE);
                    recyclerViewAdapter.notifyDataSetChanged( this.Data_ls = DB_ls);
                }
    }
    @Override
    public void onLoaderReset(Loader loader) {}

    public boolean isTwoPane(){return  findViewById(R.id.movie_detail_pane) != null; }



}
