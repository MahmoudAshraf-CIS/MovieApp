package app.com.example.mannas.monvieapp.MainActivity_module;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.example.mannas.monvieapp.DataObject;
import app.com.example.mannas.monvieapp.DetailActivity;
import app.com.example.mannas.monvieapp.R;

/**
 * Created by mannas on 8/9/2016.
 */

public class MainRecyclerViewAdapter_Cards extends RecyclerView.Adapter<MainRecyclerViewAdapter_Cards.Holder> {

    private ArrayList<DataObject> DATA_ls;
    private MainRecyclerViewAdapter_Cards thisRef;

    public MainRecyclerViewAdapter_Cards(ArrayList<DataObject> myDataset) {
        DATA_ls = new ArrayList<>();
        DATA_ls = myDataset;
        thisRef = this;
    }

    @Override
    public Holder onCreateViewHolder( ViewGroup parent, int viewType) {
        //get View of the item1 container in this case LinearLayout of item1.xmll
        View view;
        view = LayoutInflater.from( parent.getContext()).inflate(R.layout.card_movie_item, parent, false);
        if(viewType == 1) // it's a CardView
            return new card_viewHolder(view);
        return new Holder(view);
    }



    @Override
    public int getItemViewType(int position) {
        //returns 1 is a normal item , 2 if detail item
        return DATA_ls.get(position).type==1 ? 1:2;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        //updating the UI with Data givin the Ref to the UI eliments in the holder
        //and the position of the requested item1
        final DataObject item = DATA_ls.get(position);
        if(item.type == 1){
            card_viewHolder card = (card_viewHolder) holder;
            //card.card_img to set img src
//            if(MainActivity.isConnected)
                Picasso.with(card.itemView.getContext()).load("http://image.tmdb.org/t/p/w185/"+item.poster_path).into(card.card_img);
//            else
//                Picasso.with(card.itemView.getContext()).load("http://image.tmdb.org/t/p/w185/"+item.poster_path).networkPolicy(NetworkPolicy.OFFLINE).into(card.card_img);
            card.card_title.setText(item.title);
            card.card_date.setText(item.date);
            View.OnClickListener itemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Recycle Click" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( holder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra("DataObject", item);
                    holder.itemView.getContext().startActivity(intent);

                    // TODO: 8/13/2016
                }};

            card.card_footer.setOnClickListener(itemClickListener);
            card.itemView.setOnClickListener(itemClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return DATA_ls.size();
    }

    /***********************  Classes  ****************************/
    public static class Holder extends RecyclerView.ViewHolder { //item1 UI_structure in a class

        public Holder(View itemView) {
            super(itemView);
        }
    }
    public static class card_viewHolder extends Holder {
         ImageView card_img;
         TextView card_title;
         TextView card_date;
         LinearLayout card_footer;
         public card_viewHolder(View itemView) {
             super(itemView);
             card_img = (ImageView) itemView.findViewById(R.id.card_img);
             card_title = (TextView) itemView.findViewById(R.id.card_title);
             card_date = (TextView) itemView.findViewById(R.id.card_date);
             card_footer = (LinearLayout) itemView.findViewById(R.id.card_footer);
         }
     }

    public void notifyDataSetChanged(ArrayList<DataObject> DATA_ls){
            this.DATA_ls.clear();
            this.DATA_ls.addAll(DATA_ls);
            super.notifyDataSetChanged();

    }

}