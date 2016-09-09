package com.example.mannas.movieapp.data;


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mannas on 8/26/2016.
 */
public class Contract {
    public static final String CONTENT_AUTHORITY = "app.com.example.mannas.movieapp";

    public static final Uri Base_CONTENT_RUI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static class Movie_Entry implements BaseColumns {
        public static final String TABLE_NAME ="Movie";
        public static Uri uri = Base_CONTENT_RUI.buildUpon().appendPath(TABLE_NAME).build();

        public static class columns {
            //NOTE :- Editing in columns Name REQUIRE Editing SQL_CreationStatement ;
            public static final String ID                   ="ID";
            public static final String adult                = "adult";
            public static final String poster_path          = "poster_path";
            public static final String backdrop_path        ="backdrop_path";
            public static final String title                = "title";
            public static final String overview             = "overview";
            public static final String release_date         ="release_date";
            public static final String vote_avg             ="vote_avg";
            public static final String original_title       ="original_title";
            public static final String original_language    ="original_language";
            public static final String popularity           ="popularity";
            public static final String vote_count           ="vote_count";
            public static final String video                ="video";
        }
        public static final String SQL_DropStatement = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        public static final String SQL_CreationStatement =
                "CREATE TABLE "+ TABLE_NAME +" (" +
                " ID INTEGER PRIMARY KEY ON CONFLICT ABORT DEFAULT (0),"+
                "adult             BOOLEAN DEFAULT false,"+
                "poster_path       TEXT  NOT NULL,"+
                "backdrop_path     TEXT  NOT NULL,"+
                "title             TEXT  NOT NULL,"+
                "overview          TEXT  NOT NULL,"+
                "release_date      DATE,"+
                "vote_avg          DECIMAL,"+
                "original_title    TEXT  NOT NULL,"+
                "original_language TEXT  NOT NULL,"+
                "popularity        DECIMAL,"+
                "vote_count        INTEGER,"+
                "video             BOOLEAN"+
                ");" ;

    }
    public static class Fav_Entry implements BaseColumns {
        public static final String TABLE_NAME ="Fav";

        public static Uri uri = Base_CONTENT_RUI.buildUpon().appendPath(TABLE_NAME).build();

        public static class columns {
            //NOTE :- Editing in columns Name REQUIRE Editing SQL_CreationStatement ;
            public static final String ID                   ="ID";
            public static final String adult                = "adult";
            public static final String poster_path          = "poster_path";
            public static final String backdrop_path        ="backdrop_path";
            public static final String title                = "title";
            public static final String overview             = "overview";
            public static final String release_date         ="release_date";
            public static final String vote_avg             ="vote_avg";
            public static final String original_title       ="original_title";
            public static final String original_language    ="original_language";
            public static final String popularity           ="popularity";
            public static final String vote_count           ="vote_count";
            public static final String video                ="video";
        }
        public static final String SQL_DropStatement = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        public static final String SQL_CreationStatement =
                "CREATE TABLE "+ TABLE_NAME +" (" +
                        " ID INTEGER PRIMARY KEY ON CONFLICT ABORT DEFAULT (0),"+
                        "adult             BOOLEAN DEFAULT false,"+
                        "poster_path       TEXT  NOT NULL,"+
                        "backdrop_path     TEXT  NOT NULL,"+
                        "title             TEXT  NOT NULL,"+
                        "overview          TEXT  NOT NULL,"+
                        "release_date      DATE,"+
                        "vote_avg          DECIMAL,"+
                        "original_title    TEXT  NOT NULL,"+
                        "original_language TEXT  NOT NULL,"+
                        "popularity        DECIMAL,"+
                        "vote_count        INTEGER,"+
                        "video             BOOLEAN"+
                        ");" ;

    }

}
