package com.example.cfung.custom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import java.util.ArrayList;

/**
 * Created by cfung on 10/2/17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context){
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v){

        int position=(Integer) v.getTag();
        Object object = getItem(position);
        DataModel dataModel = (DataModel)object;

        switch (v.getId()){
            case R.id.item_info:
                Snackbar.make(v, "Release data " + dataModel.getFeature(), Snackbar.LENGTH_LONG).setAction("No Action", null).show();
                break;
        }

    }
    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        DataModel dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);

        return convertView;
    }

}
