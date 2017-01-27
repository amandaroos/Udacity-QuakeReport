package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amanda on 1/24/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader <ArrayList<Earthquake>>{

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    //Query url
    private String mUrl;

    /**constructor for new {@link EarthquakeLoader}
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader (Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e(LOG_TAG,"onStartLoading");
    }

    //this is on the background thread
    @Override
    public ArrayList<Earthquake> loadInBackground( ) {

        Log.e(LOG_TAG,"loadInBackground");
        // Perform the HTTP request for earthquake data and process the response.
        if (mUrl == null) {
            return null;
        }

        //Perform the network request, parse the response, and extract list of earthquakes
        ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }
}
