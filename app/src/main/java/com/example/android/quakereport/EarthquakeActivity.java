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

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    //JSON query URL returns 10 most ercent earthquakes with at least a magnitude of 6
    private static final String EARTHQUAKE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/" +
            "query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    //Adapter for the list of earthquakes
    private EarthquakeAdapter mAdapter;



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

/*

        //Request JSON data from web server, parse the data, and create a list of earthquakes
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(EARTHQUAKE_URL);
*/

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        Log.e(LOG_TAG,"Initializing Loader");
    }


    private void updateUi (List<Earthquake> earthquakes){

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

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG,"onCreateLoader");
        // Create a new loader for the given URL
        return new EarthquakeLoader(this, EARTHQUAKE_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();


        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
            updateUi(earthquakes);
        }
        Log.e(LOG_TAG,"onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.e(LOG_TAG,"onLoaderReset");
    }
}
