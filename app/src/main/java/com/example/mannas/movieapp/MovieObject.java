package com.example.mannas.movieapp;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mannas on 8/13/2016.
 */
public class MovieObject implements Parcelable {

    public static final String key = "MovieObject";
    public static final String ArrayListKey = "ArrayList<MovieObject>";

    public Boolean adult;
    public Integer id;
    public String poster_path;
    public String backdrop_path;
    public String title;
    public String overview;
    public String release_date;
    public float vote_avg;

    public String original_title;
    public String original_language;
    public float popularity;
    public Integer vote_count;
    public Boolean video;

    public MovieObject() {
        adult = video = false;
        popularity = vote_avg =0.0f;
        id = 0000;
        vote_count = 1111;
        poster_path ="posterPath";
        backdrop_path = "backPath";
        title = "title";
        overview = "overView";
        release_date = "2016";
        original_title ="original"+title;
        original_language="en";
    }

    public MovieObject(Boolean adult, Integer id, String poster_path, String backdrop_path, String title, String overview, String release_date, float vote_avg, String original_title, String original_language, float popularity, Integer vote_count, Boolean video) {
        this.adult = adult;
        this.id = id;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_avg = vote_avg;
        this.original_title = original_title;
        this.original_language = original_language;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
    }

    protected MovieObject(Parcel in) {
        byte adultVal = in.readByte();
        adult = adultVal == 0x02 ? null : adultVal != 0x00;
        id = in.readByte() == 0x00 ? null : in.readInt();
        poster_path = in.readString();
        backdrop_path = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_avg = in.readFloat();
        original_title = in.readString();
        original_language = in.readString();
        popularity = in.readFloat();
        vote_count = in.readByte() == 0x00 ? null : in.readInt();
        byte videoVal = in.readByte();
        video = videoVal == 0x02 ? null : videoVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (adult == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (adult ? 0x01 : 0x00));
        }
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeFloat(vote_avg);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeFloat(popularity);
        if (vote_count == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(vote_count);
        }
        if (video == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (video ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieObject> CREATOR = new Parcelable.Creator<MovieObject>() {
        @Override
        public MovieObject createFromParcel(Parcel in) {
            return new MovieObject(in);
        }

        @Override
        public MovieObject[] newArray(int size) {
            return new MovieObject[size];
        }
    };
}