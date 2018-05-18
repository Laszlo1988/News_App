package com.example.level.news_app;

/**
 * Created by level on 2018. 05. 03..
 */

//A custom object containing information about an earthquake.

public class NewsObject {

    //Location of the earthquake (e.g. Los Angels, San Francisco)
    private String mLocation;

    //Strength (magnitude) of the earthquake (e.g. 2.5, 7.0, 5.6)
    private double mMagnitude;

    /** Time of the earthquake */
    private long mTimeInMilliseconds;

    //URL of the earthquake's website.
    private String mWebsite;

    //Create a new Earthquake object.
    public NewsObject(String location, double magnitude, long timeInMilliseconds, String website) {

        mLocation = location;

        mMagnitude = magnitude;

        mTimeInMilliseconds = timeInMilliseconds;

        mWebsite = website;
    }

    //Get the name of the location.
    public String getLocation() {
        return mLocation;
    }

    //Get the value of the magnitude.
    public double getMagnitude() {
        return mMagnitude;
    }

    //Get the date of the earthquake.
    public Long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    //Get the website of the earthquake.
    public String getWebsiteUrl() {return mWebsite;}

}
