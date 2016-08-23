package app.com.example.mannas.monvieapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    static DataObject item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        item = (DataObject) getIntent().getExtras().get("DataObject");
        getSupportFragmentManager().beginTransaction().add(R.id.activity_detail, new Detail_Fragment()).commit();

    }
    public static class Detail_Fragment extends Fragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view =inflater.inflate(R.layout.fragment_detail,container,false);
            if(view !=null){
                TextView title = (TextView) view.findViewById(R.id.detail_title_txtView);
                title.setText(item.title);
                ImageView poster = (ImageView) view.findViewById(R.id.detail_poster);
                Picasso.with(poster.getContext()).load("http://image.tmdb.org/t/p/w342/"+item.poster_path).into(poster);
                TextView date_inYears = (TextView) view.findViewById(R.id.detail_date_year);
                date_inYears.setText(item.date.substring(0,4));
//            TextView duration_minutes = (TextView) view.findViewById(R.id.detail_duration_minutes);
//                duration_minutes.setText("");
                RatingBar ratingBar = (RatingBar) view.findViewById(R.id.detail_ratingBar);
                ratingBar.setNumStars((int)item.vote_avg/2);
                Button MARK_AS_FAVORITE_btn = (Button) view.findViewById(R.id.detail_MARK_AS_FAVORITE_btn);
                MARK_AS_FAVORITE_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"added to the Favorite",Toast.LENGTH_SHORT).show();
                    }
                });

                TextView detail_overView = (TextView) view.findViewById(R.id.detail_overView);
                detail_overView.setText(item.overview);
            }

            return view;
        }
    }
}
