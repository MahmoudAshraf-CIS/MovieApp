package com.example.mannas.movieapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by mannas on 9/11/2016.
 */
public class DB_RepopulateTask extends AsyncTask<Void ,Void,Void> {
    Context context;
    Uri uri;
    ContentValues[] values;

    public DB_RepopulateTask(Context context ,@NonNull Uri uri,@NonNull ContentValues[] values){
            this.context=context;
            this.uri = uri;
            this.values = values;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        context.getContentResolver().delete(Contract.Movie_Entry.uri,null,null);
        context.getContentResolver().bulkInsert(Contract.Movie_Entry.uri , values);
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
            super.onPostExecute(v);
    }


}
