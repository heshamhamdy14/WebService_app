package com.example.islamiccenter.webservice_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.islamiccenter.webservice_app.DataModels.movieDataModel;
import com.squareup.picasso.Picasso;

public class itemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent myintent =getIntent();


        movieDataModel movieModel=(movieDataModel) getIntent().getSerializableExtra("movieDataModels");


        TextView textView=(TextView) findViewById(R.id.textView);
        TextView textView2=(TextView) findViewById(R.id.textView2);
        ImageView imageView=(ImageView) findViewById(R.id.imageView3);
        RatingBar ratingBar=(RatingBar) findViewById(R.id.ratingBar);

        textView.setText(movieModel.getTitle());
        textView2.setText(movieModel.getOverview());
        float rate=(float) movieModel.getVoteAverage();
        rate=rate/2;
        ratingBar.setRating(rate);

        Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w500/"+movieModel.getPosterPath()).into(imageView);

    }
}
