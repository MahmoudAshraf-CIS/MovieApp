package com.example.mannas.movieapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mannas on 8/26/2016.
 */
public class DB_Helper extends SQLiteOpenHelper {
    public static final String DB_NAME = "movieApp_DB";
    public static final int DB_Version = 1;

    public DB_Helper(Context context ) {
        super(context, DB_NAME , null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(  Contract.Movie_Entry.SQL_CreationStatement );
        db.execSQL(  Contract.Fav_Entry.SQL_CreationStatement   );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL(Contract.Movie_Entry.SQL_DropStatement);
            db.execSQL(Contract.Fav_Entry.SQL_DropStatement);
            onCreate(db);
        }
    }


}
