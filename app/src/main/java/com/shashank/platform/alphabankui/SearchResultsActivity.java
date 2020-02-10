package com.shashank.platform.alphabankui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {


    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;

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

        String keyphrases = "";
        if(getIntent().hasExtra("query") == true) {
            String query = getIntent().getExtras().getString("query");
            keyphrases = getEntireWebpage(query, "20");
            System.out.println(keyphrases);
        }

        tv1 = (TextView) findViewById(R.id.tvnounphrase1);
        tv1.setText(keyphrases);
        tv2 = (TextView) findViewById(R.id.tvnounphrase2);
        tv3 = (TextView) findViewById(R.id.tvnounphrase3);
        tv4 = (TextView) findViewById(R.id.tvnounphrase4);
        tv5 = (TextView) findViewById(R.id.tvnounphrase5);
        tv6 = (TextView) findViewById(R.id.tvnounphrase6);

        cardView1 = (CardView) findViewById(R.id.nounphrase1);
        cardView1.setCardBackgroundColor(Color.GREEN);
        cardView2 = (CardView) findViewById(R.id.nounphrase2);
        cardView3 = (CardView) findViewById(R.id.nounphrase3);
        cardView4 = (CardView) findViewById(R.id.nounphrase4);
        cardView5 = (CardView) findViewById(R.id.nounphrase5);
        cardView6 = (CardView) findViewById(R.id.nounphrase6);
        cardView1.setClickable(true);
        cardView1.setOnClickListener(this);






    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.nounphrase1:
                //Toast.makeText(this, "Don't worry about a frozen or black screen for some seconds :)", Toast.LENGTH_SHORT).show();
                if(cardView1.getCardBackgroundColor().equals(Color.WHITE)){
                    cardView1.setCardBackgroundColor(Color.RED);
                }else{
                    cardView1.setCardBackgroundColor(Color.WHITE);
                    tv1.setTextColor(Color.BLACK);
                }
                break;


        }
    }

    public String getEntireWebpage(String query, String size) {
        URL url;
        try {
            url = new URL("http://localhost:8080/find-nounphrases?query="+query+",size="+size);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if(responseCode == 200) {
                //System.out.println("Connecting succesfull (" + responseCode + ")");

                //connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                long time1 = System.currentTimeMillis();
                while ((inputLine = in.readLine()) != null) {
                    long time2 = System.currentTimeMillis();
                    if((time2-time1)/1000>=20) {
                        break;
                    }
                    response.append(inputLine);
                }
                connection.disconnect();
                //String textForFile = response.toString().replaceAll("\\<.*?\\>", "").trim();
                return response.toString();
            }else {
                System.out.println("Connection failed (" + responseCode + ")");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
