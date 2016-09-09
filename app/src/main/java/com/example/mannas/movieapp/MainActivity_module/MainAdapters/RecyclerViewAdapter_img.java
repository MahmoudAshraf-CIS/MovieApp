package com.example.mannas.movieapp.MainActivity_module.MainAdapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mannas.movieapp.MainActivity_module.MainActivity;
import com.example.mannas.movieapp.DetailActivity_module.MovieDetailActivity;
import com.example.mannas.movieapp.DetailActivity_module.MovieDetailFragment;
import com.example.mannas.movieapp.MovieObject;
import com.example.mannas.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mannas on 8/13/2016.
 */
public class RecyclerViewAdapter_img extends RecyclerViewAdapter {

    private ArrayList<MovieObject> DATA_ls;
    boolean hasSellection=false;
    public RecyclerViewAdapter_img(ArrayList<MovieObject> myDataset) {
        DATA_ls = new ArrayList<>();
        DATA_ls = myDataset;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get View of the item1 container in this case LinearLayout of item1.xmll
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_img, parent , false);

        return new imgView_Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        //updating the UI with Data givin the Ref to the UI eliments in the imgViewHolder
        //and the position of the requested item1

            Picasso.with(holder.itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w185/"+DATA_ls.get(position).poster_path)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error_lading)
                    .into( ( (imgView_Holder) holder).card_img);


            final boolean twoPan =((MainActivity) ((imgView_Holder) holder).itemView.getContext()).isTwoPane();

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

        ((imgView_Holder) holder).itemView.setOnClickListener(twoPan?twoPaneListener:onePaneListener);
        if( position==0 && twoPan && !hasSellection ){
            twoPaneListener.onClick(((imgView_Holder) holder).itemView);
            hasSellection=true;
        }

    }

    @Override
    public int getItemCount() {
        return DATA_ls==null?0: DATA_ls.size();
    }

    /** retain selection **/
    /***********************  Classes  ****************************/
    public class imgView_Holder extends Holder  { //item1 UI_structure in a class

        ImageView card_img;

        public imgView_Holder(View itemView) {
            super(itemView);

            card_img = (ImageView) itemView.findViewById(R.id.item_movie_poster);
        }

    }

    @Override
    public void notifyDataSetChanged(ArrayList<MovieObject> DATA_ls){
        if(DATA_ls!=null){
            this.DATA_ls = DATA_ls;
            super.notifyDataSetChanged();
        }
    }


}
