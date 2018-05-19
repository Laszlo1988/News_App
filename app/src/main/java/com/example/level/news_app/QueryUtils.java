package com.example.level.news_app;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from The Guardian API.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the The Guardian data set and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}
        return news;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest (URL url) throws IOException, RuntimeException {

        String jsonResponse = "";

        //If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /*milliseconds*/);
            urlConnection.setConnectTimeout(15000 /*milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();

            Log.i("QueryUtils.java", "Response code is: " + responseCode);

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }catch (RuntimeException e) {
            Log.e(LOG_TAG, "The problem is: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static ArrayList extractFeatureFromJson(String newsJSON) {

        // Create an empty ArrayList that we can start adding news to
        ArrayList<News> news = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject root = new JSONObject(newsJSON);

            //Create a JSONObject for the key "response".
            JSONObject response = root.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of results (or news articles). The key "results"
            // also represents the list of data of a news article.
            JSONArray results = response.getJSONArray("results");

            // For each earthquake in the newsArray, create an {@link News} object
            for (int i = 0; i < results.length(); i++) {

                // Get a single news article at position i within the list of news
                JSONObject news_article = results.getJSONObject(i);

                // Extract the value for the key called "sectionName"
                String sectionName = news_article.getString("sectionName");

                // Extract the value for the key called "webTitle"
                String webTitle = news_article.getString("webTitle");

                // Extract the value for the key called "webUrl"
                String website = news_article.getString("webUrl");

                //Create a JSONObject for the key "blocks"
                JSONObject blocks = news_article.getJSONObject("blocks");

                //Create a JSONObject for the key "requestedBodyBlocks"
                JSONObject requestedBodyBlocks = blocks.getJSONObject("requestedBodyBlocks");

                //Create a JSONArray for the key "body:latest:3"
                JSONArray latestThreeBodies = requestedBodyBlocks.getJSONArray("body:latest:3");

                //Create a JSONObject from the latest body of the article at of the
                // index 0 of the latestThreeBodies JSONArray
                JSONObject latestBody = latestThreeBodies.getJSONObject(0);

                //Extract the value for the key "bodyTextSummary"
                String bodyTextSummary = latestBody.getString("bodyTextSummary");

                //Create a slice of the String value bodyTextSummary.
                String textSummarySlice;

                if (bodyTextSummary.length() < 120) {
                    textSummarySlice = latestBody.getString("bodyTextSummary");
                } else {
                    textSummarySlice = bodyTextSummary.substring(0,120);
                }

                // Create a JSONObject for the key "publishedDate" in from the "latestBody"
                //JSONObject.
                JSONObject publishedDate = latestBody.getJSONObject("publishedDate");

                long time = publishedDate.getLong("dateTime");

                //Create a JSONArray for the key "tags".
                JSONArray tags = news_article.getJSONArray("tags");

                //Create a String for storing author's name.
                String author = "";

                JSONObject tagsItem = null;

                if (tags.length() > 1) {
                    for (int j = 0; j < tags.length(); j++) {
                        //Create a JSONObject from the item found in "tags".
                        tagsItem = tags.getJSONObject(j);

                        author += "#" + tagsItem.getString("webTitle");
                        }
                } else {
                    tagsItem = tags.getJSONObject(0);
                    author = "#" + tagsItem.getString("webTitle");
                }

                // Create a new {@link News} object with date from the JSON response, and build up
                // a list of News objects with them.
                news.add(new News(webTitle, sectionName, textSummarySlice, time, author, website));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of news
        return news;
    }
}
