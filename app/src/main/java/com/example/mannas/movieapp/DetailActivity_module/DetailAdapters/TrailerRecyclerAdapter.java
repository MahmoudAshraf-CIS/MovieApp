package com.example.mannas.movieapp.DetailActivity_module.DetailAdapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mannas.movieapp.MovieObject;
import com.example.mannas.movieapp.R;
import com.example.mannas.movieapp.data.TrailersLoader;

import java.util.ArrayList;


/**
 * Created by mannas on 9/7/2016.
 */
public class TrailerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> YouTubeKeys;

    public TrailerRecyclerAdapter(ArrayList<String> Trailers){
        YouTubeKeys = new ArrayList<>();
        this.YouTubeKeys = Trailers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_row, parent , false);
        return new Trailer_Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String TrailerTitle = "Trailer "+ (position+1 );
        final String key = YouTubeKeys.get(position);
        ((Trailer_Holder)holder).TrailerTitle.setText(TrailerTitle);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+key ))
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return YouTubeKeys==null?0: YouTubeKeys.size();
    }

    public void notifyDataSetChanged(ArrayList<String > DATA_ls){
        if(DATA_ls!=null){
            this.YouTubeKeys = DATA_ls;
            super.notifyDataSetChanged();
        }
    }
    public class Trailer_Holder extends RecyclerView.ViewHolder  { //item1 UI_structure in a class
        TextView TrailerTitle;
        public Trailer_Holder(View itemView) {
            super(itemView);
            TrailerTitle = (TextView) itemView.findViewById(R.id.TrailerTitle);
        }

    }
}
