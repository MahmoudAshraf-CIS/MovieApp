package com.example.mannas.movieapp.DetailActivity_module;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mannas.movieapp.DetailActivity_module.DetailAdapters.ReviewRecyclerAdapter;
import com.example.mannas.movieapp.DetailActivity_module.DetailAdapters.TrailerRecyclerAdapter;
import com.example.mannas.movieapp.MainActivity_module.MainActivity;
import com.example.mannas.movieapp.MovieObject;
import com.example.mannas.movieapp.R;
import com.example.mannas.movieapp.data.Contract;
import com.example.mannas.movieapp.data.DB_DeletionTask;
import com.example.mannas.movieapp.data.DB_InsertionTask;
import com.example.mannas.movieapp.data.ReviewsLoader;
import com.example.mannas.movieapp.data.TrailersLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */

public class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks  {

    private MovieObject mItem;
    boolean mItemInitialyInFav = false;
    boolean mItemStateInFav = false;


    final int RowQueryID = 2;
    FloatingActionButton fab;

    final int TrailerLoaderID = 0;
    TrailerRecyclerAdapter trailerRecyclerAdapter;
    TextView noTrailersTxt;

    final int ReviewsLoaderID = 1;
    ReviewRecyclerAdapter reviewRecyclerAdapter;
    TextView noReviewsTxt;


    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(MovieObject.key)) {
            mItem = (MovieObject) getArguments().get(MovieObject.key);
        }
        // Load Trailers
        trailerRecyclerAdapter = new TrailerRecyclerAdapter(new ArrayList<String>());
        getLoaderManager().initLoader(TrailerLoaderID,null,this);

        // Load Reviews
        reviewRecyclerAdapter = new ReviewRecyclerAdapter(new ArrayList<MovieReview>());
        getLoaderManager().initLoader(ReviewsLoaderID,null,this);

        // query the Fav
        getLoaderManager().initLoader(RowQueryID,null,this);
    }

    void UpdateFavUI(){
        if(getActivity() instanceof MainActivity){
            getLoaderManager().restartLoader(MainActivity.DB_FavLoader,null, ((MainActivity) getActivity()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str="";
                if(mItemStateInFav ){ // now it is in Fav so delete it
                    str = "Removed from Your Fav";
                    fab.setImageResource(R.drawable.ic_add);
                    new DB_DeletionTask(getContext(), Contract.Fav_Entry.uri, Contract.Fav_Entry.columns.ID+" = ?", new String[]{ mItem.id.toString()}
                    ).execute();
                    UpdateFavUI();
                }
                else {
                    str = "Added To your Fav";
                    fab.setImageResource(R.drawable.ic_delete);
                    new DB_InsertionTask(getContext(),Contract.Fav_Entry.uri,MovieObject.getAsContentValues(mItem)).execute();
                    UpdateFavUI();
                }
                mItemStateInFav = !mItemStateInFav;

                Snackbar.make(view, str, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        ImageView poster = (ImageView) rootView.findViewById(R.id.posterImg);
        Picasso.with(poster.getContext()).load("http://image.tmdb.org/t/p/w342/"+mItem.backdrop_path).into(poster);

        String lanAcro = mItem.original_language;
        String languageVal = ( new Locale(lanAcro)).getDisplayLanguage();
        TextView language = (TextView) rootView.findViewById(R.id.LanguageTxt);
        language.setText(languageVal.toUpperCase());

        TextView title = (TextView) rootView.findViewById(R.id.detail_title_txtView);
        title.setText(mItem.title);

        TextView original_title = (TextView) rootView.findViewById(R.id.original_title);
        original_title.setText(mItem.original_title);

        ImageView backdrop = (ImageView) rootView.findViewById(R.id.detail_backdrop);
        Picasso.with(backdrop.getContext()).load("http://image.tmdb.org/t/p/w342/"+mItem.poster_path).into(backdrop);

        TextView date_inYears = (TextView) rootView.findViewById(R.id.detail_date_year);
        date_inYears.setText( "Released : " +mItem.release_date.substring(0,4));

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.detail_ratingBar);
        ratingBar.setRating(mItem.vote_avg/2);


        TextView detail_overView = (TextView) rootView.findViewById(R.id.detail_overView);
        detail_overView.setText(mItem.overview);

        noTrailersTxt = (TextView) rootView.findViewById(R.id.noTrailersTxt);
        RecyclerView trailerRecyclerView = (RecyclerView) rootView.findViewById(R.id.trailers);
        trailerRecyclerView.setAdapter(trailerRecyclerAdapter);
        trailerRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1) );

        noReviewsTxt = (TextView) rootView.findViewById(R.id.noReviewsTxt);
        RecyclerView reviewsRecyclerView = (RecyclerView) rootView.findViewById(R.id.reviews);
        reviewsRecyclerView.setAdapter(reviewRecyclerAdapter);
        reviewsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1) );

        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
//        if(mItemStateInFav != mItemInitialyInFav ){ // change required
//            if(mItemStateInFav){ //add
//                new DB_InsertionTask(getContext(),Contract.Fav_Entry.uri,MovieObject.getAsContentValues(mItem)).execute();
//            }else { // remove
//                new DB_DeletionTask(getContext(), Contract.Fav_Entry.uri,
//                        Contract.Fav_Entry.columns.ID+" = ?",
//                        new String[]{ mItem.id.toString()}
//                ).execute();
//            }
//        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if(id == TrailerLoaderID){
            return  new TrailersLoader(getContext(),mItem.id.toString());
        }
        else if(id == ReviewsLoaderID){
            return  new ReviewsLoader(getContext(),mItem.id.toString());
        }else if(id == RowQueryID){
            return new CursorLoader(getContext(),
                    Contract.Fav_Entry.uri, new String[]{Contract.Fav_Entry.columns.ID },
                    Contract.Fav_Entry.columns.ID+" = "+mItem.id ,
                    null,
                    null );
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(data!=null){
            //notifay the Trailer Adapter
            if(loader.getId()==TrailerLoaderID){
                ArrayList<String>ls  = ((ArrayList<String>)data);
                if(ls.size()!=0){
                    trailerRecyclerAdapter.notifyDataSetChanged(ls);
                    noTrailersTxt.setVisibility(View.GONE);
                }
                else {
                    noTrailersTxt.setVisibility(View.VISIBLE);
                }
            }
            else if(loader.getId()==ReviewsLoaderID){
                ArrayList<MovieReview> ls = ((ArrayList<MovieReview>) data);
                if(ls.size()!=0){
                    reviewRecyclerAdapter.notifyDataSetChanged(ls);
                    noReviewsTxt.setVisibility(View.GONE);
                }else{
                    noReviewsTxt.setVisibility(View.VISIBLE);
                }

            }else if (loader.getId()==RowQueryID){
                Cursor cursor = ((Cursor) data);
                //// TODO: 9/8/2016 the fab icon initialy
                if(cursor.moveToFirst()){ // there is date in the cursor So it is in the Fav
                    //Toast.makeText(getContext(),"found",Toast.LENGTH_LONG).show();
                    mItemInitialyInFav = mItemStateInFav = true;
                    fab.setImageResource(R.drawable.ic_delete);
                }
                else {
                    //Toast.makeText(getContext()," Not found",Toast.LENGTH_LONG).show();
                    //new DB_InsertionTask(getContext(),Contract.Fav_Entry.uri).execute(MovieObject.getAsContentValue(mItem));
                    mItemInitialyInFav = mItemStateInFav = false;
                    fab.setImageResource(R.drawable.ic_add);
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
