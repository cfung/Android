package com.example.cfung.project_3_baking_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by cfung on 3/1/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private static final String TAG = "RecipeAdapter";
    //private ItemClickListener mClickListener;
    private ArrayList<RecipeModel> recipeData;
    private LayoutInflater mInflater;
    private ListItemClickListener listItemClickListener;
    Context context;

    //String BASE_POSTER_URL= "http://image.tmdb.org/t/p/w185";

    ArrayList<RecipeModel> AllRecipes = new ArrayList<RecipeModel>();

    public interface ListItemClickListener {
        void onListItemClick(RecipeModel clickedItemIndex);
    }

    public RecipeAdapter(Activity context, ArrayList<RecipeModel> recipes){
        //super(context, resources,  recipes);
        Log.v(TAG, "RecipeAdapter was called (context + 2)");
        this.mInflater = LayoutInflater.from(context);
        //this.AllRecipes =  recipes;
        this.recipeData = recipes;
    }

    public RecipeAdapter(ArrayList<RecipeModel> recipes){
        Log.v(TAG, "RecipeAdapter was called (recipes only)");
        this.recipeData = recipes;
    }

    public RecipeAdapter(ArrayList<RecipeModel> recipes, ListItemClickListener listItemClickListener) {
        Log.v(TAG, "RecipeAdapter was called (recipes + listItemClickListner)");
        this.recipeData = recipes;
        this.listItemClickListener = listItemClickListener;

    }

    public RecipeAdapter(ListItemClickListener listener) {
        Log.v(TAG, "RecipeAdapter was called (listener only)");
        this.listItemClickListener = listener;
    }
    /*
    public MyViewHolder(TextView v) {
        super(v);
        mTextView = v;
    }*/

    public void setRecipeData(ArrayList<RecipeModel> recipes, Context context) {
        Log.v(TAG, "setting recipedata..");
        this.recipeData = recipes;
        this.context = context;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        /*TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recipe, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;*/
        Log.v(TAG, "inside onCreateViewHolder...");
        Context context = parent.getContext();
        int idListItem = R.layout.list_item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(idListItem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Log.v(TAG, "inside onBindViewHolder..");
        holder.textView.setText(recipeData.get(pos).getRecipeName());
        Log.v(TAG, "getImage(): " + recipeData.get(pos).getImage());
        Log.v(TAG, "getID(): " + recipeData.get(pos).getid());
        Log.v(TAG, "getServings: " + recipeData.get(pos).getServings());
        Log.v(TAG, "getSteps: " + recipeData.get(pos).getSteps());
        String imageURL = recipeData.get(pos).getImage();

        /*Picasso.with(context)
                .load(recipeData.get(pos).getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);*/

    }

    @Override
    public int getItemCount() {
        Log.v(TAG, "what is getItemCount()..." + recipeData);
        if (recipeData != null) {
            return recipeData.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.v(TAG, "ViewHolder constructor...");
            textView = (TextView) itemView.findViewById(R.id.list_item_recipe_name);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_recipe_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            Log.v(TAG, "onClick!! " + clickedPosition);

            listItemClickListener.onListItemClick(recipeData.get(clickedPosition));

            /*if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }*/

        }
    }

    /*
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }*/

    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        RecipeModel mRecipe = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_recipe, parent, false);
        }


        TextView recipeNameView = (TextView) convertView.findViewById(R.id.list_item_recipe_name);
        Log.v(TAG, "what is getRecipeName: " + AllRecipes.get(position).getRecipeName());
        recipeNameView.setText(AllRecipes.get(position).getRecipeName());

        return convertView;
    }*/

    String getItem(int id) {
        return String.valueOf(recipeData.get(id));
    }

}
