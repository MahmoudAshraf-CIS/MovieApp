package app.com.example.mannas.monvieapp.MainActivity_module;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.mannas.monvieapp.DataObject;
import app.com.example.mannas.monvieapp.R;
import app.com.example.mannas.monvieapp.SettingsActivity;
import app.com.example.mannas.monvieapp.module.PreferencesConstants;

public class MainActivity extends AppCompatActivity {
    static Boolean isConnected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isConnected = isNetworkAvailable();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         
       getSupportFragmentManager().beginTransaction().add(R.id.activity_main, new MainFragment(),MainFragment.class.getName()).commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getBaseContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_Refresh){
            int i=9;
        }

        return super.onOptionsItemSelected(item);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*************    class     ***********/
    public static class MainFragment extends Fragment {

        public ArrayList<DataObject> Data_ls;
        MainRecyclerViewAdapter_Cards CardAdapter;
        MainRecyclerViewAdapter_img GridAdapter;
        RecyclerView.LayoutManager manager;
        MainFragment this_ref;

        String MODE;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            this_ref = this;
            getActivity().getSharedPreferences(getActivity().getPackageName(),MODE_PRIVATE).registerOnSharedPreferenceChangeListener(
                    new SharedPreferences.OnSharedPreferenceChangeListener() {
                        @Override
                        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                            this_ref.Refresh();
                        }
                    }

            );

            Data_ls = new ArrayList<>(); //// TODO: 8/23/2016 updated from the DB
            for(int i=0;i<10;i++){
                DataObject obj=new DataObject(0,"","","title"+Integer.toString(i),"","date",0.0f,1);
                Data_ls.add(obj);
            }

            DataFetcher dataFetcher = new DataFetcher();
            if(isConnected)
                dataFetcher.execute();

            MODE = getActivity().getSharedPreferences(getActivity().getPackageName(),MODE_PRIVATE).getString(PreferencesConstants.Pref_Mode_Key,PreferencesConstants.Pref_Mode_DefVal);

            if( MODE.equals("Cards") ){
                CardAdapter = new MainRecyclerViewAdapter_Cards(Data_ls);
                manager = new StaggeredGridLayoutManager(1,1);
            }
            else if( MODE.equals("Grid") ) {
                GridAdapter = new MainRecyclerViewAdapter_img(Data_ls);
                manager = new StaggeredGridLayoutManager(2,1);
            }

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view =inflater.inflate(R.layout.fragment_main,container,false);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mainRecyclerView);
            if(recyclerView != null){
                if( MODE.equals("Cards") ){
                    recyclerView.setAdapter(CardAdapter);
                    recyclerView.setLayoutManager(manager);
                }
                else if( MODE.equals("Grid") ) {
                    recyclerView.setAdapter(GridAdapter);
                    recyclerView.setLayoutManager(manager);
                }
            }else {
                Log.e("Couldn't find the ", "RecyclerView from the activity_main xml !");
            }
            return view;
        }

        public class DataFetcher extends AsyncTask<Void,Void, Void> {
            final String LOG_TAG = this.getClass().getName();

            public ArrayList<DataObject> Downloaded_DATA_ls;

            final String BASE_URL = "http://api.themoviedb.org/3/movie/";
            SharedPreferences SP = getActivity().getSharedPreferences(getActivity().getPackageName(),MODE_PRIVATE);

            String SORT_PARAM = SP.getString(PreferencesConstants.pref_SortBy_Key, PreferencesConstants.pref_SortBy_DefVal);
            String PARAM = "?api_key=bee0f6d21ef244ebb9424236dc32123a";


            String Server_json = "";
            // updates the Server_json if possible returns true if the download is completed
            private Boolean Download(){
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL( BASE_URL+SORT_PARAM +PARAM );

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();

                    if (inputStream == null) {
                        // Nothing to do.
                        return false;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        Server_json += (line + "\n");
                    }
                    if (Server_json.length() == 0) {
                        return false;
                    }

                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error "+ e);
                    return false;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }
                if(Server_json !=""){
                    return true;
                }
                return false;
            }

            //parse the Server_json to fill the DATA_ls
            private Void parse(){
                try {

                    JSONObject obj = new JSONObject(Server_json);
                    JSONArray results = obj.getJSONArray("results");



                    for(int i=0;i<results.length();i++){
                        JSONObject JsonItem = results.getJSONObject(i);
                         int type,id;
                         String poster_path,backdrop_path,title,overview,release_date;
                         float vote_avg;
                        id = Integer.valueOf(JsonItem.getString("id"));
                        poster_path = JsonItem.getString("poster_path");
                        backdrop_path = JsonItem.getString("backdrop_path");
                        title = JsonItem.getString("title");
                        overview = JsonItem.getString("overview");
                        release_date = JsonItem.getString("release_date");
                        vote_avg = Float.valueOf(JsonItem.getString("vote_average"));
                        Downloaded_DATA_ls.add(new DataObject(id,poster_path,backdrop_path,title,overview,release_date,vote_avg,1));
                    }
                }
                catch (JSONException e){
                    Log.e(e.getMessage(),LOG_TAG+"JSONException");
                }
                return  null;
            }
            @Override
            protected Void doInBackground(Void... params) {
                    if( Download()){
                        Downloaded_DATA_ls = new ArrayList<>();
                        parse();
                    }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if( Downloaded_DATA_ls!=null){
                    if( MODE.equals("Cards"))
                        CardAdapter.notifyDataSetChanged(Downloaded_DATA_ls);
                    else if ( MODE.equals("Grid") )
                        GridAdapter.notifyDataSetChanged(Downloaded_DATA_ls);
                }

            }
        }
        public void Refresh (){
            FragmentTransaction FT  = getFragmentManager().beginTransaction();
            FT.detach( this ).commit();
            FT.attach(this).commit();
            FT.commit();
            Toast.makeText(getContext(),"Refreshed",Toast.LENGTH_LONG).show();
        }
    }
    

}

