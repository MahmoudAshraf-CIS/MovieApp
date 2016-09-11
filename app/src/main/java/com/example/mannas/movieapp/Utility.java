package com.example.mannas.movieapp;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.mannas.movieapp.data.Contract;

import java.util.ArrayList;

/**
 * Created by mannas on 9/10/2016.
 */
public class Utility {
    public static ArrayList<MovieObject> CursorToArrayList(Cursor cursor){
        ArrayList<MovieObject> ls = new ArrayList<>();
        if(cursor!=null) {
            if (cursor.moveToFirst()) {
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
        }
        return ls;
    }
    public static ContentValues AsContentValue(MovieObject m ){
        ContentValues values = new ContentValues();

        values.put(Contract.Movie_Entry.columns.ID,  m.id );
        values.put(Contract.Movie_Entry.columns.adult, m.adult );
        values.put(Contract.Movie_Entry.columns.backdrop_path, m.backdrop_path );
        values.put(Contract.Movie_Entry.columns.original_language, m.original_language);
        values.put(Contract.Movie_Entry.columns.original_title, m.original_title);
        values.put(Contract.Movie_Entry.columns.overview, m.overview);
        values.put(Contract.Movie_Entry.columns.popularity, m.popularity );
        values.put(Contract.Movie_Entry.columns.poster_path,m.poster_path );
        values.put(Contract.Movie_Entry.columns.release_date, m.release_date);
        values.put(Contract.Movie_Entry.columns.vote_avg,m.vote_avg );
        values.put(Contract.Movie_Entry.columns.vote_count, m.vote_count);
        values.put(Contract.Movie_Entry.columns.video, m.video);
        values.put(Contract.Movie_Entry.columns.title, m.title);

        return values;
    }

    public static ContentValues[] AsContentValues(ArrayList<MovieObject> ls){
        ContentValues[] c = new ContentValues[ls.size()];
        for(int i=0;i<ls.size();i++){
            c[i] = AsContentValue(ls.get(i));
        }
        return c;
    }
    public static ContentValues[] AsContentValues(MovieObject movieObject){
        ContentValues[] c = new ContentValues[1];
        c[0] = AsContentValue(movieObject);
        return c;
    }

     public static class Loaders{
         public static final int MovieLoader_ID = 0;
         public static final int FavLoader_ID = 1;
         public static final int RowQueryID = 2;
         public static final int TrailerLoaderID = 3;
         public static final int ReviewsLoaderID = 4;
     }
}
