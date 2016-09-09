package com.example.mannas.movieapp.DetailActivity_module.DetailAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mannas.movieapp.DetailActivity_module.MovieReview;
import com.example.mannas.movieapp.R;

import java.util.ArrayList;

/**
 * Created by mannas on 9/7/2016.
 */
public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MovieReview> Reviews;

    public ReviewRecyclerAdapter(ArrayList<MovieReview> Reviews){
        Reviews = new ArrayList<>();
        this.Reviews = Reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row, parent , false);
        return new Reviews_Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Reviews_Holder)holder).ReviewsAuthor.setText(Reviews.get(position).ReviewsAuthor);
        ((Reviews_Holder)holder).ReviewsContent.setText(Reviews.get(position).ReviewContent);
    }

    @Override
    public int getItemCount() {
        return Reviews==null?0: Reviews.size();
    }

    public void notifyDataSetChanged(ArrayList<MovieReview > Reviews){
        if(Reviews!=null){
            this.Reviews = Reviews;
            super.notifyDataSetChanged();
        }
    }
    public class Reviews_Holder extends RecyclerView.ViewHolder  { //item1 UI_structure in a class
        TextView ReviewsAuthor,ReviewsContent;

        public Reviews_Holder(View itemView) {
            super(itemView);
            ReviewsAuthor = (TextView) itemView.findViewById(R.id.author);
            ReviewsContent = (TextView) itemView.findViewById(R.id.content);
        }

    }
}
