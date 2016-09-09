package com.example.mannas.movieapp.data;

import android.content.Context;
import android.util.Log;

import com.example.mannas.movieapp.DetailActivity_module.MovieReview;
import com.example.mannas.movieapp.R;

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
 * Created by mannas on 9/7/2016.
 */
public class ReviewsLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<MovieReview>>{


    final String LOG_TAG = TrailersLoader.class.getName();

    final String BASEurl = "http://api.themoviedb.org/3/movie/";
    String key = "/reviews?api_key=" + getContext().getString( R.string.api_key);

    String queryURL;
    String Server_json="";

    ArrayList<MovieReview> Reviews;

    public ReviewsLoader(Context context, String ID) {
        super(context);
        //ID = "4880";  //test that TrailerRecyclerView empty
        queryURL = BASEurl+ID+key;
    }


    @Override
    protected void onStartLoading() {
        if(Reviews ==null){ //first time to load
            Reviews = new ArrayList<>();
            forceLoad();
        }
        else {
            deliverResult(Reviews);
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
    public void onCanceled(ArrayList<MovieReview> data) {
        super.onCanceled(data);
    }






    @Override
    public ArrayList<MovieReview> loadInBackground() {
        if(Downloaded()){
            Reviews = new ArrayList<>();
            parse();
        }
        return Reviews.size()==0? null : Reviews;
    }
    private Boolean Downloaded(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL( queryURL );

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
                JSONObject Item = results.getJSONObject(i);

                Reviews.add(new MovieReview( Item.getString("author"),Item.getString("content") ));
            }
        }
        catch (JSONException e){
            Log.e(e.getMessage(),LOG_TAG+"JSONException");
        }
        return  null;
    }
}
