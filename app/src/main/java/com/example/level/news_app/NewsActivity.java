package com.example.level.news_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsActivity extends AppCompatActivity {

    private static final String GUARDIAN_API_URL = "http://content.guardianapis.com/" +
            "search?from-date=2018-04-01&order-by=relevance&show-tags=contributor&show-blocks=" +
            "body:latest&show-fields=body&page-size=20&q=politics%20AND%20Hungary&" +
            "api-key=eb7599eb-8e50-45b4-8f13-fb5d4621cc84";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
    }
}
