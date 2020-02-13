package com.shashank.platform.alphabankui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    EditText etQuery;
    TextView tvRandom;
    Button btnStart;
    ImageView btnNounphrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        setContentView(R.layout.activity_startsearch);

        etQuery = (EditText) findViewById(R.id.queryField);
        etQuery.setOnKeyListener(this);
        if(getIntent().hasExtra("query") == true) {
            String query = getIntent().getExtras().getString("query");
            etQuery.setText(query);
        }
        btnStart = (Button) findViewById(R.id.search);
        btnStart.setOnClickListener(this);
        btnNounphrases = (ImageView) findViewById(R.id.btnNounphrases);
        btnNounphrases.setOnClickListener(this);
        tvRandom = (TextView) findViewById(R.id.tvRandom);
        tvRandom.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                nextActivity();
                break;
            case R.id.tvRandom:
                new WebOperations(this).loadExampleQuery();
                break;
            case R.id.btnNounphrases:
                String q = etQuery.getText()+"";
                System.out.println(q);
                Intent intent = new Intent(getApplicationContext(), ChooseNounphrasesActivity.class);
                intent.putExtra("query", q);
                startActivity(intent);
                break;
        }
    }

    private void nextActivity(){
        String q = etQuery.getText()+"";
        System.out.println(q);
        /*Intent intent = new Intent(getApplicationContext(), ChooseNounphrasesActivity.class);
        intent.putExtra("query", q);
        startActivity(intent);*/
        Intent intent = new Intent(getApplicationContext(), ProgressBarActivity.class);
        intent.putExtra("query", q);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch (keyCode)
            {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    nextActivity();
                    return true;
                default:
                    break;
            }
        }
        return false;
    }
}
