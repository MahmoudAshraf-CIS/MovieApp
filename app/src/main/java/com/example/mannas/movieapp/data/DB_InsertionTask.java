package com.example.mannas.movieapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Created by mannas on 9/2/2016.
 */
public class DB_InsertionTask extends AsyncTask<Void ,Void,Integer> {
    Context context;
    Uri uri;
    ContentValues[] values;
    public DB_InsertionTask(Context context ,@NonNull Uri uri,@NonNull ContentValues[] values){
        this.context=context;
        this.uri = uri;
        this.values = values;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return context.getContentResolver().bulkInsert( uri, values );
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        //Toast.makeText(context,"Inserted ", Toast.LENGTH_LONG).show();

    }


}