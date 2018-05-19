package com.example.level.news_app;

//A custom object containing information about a news item.

import java.util.ArrayList;

public class News {

    //Header of the article
    private String mHeader;

    //Section where the news article belongs
    private String mSection;

    //An excerpt from the beginning of the body of the article.
    private String mBodyBeginning;

    /** Publishing date and time of the article */
    private long mTimeInMilliseconds;

    //Author of the article (if it is known).
    String mAuthor;

    //URL of the article's website.
    private String mWebsite;

    //Create a new News object.
    public News(String header, String section, String bodyBeginning, long timeInMilliseconds,
                String author, String website) {

        mHeader = header;

        mSection = section;

        mBodyBeginning = bodyBeginning;

        mTimeInMilliseconds = timeInMilliseconds;

        mAuthor = author;

        mWebsite = website;
    }

    //Get the header of the article.
    public String getHeader() {
        return mHeader;
    }

    //Get the name of the section of the article.
    public String getSection() {
        return mSection;
    }

    //Get the beginning sentence of the article.
    public String getBodyBeginning() {
        return mBodyBeginning;
    }

    //Get the publishing date and time of the article
    public Long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    //Get the name of the author of the article.
    public String getAuthor(){
        return mAuthor.toString();
    }

    //Get the website of the news article.
    public String getWebsiteUrl() {return mWebsite;}

}
