package com.example.mannas.movieapp.DetailActivity_module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mannas.movieapp.MainActivity_module.MainActivity;
import com.example.mannas.movieapp.MovieObject;
import com.example.mannas.movieapp.R;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 */
public class MovieDetailActivity extends AppCompatActivity {
    MovieObject movieObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if( getIntent() !=null){
            Bundle arguments = new Bundle();
            arguments.putParcelable(
                    MovieObject.key,
                    movieObject = getIntent().getParcelableExtra(MovieObject.key) );
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.MovieDetaiActivity, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MovieObject.key, movieObject );
        super.onSaveInstanceState(outState);
    }

}
