package com.example.mannas.movieapp.data;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by mannas on 9/8/2016.
 */
public class DB_DeletionTask extends AsyncTask<Void ,Void,Integer> {
    Context context;
    Uri uri;
    String where;
    String[] selectionArgs;

    public DB_DeletionTask(Context context , @NonNull Uri uri, @Nullable String where,@Nullable String[] selectionArgs){
        this.context=context;
        this.uri = uri;
        this.where = where;
        this.selectionArgs = selectionArgs;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return context.getContentResolver().delete(uri,where,selectionArgs);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }


}