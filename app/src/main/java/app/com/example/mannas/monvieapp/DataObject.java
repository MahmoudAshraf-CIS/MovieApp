package app.com.example.mannas.monvieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mannas on 8/13/2016.
 */
public class DataObject implements Parcelable {  // item1 Data
    public int type;
    int id;
    public String poster_path;
    public String backdrop_path;
    public String title;
    public String overview;
    public String date;
    public float vote_avg;

    public DataObject(int id, String poster_path, String backdrop_path, String title, String overview, String release_date, float vote_avg, int type) {
        this.id = id;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.overview = overview;
        this.date = release_date;
        this.vote_avg = vote_avg;
        this.type = type;
    }

    protected DataObject(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        poster_path = in.readString();
        backdrop_path = in.readString();
        title = in.readString();
        overview = in.readString();
        date = in.readString();
        vote_avg = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(date);
        dest.writeFloat(vote_avg);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataObject> CREATOR = new Parcelable.Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel in) {
            return new DataObject(in);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };
}
