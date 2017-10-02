package com.example.islamiccenter.webservice_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {
    final String ReleaseDateAsc = "release_date.asc";
    final String PopularityAsc = "popularity.asc";

    final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
    RadioButton btn_releaseDate = (RadioButton) findViewById(R.id.radioButtonReleaseDate);
    RadioButton btn_popularity = (RadioButton) findViewById(R.id.radioButtonpop);
    Button search = (Button) findViewById(R.id.buttonSearch);
    String sort_By_Varible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButtonReleaseDate:
                        sort_By_Varible = "release_date.asc";
                        break;
                    case R.id.radioButtonpop:
                        sort_By_Varible = "popularity.asc";
                        break;
                }
                String url = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=ab71bd22b9e87623e779800766e9ef81";

                executeWebService(url);

            }

            private void executeWebService(String url) {
            }
        });
    }

    private void executeWebService(String url) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final Gson gson = new Gson();
        final ListView lst_movies = (ListView) findViewById(R.id.movielist);
        final MoviesArrayAdapter moviesArrayAdapter;


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            final movieDataModel[] movieDataModels;


                            movieDataModels = gson.fromJson(jsonArray.toString(), movieDataModel[].class);
                          //  Toast.makeText(MainActivity.this, movieDataModels[0].getOverview(), Toast.LENGTH_SHORT).show();

                            moviesArrayAdapter = new MoviesArrayAdapter(this , movieDataModels);
                            lst_movies.setAdapter(moviesArrayAdapter);
                            lst_movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(MainActivity.this, moviesArrayAdapter.getItem(i).getTitle(), Toast.LENGTH_SHORT).show();
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
