/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();


    //JSON query URL returns 10 most ercent earthquakes with at least a magnitude of 6
    private static final String EARTHQUAKE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/" +
            "query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


/*        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        earthquakes.add(new Earthquake(7.1, "San Francisco", 1485059423, "www.google.com"));
        earthquakes.add(new Earthquake(5.1, "London", 1485059423, "www.google.com"));
        earthquakes.add(new Earthquake(4.2, "Tokyo", 1485059423, "www.google.com"));
        earthquakes.add(new Earthquake(4.5, "Mexico City", 1485059423, "www.google.com"));
        earthquakes.add(new Earthquake(2.1, "Moscow", 1485059423, "www.google.com"));
        earthquakes.add(new Earthquake(1.1, "Rio de Janeiro", 1485059423, "www.google.com"));
        earthquakes.add(new Earthquake(5.5, "Paris, France", 1485059423, "www.google.com"));
*/


        //Request JSON data from web server, parse the data, and create a list of earthquakes
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(EARTHQUAKE_URL);

    }

    private void updateUi (ArrayList<Earthquake> earthquakes){

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link EarthquakeAdapter} of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        //Set click listener to open a website that shows more information about the earthquake
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //Get the {@link Earthquake} object at the given position the user clicked on
                Earthquake earthquake = adapter.getItem(position);

                //Send intent to open the website for the earthquake that was clicked
                Uri webpage = Uri.parse(earthquake.getWebsite());
                Intent openWebsite = new Intent(Intent.ACTION_VIEW, webpage);
                //openWebsite.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //openWebsite.setPackage("com.android.chrome");

                if (openWebsite.resolveActivity(getPackageManager()) != null) {
                    startActivity(openWebsite);
                }
            }
        });

    }

    //Create an AsyncTask to handle getting the JSON Response
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected ArrayList doInBackground(String... urls) {

            // Perform the HTTP request for earthquake data and process the response.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result) {

            // If there is no result, do nothing.
            if (result == null) {
                return;
            }
            updateUi(result);
        }
    }
}
