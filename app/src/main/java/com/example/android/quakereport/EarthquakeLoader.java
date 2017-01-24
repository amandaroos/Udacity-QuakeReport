package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amanda on 1/24/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader <List<Earthquake>>{
    public EarthquakeLoader (Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {

        //JSON query URL returns 10 most ercent earthquakes with at least a magnitude of 6
        String EARTHQUAKE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/" +
                "query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


        ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(EARTHQUAKE_URL);
        return result;
    }
}
