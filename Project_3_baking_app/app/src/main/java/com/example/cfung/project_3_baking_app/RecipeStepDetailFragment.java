package com.example.cfung.project_3_baking_app;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cfung on 1/7/19.
 */

public class RecipeStepDetailFragment extends Fragment{

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private BandwidthMeter bandwidthMeter;
    private ArrayList<Steps> steps = new ArrayList<>();
    private int index;
    private Handler handler;
    ArrayList<RecipeModel> recipeModels;
    String recipeName;

    static String RECIPES = "RECIPES";
    static String SELECTED_RECIPES = "SELECTED_RECIPES";
    static String STEPS = "STEPS";
    static String INDEX = "INDEX";
    static String RECIPE_DETAIL = "RECIPE_DETAIL";
    static String RECIPE_STEP_DETAIL = "RECIPE_STEP_DETAIL";

    private static final String TAG = "RecipeStepDetailFrag";

    private ListItemClickListener listItemClickListener;

    public RecipeStepDetailFragment() {
    }

    public interface ListItemClickListener {
        void onListItemClick(List<Steps> steps, int index, String recipeName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView;
        handler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        listItemClickListener = (DetailActivity)getActivity();
        recipeModels = new ArrayList<>();

        // Completed:  add case to handle savedInstanceState != null
        if (savedInstanceState != null) {
            steps = getArguments().getParcelableArrayList(STEPS);
            index = getArguments().getInt(INDEX);
            recipeName = getArguments().getString("Title");

        }
        else {

            steps = getArguments().getParcelableArrayList(STEPS);

            if (steps != null) {
                //steps = getArguments().get
                index = getArguments().getInt(INDEX);
                Log.v(TAG, "index is...: " + index);
                recipeName = getArguments().getString("Title");
                Log.v(TAG, "recipeName is: " + recipeName);
            } else {
                recipeModels = getArguments().getParcelableArrayList(SELECTED_RECIPES);
                Log.v(TAG, "steps is NULL, in else...");
                steps = (ArrayList<Steps>) recipeModels.get(0).getSteps();
                index = 0;
            }

        }

        Log.v(TAG, "steps in stepFragment is...: " + steps);


        View rootView = inflater.inflate(R.layout.recipe_step_fragment, container, false);
        textView = (TextView) rootView.findViewById(R.id.step_detail_text);
        textView.setText(steps.get(index).getDescription());
        textView.setVisibility(View.VISIBLE);

        exoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exoplayer);
        exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoURL = steps.get(index).getVideoURL();
        Log.v(TAG, "videoURL is...:" + videoURL);

        String imageURL = steps.get(index).getThumbnailURL();
        Log.v(TAG, "imageURL is...: " + imageURL);
        if (!imageURL.isEmpty()) {
            Uri builtUri = Uri.parse(imageURL).buildUpon().build();
            ImageView image = (ImageView) rootView.findViewById(R.id.detail_image);
            Picasso.with(getContext()).load(builtUri).into(image);

        }

        if (!videoURL.isEmpty()) {

            initializeExoPlayer(Uri.parse(steps.get(index).getVideoURL()));

            if (rootView.findViewWithTag("sw600dp-land-recipe_step_detail") != null) {
                getActivity().findViewById(R.id.fragment_container_2).setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            }
            else if (landscapeMode(getContext())) {
                textView.setVisibility(View.GONE);
            }
        } // completed:  add else
        else {
            exoPlayer = null;
            exoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300,300));
        }


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEPS, steps);
        outState.putString("Title", recipeName);
        outState.putInt(INDEX, index);
    }

    private void initializeExoPlayer(Uri uri) {
        if (exoPlayer == null){

            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(factory);
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            exoPlayerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
            DefaultExtractorsFactory extratorsfactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extratorsfactory, null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

        }
    }

    private boolean landscapeMode(Context context) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

}
