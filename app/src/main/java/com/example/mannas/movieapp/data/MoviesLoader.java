package com.example.mannas.movieapp.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mannas.movieapp.MovieObject;
import com.example.mannas.movieapp.R;
import com.example.mannas.movieapp.SettingsActivity_module.PreferencesConstants;

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

/**
 * updates the DB if possible when ever created a new object of it
 *  <h1>How it works</h1>an AsyncTaskLoader that fill an ArrayList<{@link MovieObject}>
 *     from the Api { http://api.themoviedb.org} according to the SharedPreference Value of SortBy
 * Created by mannas on 9/2/2016.
 */
public class MoviesLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<MovieObject>> {

    final String LOG_TAG = this.getClass().getName();

    public ArrayList<MovieObject> mDownloaded_DATA_ls;

    /*****  constructing the URl ***/
    final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    SharedPreferences SP = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
    String SORT_PARAM =""; //SP.getString(PreferencesConstants.pref_SortBy_Key, PreferencesConstants.pref_SortBy_DefVal);
    String PARAM = "?api_key="+  getContext().getString( R.string.api_key);
    String Server_json = "";

    public MoviesLoader(Context context) {
        super(context);
        SORT_PARAM = SP.getString(PreferencesConstants.pref_SortBy_Key, PreferencesConstants.pref_SortBy_DefVal);
    }
    public void setSORT_PARAM(String SortBy){
        this.SORT_PARAM = SortBy;
    }

    @Override
    public ArrayList<MovieObject> loadInBackground() {
        if( Download() ){
            mDownloaded_DATA_ls = new ArrayList<>();
            parse();

            getContext().getContentResolver().delete(Contract.Movie_Entry.uri,null,null);
            getContext()
                    .getContentResolver()
                    .bulkInsert
                            (Contract.Movie_Entry.uri, MovieObject.getAsContentValues(mDownloaded_DATA_ls) );
        }
        return mDownloaded_DATA_ls;
    }

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
                MovieObject movie = new MovieObject();
                movie.poster_path = JsonItem.getString("poster_path");
                movie.adult =  JsonItem.getBoolean("adult");
                movie.overview = JsonItem.getString("overview");
                movie.release_date = JsonItem.getString("release_date");
                movie.id = Integer.valueOf(JsonItem.getString("id"));
                movie.original_title = JsonItem.getString("original_title");
                movie.title = JsonItem.getString("title");
                movie.backdrop_path = JsonItem.getString("backdrop_path");
                movie.vote_count = JsonItem.getInt("vote_count");
                movie.video = JsonItem.getBoolean("video");
                movie.vote_avg = Float.valueOf(JsonItem.getString("vote_average"));
                mDownloaded_DATA_ls.add( movie );
            }
        }
        catch (JSONException e){
            Log.e(e.getMessage(),LOG_TAG+"JSONException");
        }
        return  null;
    }

    @Override
    protected void onStartLoading() {

        if(mDownloaded_DATA_ls!=null ){
            deliverResult(mDownloaded_DATA_ls);
        }
        if (takeContentChanged() || mDownloaded_DATA_ls == null ) {
            mDownloaded_DATA_ls = new ArrayList<>();
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();

    }

    @Override
    public void onCanceled(ArrayList<MovieObject> data) {
        super.onCanceled(data);
    }

}
