package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;

/**
 * Created by Amanda on 12/8/2016.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     *
     * @param context
     * @param earthquakes List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter (Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        //Get the magnitude of the quake and set to textview
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(currentEarthquake.getMagnitude());

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitudeDouble());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //Get the city name of the quake and set to textview
        TextView distanceFromCityTextView = (TextView) listItemView.findViewById(R.id.distance_from_city);
        distanceFromCityTextView.setText(currentEarthquake.getDistanceFromCity());

        //Get the city name of the quake and set to textview
        TextView cityNameTextView = (TextView) listItemView.findViewById(R.id.city_name);
        cityNameTextView.setText(currentEarthquake.getCityName());

        //Get the date of the quake and set to textview
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(currentEarthquake.getDate());

        //Get the time of the quake and set to textview
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(currentEarthquake.getTime());

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude){

        int magnitudeInt = (int) magnitude;
        int magnitudeColor;

        switch (magnitudeInt){
            case 0:
            case 1:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            case 10:
            default:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }

        return magnitudeColor;
    }
}
