package com.example.cfung.project_3_baking_app;

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

        // TODO:  add case to handle savedInstanceState != null
        steps = getArguments().getParcelableArrayList("steps");
        Log.v(TAG, "steps in stepFragment is...: " + steps);
        if (steps != null) {
            //steps = getArguments().get
            index = getArguments().getInt("index");
            Log.v(TAG, "index is...: " + index);
            recipeName = getArguments().getString("Title");
            Log.v(TAG, "recipeName is: " + recipeName);
        } else {
            Log.v(TAG, "steps is NULL, in else...");
            steps = (ArrayList<Steps>) recipeModels.get(0).getSteps();
            index = 0;
        }

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
        }


        return rootView;
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

}
