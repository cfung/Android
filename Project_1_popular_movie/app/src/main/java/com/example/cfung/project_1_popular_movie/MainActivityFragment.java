package com.example.cfung.project_1_popular_movie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.Arrays;

/**
 * Created by cfung on 10/4/17.
 */

public class MainActivityFragment extends Fragment {

    private CustomAdapter movieAdapter;

    MovieModel[] movies = {

            //public MovieModel(String mName, int mImage)
            new MovieModel("movie 1", 1),

    };


    public MainActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new CustomAdapter(getActivity(), Arrays.asList(movies));

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieAdapter);

        return rootView;
    }


}
