package com.example.mannas.movieapp.DetailActivity_module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mannas on 9/7/2016.
 */
public class MovieReview implements Parcelable {
    public String ReviewsAuthor;
    public String ReviewContent;

    public MovieReview( String ReviewsAuthor, String ReviewContent ){
        this.ReviewContent = ReviewContent;
        this.ReviewsAuthor = ReviewsAuthor;
    }

    protected MovieReview(Parcel in) {
        ReviewsAuthor = in.readString();
        ReviewContent = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ReviewsAuthor);
        dest.writeString(ReviewContent);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieReview> CREATOR = new Parcelable.Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };
}
