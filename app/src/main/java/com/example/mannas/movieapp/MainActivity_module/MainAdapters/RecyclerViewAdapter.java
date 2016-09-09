package com.example.mannas.movieapp.MainActivity_module.MainAdapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mannas.movieapp.MovieObject;

import java.util.ArrayList;

/**
 * Created by mannas on 8/26/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<Holder> {

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void notifyDataSetChanged(ArrayList<MovieObject> DATA_ls){

    }

    public class ItemClickListener implements View.OnClickListener{
        public MovieObject movieObject;
        ItemClickListener(MovieObject movieObject){
            this.movieObject=movieObject;
        }
        @Override
        public void onClick(View view){

        }
    }

}
