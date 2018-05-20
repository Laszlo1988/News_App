package com.example.level.news_app;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NewsAdapter extends ArrayAdapter<News> {

    //Storing the name of the class in case of using it in Log messages.
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *  @param context     The current context. Used to inflate the layout file.
     * @param news A List of News objects to display in a list
     */
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        News currentNews = getItem(position);

        // Find the TextView with view ID header
        TextView headerTextView = (TextView) listItemView.findViewById(R.id.header);

        //Get the header of the article
        String header = currentNews.getHeader();

        //Set the header.
        headerTextView.setText(header);

        // Find the TextView with view ID section.
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.section);

        //Get the section name of the article
        String section = currentNews.getSection();

        //Set the name of the section.
        sectionTextView.setText(section);

        // Find the TextView for the beginning of the body of the text.
        TextView bodyTextView = (TextView) listItemView.findViewById(R.id.body);

        //Get the beginning of the article
        String body = currentNews.getBodyBeginning();

        //Set the beginning of the article.
        bodyTextView.setText(body);

        // Find the TextView for the name of the author.
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);

        //Get the beginning of the article
        String author = "by" + currentNews.getAuthor();

        //Set the header.
        authorTextView.setText(author);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentNews.getTimeInMilliseconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);

        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);

        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
