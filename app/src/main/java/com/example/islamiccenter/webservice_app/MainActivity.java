package com.example.islamiccenter.webservice_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.islamiccenter.webservice_app.Adapters.MoviesArrayAdapter;
import com.example.islamiccenter.webservice_app.DataModels.movieDataModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    movieDataModel[] movieDataModels;
    EditText year;
    MoviesArrayAdapter moviesArrayAdapter;
    ListView lst_movies;

     String ReleaseDateAsc = "release_date.asc";
     String PopularityAsc = "popularity.asc";
    String ReleaseDatedesc = "release_date.desc";
    String Popularitydesc = "popularity.desc";

     RadioGroup radioGroup ;
    RadioButton btn_releaseDateasc ;
    RadioButton btn_popularityasc ;
    RadioButton btn_releaseDatedesc ;
    RadioButton btn_popularitydesc ;
    Button search;
    String sort_By_Varible;
    String YearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
         btn_releaseDateasc = (RadioButton) findViewById(R.id.radioButtonReleaseDateasc);
         btn_popularityasc = (RadioButton) findViewById(R.id.popularityasc);
         btn_releaseDatedesc = (RadioButton) findViewById(R.id.radioButtonReleaseDatedesc);
         btn_popularitydesc = (RadioButton) findViewById(R.id.popularitydesc);
        year =(EditText)findViewById(R.id.editText);
         search = (Button) findViewById(R.id.buttonSearch);
        lst_movies = (ListView) findViewById(R.id.movielist);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButtonReleaseDateasc:
                        sort_By_Varible = "release_date.asc";
                        YearList=year.getText().toString();
                        break;
                    case R.id.popularityasc:
                        sort_By_Varible = "popularity.asc";
                        YearList=year.getText().toString();
                        break;
                    case R.id.radioButtonReleaseDatedesc:
                        sort_By_Varible = "release_date.desc";
                        YearList=year.getText().toString();
                        break;
                    case R.id.popularitydesc:
                        sort_By_Varible = "popularity.desc";
                        YearList=year.getText().toString();
                        break;
                }

                String url = "http://api.themoviedb.org/3/discover/movie?api_key=ab71bd22b9e87623e779800766e9ef81&language=en-US&sort_by=" + sort_By_Varible + "&page=1";

                executeWebService(url);

            }

        });
    }

    private void executeWebService(String url) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final Gson gson = new Gson();



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");


                            movieDataModels = gson.fromJson(jsonArray.toString(), movieDataModel[].class);
                          //  Toast.makeText(MainActivity.this, movieDataModels[0].getOverview(), Toast.LENGTH_SHORT).show();

                            moviesArrayAdapter = new MoviesArrayAdapter( MainActivity.this ,0,  movieDataModels);
                            lst_movies.setAdapter(moviesArrayAdapter);
                            lst_movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                   // Toast.makeText(MainActivity.this, moviesArrayAdapter.getItem(i).getTitle(), Toast.LENGTH_SHORT).show();
                                    Intent myintent=new Intent(MainActivity.this , itemDetails.class);
                                    myintent.putExtra("movieDataModels",(Serializable) movieDataModels[i]);
                                    startActivity(myintent);
                                }
                            });


                        } catch (JsonIOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
