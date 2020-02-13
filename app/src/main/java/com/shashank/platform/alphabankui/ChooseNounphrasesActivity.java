package com.shashank.platform.alphabankui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChooseNounphrasesActivity extends AppCompatActivity implements View.OnClickListener {

    CardView[] cardView;
    TextView[] tv;
    TextView tvRandom;
    boolean [] userChoice;
    Button button;

    String query;
    String [] nounphrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosenounphrases);

        cardView = new CardView[10];
        tv = new TextView[10];
        tvRandom = (TextView) findViewById(R.id.tvRandom);
        tvRandom.setOnClickListener(this);
        nounphrases = new String[]{"phrase1", "phrase2", "phrase3", "phrase4", "phrase5"};

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().hasExtra("query") == true && getIntent().hasExtra("nounphrases") == true) {
            query = getIntent().getExtras().getString("query");
            String nounphrasesString = getIntent().getExtras().getString("nounphrases");

            try {
                Map nounphrasesMap = new ObjectMapper().readValue(nounphrasesString, Map.class);
                List<String> list = (List) nounphrasesMap.get("keyphrases");
                nounphrases = list.toArray(new String[list.size()]);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //if(ip != null){
            /*if(true){
                //local
            }else{
                Toast.makeText(this, "Please set up a server in settings", Toast.LENGTH_SHORT).show();
            }*/
            //TODO Kommentare weg

            //String nounphrasesString = new WebOperations(this).getEntireWebpage("find-nounphrases?query="+query);
            /*if(nounphrasesString != null){
                nounphrases = nounphrasesString.split(",");
            }*/

            //System.out.println(nounphrasesString);

            userChoice = new boolean[nounphrases.length];
            for(int i = 0; i < userChoice.length; i++){
                userChoice[i] = true;
            }
        }

        button = (Button) findViewById(R.id.button);
        button.setClickable(true);
        button.setOnClickListener(this);

        tv[0] = (TextView) findViewById(R.id.tvnounphrase1);
        tv[1] = (TextView) findViewById(R.id.tvnounphrase2);
        tv[2] = (TextView) findViewById(R.id.tvnounphrase3);
        tv[3] = (TextView) findViewById(R.id.tvnounphrase4);
        tv[4] = (TextView) findViewById(R.id.tvnounphrase5);
        tv[5] = (TextView) findViewById(R.id.tvnounphrase6);
        tv[6] = (TextView) findViewById(R.id.tvnounphrase7);
        tv[7] = (TextView) findViewById(R.id.tvnounphrase8);
        tv[8] = (TextView) findViewById(R.id.tvnounphrase9);
        tv[9] = (TextView) findViewById(R.id.tvnounphrase10);
        for(int i = 0; i < tv.length && i<nounphrases.length; i++){
            tv[i].setText(nounphrases[i]);
        }


        cardView[0] = (CardView) findViewById(R.id.nounphrase1);
        cardView[1] = (CardView) findViewById(R.id.nounphrase2);
        cardView[2] = (CardView) findViewById(R.id.nounphrase3);
        cardView[3] = (CardView) findViewById(R.id.nounphrase4);
        cardView[4] = (CardView) findViewById(R.id.nounphrase5);
        cardView[5] = (CardView) findViewById(R.id.nounphrase6);
        cardView[6] = (CardView) findViewById(R.id.nounphrase7);
        cardView[7] = (CardView) findViewById(R.id.nounphrase8);
        cardView[8] = (CardView) findViewById(R.id.nounphrase9);
        cardView[9] = (CardView) findViewById(R.id.nounphrase10);
        //TODO 12
        for(int i = 0; i < cardView.length; i++){
            cardView[i].setClickable(true);
            cardView[i].setOnClickListener(this);
        }

    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.nounphrase1:
                //Toast.makeText(this, "Don't worry about a frozen or black screen for some seconds :)", Toast.LENGTH_SHORT).show();
                changeStatus(0);
                break;
            case R.id.nounphrase2:
                changeStatus(1);
                break;
            case R.id.nounphrase3:
                changeStatus(2);
                break;
            case R.id.nounphrase4:
                changeStatus(3);
                break;
            case R.id.nounphrase5:
                changeStatus(4);
                break;
            case R.id.nounphrase6:
                changeStatus(5);
                break;
            case R.id.nounphrase7:
                changeStatus(6);
                break;
            case R.id.nounphrase8:
                changeStatus(7);
                break;
            case R.id.nounphrase9:
                changeStatus(8);
                break;
            case R.id.nounphrase10:
                changeStatus(9);
                break;
            case R.id.tvRandom:
                new WebOperations(this).loadExampleQuery();
                break;
            case R.id.button:
                if(button.getText().equals("Confirm")){
                    ArrayList<String> usedNounphrases = new ArrayList<String>();
                    for(int i = 0; i < nounphrases.length; i++){
                        if(userChoice[i]){
                            usedNounphrases.add(nounphrases[i]);
                        }
                    }
                    Intent intentResults = new Intent(getApplicationContext(), ProgressBarActivityGetKeyqueries.class);
                    intentResults.putExtra("query", query);
                    intentResults.putStringArrayListExtra("nounphrases", usedNounphrases);
                    startActivity(intentResults);
                }else{

                }
                break;
        }
    }

    private void changeStatus(int i){
        if(userChoice[i] == false){
            cardView[i].setCardBackgroundColor(Color.parseColor("#FF8BC34A"));
            tv[i].setTextColor(Color.WHITE);
            userChoice[i] = true;
        }else{
            cardView[i].setCardBackgroundColor(Color.WHITE);
            cardView[i].setClickable(true);
            tv[i].setTextColor(Color.BLACK);
            userChoice[i] = false;
        }
    }


}
