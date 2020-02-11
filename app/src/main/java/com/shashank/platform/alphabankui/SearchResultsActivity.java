package com.shashank.platform.alphabankui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvQuery;
    TextView[] tvTitle, tvURL, tvSnippet;
    CardView[] cvResult;

    String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra("query") == true) {
            query = getIntent().getExtras().getString("query");
            //String[] nounphrases = getIntent().getExtras().getStringArray("nounphrases");
            //nounphrases = new WebOperations().getEntireWebpage(query, "20", "find-nounphrases").split(", "); //TODO split an was?

        }

        tvQuery = (TextView) findViewById(R.id.tvquery);
        tvQuery.setText(query);


        tvTitle = new TextView[10];
        tvURL = new TextView[10];
        tvSnippet = new TextView[10];
        cvResult = new CardView[10];
        instantiate();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv1:
                Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
                intent.putExtra("url", "https://wikipedia.de/");
                //intent.putExtra("nounphrases", usedNounphrases);
                startActivity(intent);
                break;
            case R.id.cv2:
                break;
            case R.id.cv3:
                break;
            case R.id.cv4:
                break;
            case R.id.cv5:
                break;
            case R.id.cv6:
                break;
            case R.id.cv7:
                break;
            case R.id.cv8:
                break;
            case R.id.cv9:
                break;
            case R.id.cv10:
                break;
        }
    }




    private void instantiate(){
        tvTitle[0] = (TextView) findViewById(R.id.tv1title);
        tvTitle[1] = (TextView) findViewById(R.id.tv2title);
        tvTitle[2] = (TextView) findViewById(R.id.tv3title);
        tvTitle[3] = (TextView) findViewById(R.id.tv4title);
        tvTitle[4] = (TextView) findViewById(R.id.tv5title);
        tvTitle[5] = (TextView) findViewById(R.id.tv6title);
        tvTitle[6] = (TextView) findViewById(R.id.tv7title);
        tvTitle[7] = (TextView) findViewById(R.id.tv8title);
        tvTitle[8] = (TextView) findViewById(R.id.tv9title);
        tvTitle[9] = (TextView) findViewById(R.id.tv10title);
        tvURL[0] = (TextView) findViewById(R.id.tv1url);
        tvURL[1] = (TextView) findViewById(R.id.tv2url);
        tvURL[2] = (TextView) findViewById(R.id.tv3url);
        tvURL[3] = (TextView) findViewById(R.id.tv4url);
        tvURL[4] = (TextView) findViewById(R.id.tv5url);
        tvURL[5] = (TextView) findViewById(R.id.tv6url);
        tvURL[6] = (TextView) findViewById(R.id.tv7url);
        tvURL[7] = (TextView) findViewById(R.id.tv8url);
        tvURL[8] = (TextView) findViewById(R.id.tv9url);
        tvURL[9] = (TextView) findViewById(R.id.tv10url);
        tvSnippet[0] = (TextView) findViewById(R.id.tv1snippet);
        tvSnippet[1] = (TextView) findViewById(R.id.tv2snippet);
        tvSnippet[2] = (TextView) findViewById(R.id.tv3snippet);
        tvSnippet[3] = (TextView) findViewById(R.id.tv4snippet);
        tvSnippet[4] = (TextView) findViewById(R.id.tv5snippet);
        tvSnippet[5] = (TextView) findViewById(R.id.tv6snippet);
        tvSnippet[6] = (TextView) findViewById(R.id.tv7snippet);
        tvSnippet[7] = (TextView) findViewById(R.id.tv8snippet);
        tvSnippet[8] = (TextView) findViewById(R.id.tv9snippet);
        tvSnippet[9] = (TextView) findViewById(R.id.tv10snippet);
        cvResult[0] = (CardView) findViewById(R.id.cv1);
        cvResult[1] = (CardView) findViewById(R.id.cv2);
        cvResult[2] = (CardView) findViewById(R.id.cv3);
        cvResult[3] = (CardView) findViewById(R.id.cv4);
        cvResult[4] = (CardView) findViewById(R.id.cv5);
        cvResult[5] = (CardView) findViewById(R.id.cv6);
        cvResult[6] = (CardView) findViewById(R.id.cv7);
        cvResult[7] = (CardView) findViewById(R.id.cv8);
        cvResult[8] = (CardView) findViewById(R.id.cv9);
        cvResult[9] = (CardView) findViewById(R.id.cv10);
        for(int i = 0; i < cvResult.length; i++){
            cvResult[i].setOnClickListener(this);
        }
    }
}
