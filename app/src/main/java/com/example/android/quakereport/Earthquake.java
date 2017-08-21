package com.example.android.quakereport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Amanda on 12/8/2016.
 */

public class Earthquake {

    private double mMagnitude = 0;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mWebsite;

    /**
     * Create a new Earthquake object
     * @param magnitude
     * @param location
     *@param timeInMilliseconds Unix time representation of when the quake happened
     */
    public Earthquake (double magnitude, String location, long timeInMilliseconds, String website) {
        mLocation = location;
        mMagnitude = magnitude;
        mTimeInMilliseconds = timeInMilliseconds;
        mWebsite = website;
    }

    // @return mLocation the location of the quake
    public String getLocation (){
        return mLocation;
    }

    // Extract city name from mLocation
    // @return cityName
    public String getCityName(){

        String cityName = mLocation;

        if (mLocation.contains(" of ")){
            String[] parts = mLocation.split(" of ");
            cityName = parts [1];
        }
        else {
            cityName = mLocation;
        }
        return cityName;
    }

    // Extract distance from city from mLocation
    // @return cityName
    public String getDistanceFromCity(){

        String distanceFromCity;

        if (mLocation.contains(" of ")){
            String[] parts = mLocation.split("(?<=of)");
            distanceFromCity = parts [0];
        }
        else {
            distanceFromCity = "Near the";
        }
        return distanceFromCity;
    }


    // @return mMagnitude the magnitude of the quake with one decimal place
    public double getMagnitudeDouble (){
        return mMagnitude;
    }

    // @return mMagnitude the magnitude of the quake with one decimal place
    public String getMagnitude (){
        DecimalFormat formatter =new DecimalFormat("0.0");
        String output = formatter.format(mMagnitude);
        return output;
    }

    // @return mDate the date of the quake
    public String getDate (){

        Date dateObject = new Date(mTimeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        return dateToDisplay;
    }

    // @return mDate the time of the quake
    public String getTime (){

        Date dateObject = new Date(mTimeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        String timeToDisplay = dateFormatter.format(dateObject);
        return timeToDisplay;
    }

    // @return mWebsite the url with more information related to the quake
    public String getWebsite (){
        return mWebsite;
    }


}
