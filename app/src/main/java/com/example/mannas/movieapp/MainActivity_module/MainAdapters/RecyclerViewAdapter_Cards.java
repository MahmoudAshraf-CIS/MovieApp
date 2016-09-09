package com.example.mannas.movieapp.MainActivity_module.MainAdapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mannas.movieapp.DetailActivity_module.MovieDetailActivity;
import com.example.mannas.movieapp.DetailActivity_module.MovieDetailFragment;
import com.example.mannas.movieapp.MainActivity_module.MainActivity;
import com.example.mannas.movieapp.MovieObject;
import com.example.mannas.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mannas on 8/9/2016.
 */

public class RecyclerViewAdapter_Cards extends RecyclerViewAdapter {

    private ArrayList<MovieObject> DATA_ls;
    boolean hasSellection=false;

    public RecyclerViewAdapter_Cards(ArrayList<MovieObject> myDataset) {
        DATA_ls = new ArrayList<>();
        DATA_ls = myDataset;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get View of the item1 container in this case LinearLayout of item1.xmll
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.movie_item_card, parent, false);
        return new card_viewHolder(view);
    }



    @Override
    public void onBindViewHolder( Holder holder,  int position) {
        //updating the UI with Data givin the Ref to the UI eliments in the holder
        //and the position of the requested item1

        card_viewHolder card = (card_viewHolder) holder;

        Picasso.with(card.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w342/" + DATA_ls.get(position).backdrop_path)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_lading)
                .into(card.card_img);

        card.card_title.setText(DATA_ls.get(position).title);
        card.card_date.setText(DATA_ls.get(position).release_date);

        final boolean twoPan =((MainActivity) ((card_viewHolder) holder).itemView.getContext()).isTwoPane();

        ItemClickListener onePaneListener = new ItemClickListener(DATA_ls.get(position)){
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieObject.key, movieObject );
                context.startActivity(intent);
            }
        };
        ItemClickListener twoPaneListener = new ItemClickListener(DATA_ls.get(position)){
            @Override
            public void onClick(View view) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(MovieObject.key, movieObject);
                MovieDetailFragment fragment = new MovieDetailFragment();
                fragment.setArguments(arguments);
                ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_pane , fragment)
                        .commit();
            }
        };

        ((card_viewHolder) holder).itemView.setOnClickListener(twoPan?twoPaneListener:onePaneListener);
        if( position==0 && twoPan && !hasSellection ){
            twoPaneListener.onClick(((card_viewHolder) holder).itemView);
            hasSellection=true;
        };
    }
    @Override
    public int getItemCount() {
        return DATA_ls==null?0: DATA_ls.size();
    }


    public class card_viewHolder extends Holder {
         ImageView card_img;
         TextView card_title;
         TextView card_date;

         public card_viewHolder(View itemView) {
             super(itemView);
             card_img = (ImageView) itemView.findViewById(R.id.card_img);
             card_title = (TextView) itemView.findViewById(R.id.card_title);
             card_date = (TextView) itemView.findViewById(R.id.card_date);
         }
     }

    public void notifyDataSetChanged(ArrayList<MovieObject> DATA_ls){
        if(DATA_ls!=null){
            this.DATA_ls.clear();
            this.DATA_ls.addAll(DATA_ls);
            super.notifyDataSetChanged();
        }
    }

}