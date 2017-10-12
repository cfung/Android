package com.example.cfung.project_1_popular_movie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

/**
 * Created by cfung on 10/4/17.
 */

public class MainActivityFragment extends Fragment {

    private CustomAdapter flavorAdapter;

    DataModel[] androidFlavors = {

            new DataModel("Cupcake", "1.5", R.drawable.cupcake),
            new DataModel("Donut", "1.6", R.drawable.donut),
            new DataModel("Eclair", "2.0-2.1", R.drawable.eclair),
            new DataModel("Froyo", "2.2-2.2.3", R.drawable.froyo),
            new DataModel("GingerBread", "2.3-2.3.7", R.drawable.gingerbread),
            new DataModel("Honeycomb", "3.0-3.2.6", R.drawable.honeycomb),
            new DataModel("Ice Cream Sandwich", "4.0-4.0.4", R.drawable.icecream),
            new DataModel("Jelly Bean", "4.1-4.3.1", R.drawable.jellybean),
            new DataModel("Kitkat", "4.4-4.4.4", R.drawable.kitkat),
            new DataModel("Lollipop", "5.0", R.drawable.lollipop),
            new DataModel("Marshmallow", "6.0", R.drawable.marshmallow),
            new DataModel("Nougat", "7.0", R.drawable.nougat),
            new DataModel("Oreo", "8.0", R.drawable.oreo)

    };


    public MainActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        flavorAdapter = new CustomAdapter(getActivity(), Arrays.asList(androidFlavors));

        ListView listView = (ListView) rootView.findViewById(R.id.listview_flavor);
        listView.setAdapter(flavorAdapter);

        return rootView;
    }


}
