package com.example.islamiccenter.webservice_app.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.islamiccenter.webservice_app.DataModels.movieDataModel;
import com.example.islamiccenter.webservice_app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by islamic center on 01/10/2017.
 */

public class MoviesArrayAdapter extends ArrayAdapter<movieDataModel> {
    public MoviesArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull movieDataModel[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.movieitem,parent,false);

        }
        movieDataModel movieDataModel=getItem(position);
        ImageView imageView= (ImageView)convertView.findViewById(R.id.imageView);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500/"+movieDataModel.getPosterPath()).into(imageView);

        TextView textViewTitle=(TextView)convertView.findViewById(R.id.textView_title);
        TextView textViewOver=(TextView)convertView.findViewById(R.id.textviewover);
        textViewOver.setText(movieDataModel.getOverview());
        textViewTitle.setText(movieDataModel.getTitle());

        return convertView;
    }
}

