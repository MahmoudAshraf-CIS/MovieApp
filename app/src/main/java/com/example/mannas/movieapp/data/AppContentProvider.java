package com.example.mannas.movieapp.data;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mannas on 8/26/2016.
 */
public class AppContentProvider extends android.content.ContentProvider {
    DB_Helper mDBHelper;
    final String LOG_TAG = AppContentProvider.class.getName();

    @Override
    public boolean onCreate() {
        mDBHelper = new DB_Helper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase DB = mDBHelper.getReadableDatabase();
        Cursor cursor;
        if(uri == Contract.Movie_Entry.uri){
            cursor = DB.query(Contract.Movie_Entry.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
        }
        else  if(uri == Contract.Fav_Entry.uri){
            cursor = DB.query(Contract.Fav_Entry.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
        }
        else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues Row) {

        SQLiteDatabase DB = mDBHelper.getWritableDatabase();
        if(uri == Contract.Movie_Entry.uri) {
           long _id = DB.insert(Contract.Movie_Entry.TABLE_NAME,null,Row);

            return Contract.Movie_Entry.uri.buildUpon().appendPath( String.valueOf(_id)).build();
        }else if (uri == Contract.Fav_Entry.uri){
            long _id = DB.insert(Contract.Fav_Entry.TABLE_NAME,null,Row);
            return Contract.Fav_Entry.uri.buildUpon().appendPath( String.valueOf(_id)).build();
        }

        return uri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = 0;
        if(uri == Contract.Movie_Entry.uri) {
            SQLiteDatabase sqlDB = mDBHelper.getWritableDatabase();
            sqlDB.beginTransaction();
            try {
                for (ContentValues cv : values) {
                    try{
                        long newID = sqlDB.insertOrThrow(Contract.Movie_Entry.TABLE_NAME , null, cv);
                    }
                    catch (SQLException e){
                        Log.e(LOG_TAG,e.getMessage());
                        numInserted--;
                    }
                    numInserted++;
                }
                sqlDB.setTransactionSuccessful();
                if(getContext()!=null)
                    getContext().getContentResolver().notifyChange(uri, null);
                else {
                    Log.e(this.getClass().getName(),"getContext() returns null !");
                }

            } finally {
                sqlDB.endTransaction();

            }
        }
        else if(uri == Contract.Fav_Entry.uri){
            SQLiteDatabase sqlDB = mDBHelper.getWritableDatabase();
            sqlDB.beginTransaction();
            try {
                for (ContentValues cv : values) {
                    try{
                        long newID = sqlDB.insertOrThrow(Contract.Fav_Entry.TABLE_NAME , null, cv);
                    }
                    catch (SQLException e){
                        Log.e(LOG_TAG,e.getMessage());
                        numInserted--;
                    }
                    numInserted++;
                }
                sqlDB.setTransactionSuccessful();
                if(getContext()!=null)
                    getContext().getContentResolver().notifyChange(uri, null);
                else {
                    Log.e(this.getClass().getName(),"getContext() returns null !");
                }

            } finally {
                sqlDB.endTransaction();
            }
        }

        return numInserted;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase DB = mDBHelper.getWritableDatabase();
        int deletedCount=0;
        if(uri == Contract.Movie_Entry.uri && selection== null && selectionArgs==null){ // delete the MovieTable
           deletedCount = DB.delete(Contract.Movie_Entry.TABLE_NAME,null,null);

        }else if(uri == Contract.Fav_Entry.uri&& selection== null && selectionArgs==null){ //delete the Fav Table
            DB.delete(Contract.Fav_Entry.TABLE_NAME,null,null);

        }else if(uri == Contract.Fav_Entry.uri && (selection != null || selectionArgs !=null ) ) { //delete from FavTable
            deletedCount = DB.delete(Contract.Fav_Entry.TABLE_NAME, selection, selectionArgs);
        }
        return deletedCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
