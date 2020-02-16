package apps.eos.automaticobfuscationofsensitivequeries;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import eos.apps.automaticobfuscationofsensitivequeries.R;

public class ProgressBarActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private MyProgressBar pb;
    private int progressStatus = 0;
    private android.widget.ImageView ImageView;
    private Handler handler = new Handler();
    private String query;

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

        if(getIntent().hasExtra("query") == true) {
            this.query = getIntent().getExtras().getString("query");
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        pb = new MyProgressBar(this);


        new WebOperations(this).getNounphrases(query);

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
