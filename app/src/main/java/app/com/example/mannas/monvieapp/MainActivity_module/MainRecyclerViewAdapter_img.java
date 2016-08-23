package app.com.example.mannas.monvieapp.MainActivity_module;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.example.mannas.monvieapp.DataObject;
import app.com.example.mannas.monvieapp.DetailActivity;
import app.com.example.mannas.monvieapp.R;

/**
 * Created by mannas on 8/13/2016.
 */
public class MainRecyclerViewAdapter_img extends RecyclerView.Adapter<MainRecyclerViewAdapter_img.imgView_Holder> {

    private ArrayList<DataObject> DATA_ls;
    private MainRecyclerViewAdapter_img thisRef;

    public MainRecyclerViewAdapter_img(ArrayList<DataObject> myDataset) {
        DATA_ls = new ArrayList<>();
        DATA_ls = myDataset;
        thisRef = this;
    }

    @Override
    public imgView_Holder onCreateViewHolder( ViewGroup parent, int viewType) {
        //get View of the item1 container in this case LinearLayout of item1.xmll
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_movie_item, parent, false);
        return new imgView_Holder(view);
    }

    @Override
    public void onBindViewHolder(final imgView_Holder imgViewHolder, final int position) {
        //updating the UI with Data givin the Ref to the UI eliments in the imgViewHolder
        //and the position of the requested item1
        final DataObject item = DATA_ls.get(position);
            //card.card_img to set img src
            Picasso.with(imgViewHolder.itemView.getContext()).load("http://image.tmdb.org/t/p/w185/"+item.poster_path).into(imgViewHolder.card_img);

            View.OnClickListener itemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Recycle Click" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( imgViewHolder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra("DataObject", item);
                    imgViewHolder.itemView.getContext().startActivity(intent);
                }};

            imgViewHolder.itemView.setOnClickListener(itemClickListener);

        }

    @Override
    public int getItemCount() {
        return DATA_ls.size();
    }

    /***********************  Classes  ****************************/
    public class imgView_Holder extends RecyclerView.ViewHolder { //item1 UI_structure in a class
        ImageView card_img;
        public imgView_Holder(View itemView) {
            super(itemView);
            card_img = (ImageView) itemView.findViewById(R.id.item_movie_poster);
        }
    }
    public void notifyDataSetChanged(ArrayList<DataObject> DATA_ls){
        this.DATA_ls.clear();
        this.DATA_ls.addAll(DATA_ls);
        super.notifyDataSetChanged();

    }

}
