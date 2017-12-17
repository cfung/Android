package com.example.cfung.project_1_popular_movie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by cfung on 10/4/17.
 */

public class MainActivityFragment extends Fragment {

    private CustomAdapter movieAdapter;

    /*MovieModel[] movies = {

            //public MovieModel(String mName, int mImage)
            new MovieModel(getMovieName(), getMovieLink()),
    };*/
    ArrayList<MovieModel> movieModelArrayList = new ArrayList<MovieModel>();

    //movieModelArrayList.add(movie);



    public MainActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        MovieModel movie = new MovieModel("movie 1", "");
        movieModelArrayList.add(movie);
        movieAdapter = new CustomAdapter(getActivity(), movieModelArrayList);

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieAdapter);

        return rootView;
    }


}
