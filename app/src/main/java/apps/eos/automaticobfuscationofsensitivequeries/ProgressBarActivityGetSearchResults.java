package apps.eos.automaticobfuscationofsensitivequeries;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eos.apps.automaticobfuscationofsensitivequeries.R;

import java.util.List;
import java.util.Map;

public class ProgressBarActivityGetSearchResults extends AppCompatActivity {
    private ProgressBar progressBar;
    private MyProgressBar pb;
    private int progressStatus = 0;
    private android.widget.ImageView ImageView;
    private Handler handler = new Handler();
    private String query;
    String[] keyqueryCover;
    TextView tvTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().hasExtra("query") == true && getIntent().hasExtra("keyqueries") == true) {
            this.query = getIntent().getExtras().getString("query");
            String keyqueries = getIntent().getExtras().getString("keyqueries");
            try {
                Map keyqueriesMap = new ObjectMapper().readValue(keyqueries, Map.class);
                List<String> list = (List) keyqueriesMap.get("keyqueries");
                keyqueryCover = list.toArray(new String[list.size()]);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        pb = new MyProgressBar(this);

        String keyqueriesParameter = "";
        for(String s : keyqueryCover){
            keyqueriesParameter += "&keyqueries="+s;
        }
        tvTodo = (TextView) findViewById(R.id.tvTodo);
        tvTodo.setText("Receiving search results");
        new WebOperations(this).getSearchResults(query,keyqueriesParameter);

        //Long operation by thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 4;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            pb.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 300 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    class MyProgressBar extends ProgressBar {
        public MyProgressBar(Context context) {
            super(context);
        }

        @Override
        public void setProgress(int progress)
        {
            super.setProgress(progress);
            if(progress == this.getMax())
            {
                System.out.println("MAX");
                //Do stuff when progress is max
                //Intent intent  = new Intent(mContext, ChooseNounphrasesActivity.class);
                //startActivity(intent);


                /*Intent intent = new Intent(getApplicationContext(), ChooseNounphrasesActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);*/
            }
        }
    }





}
