package com.shashank.platform.alphabankui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {
    EditText tvQuery;
    TextView[] tvTitle, tvURL, tvSnippet;
    CardView[] cvResult;
    ImageView btnNounphrases;

    String query;
    String[] results;


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

        if (getIntent().hasExtra("query") == true && getIntent().hasExtra("results") == true) {
            query = getIntent().getExtras().getString("query");
            String resultsString = getIntent().getExtras().getString("results");

            try {
                Map nounphrasesMap = new ObjectMapper().readValue(resultsString, Map.class);
                List<String> list = (List) nounphrasesMap.get("search results");
                results = list.toArray(new String[list.size()]);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //String[] nounphrases = getIntent().getExtras().getStringArray("nounphrases");
            //nounphrases = new WebOperations().getEntireWebpage(query, "20", "find-nounphrases").split(", "); //TODO split an was?

        }

        tvQuery = (EditText) findViewById(R.id.tvquery);
        tvQuery.setText(query);
        btnNounphrases = (ImageView)findViewById(R.id.btnNounphrases);
        btnNounphrases.setOnClickListener(this);


        tvTitle = new TextView[10];
        tvURL = new TextView[10];
        for(int i = 0; i < results.length; i++){
            if(i < tvURL.length){
                tvURL[i].setText(results[i]);
            }
        }
        tvSnippet = new TextView[10];
        cvResult = new CardView[10];
        instantiate();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv1:
                openWebview(results[0]);
                break;
            case R.id.cv2:
                openWebview(results[1]);
                break;
            case R.id.cv3:
                openWebview(results[2]);
                break;
            case R.id.cv4:
                openWebview(results[3]);
                break;
            case R.id.cv5:
                openWebview(results[4]);
                break;
            case R.id.cv6:
                openWebview(results[5]);
                break;
            case R.id.cv7:
                openWebview(results[6]);
                break;
            case R.id.cv8:
                openWebview(results[7]);
                break;
            case R.id.cv9:
                openWebview(results[8]);
                break;
            case R.id.cv10:
                openWebview(results[9]);
                break;
            case R.id.btnNounphrases:
                String q = tvQuery.getText()+"";
                System.out.println(q);
                Intent intent2 = new Intent(getApplicationContext(), ChooseNounphrasesActivity.class);
                intent2.putExtra("query", q);
                startActivity(intent2);
                break;
        }
    }


    private void openWebview(String url){
        Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
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
