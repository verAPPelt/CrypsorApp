package apps.eos.automaticobfuscationofsensitivequeries;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import eos.apps.automaticobfuscationofsensitivequeries.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart,btnSetting;
    ImageView crypsorButton, ivServer;
    final int PERMISSION_INTERNET_REQUEST = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnStart = findViewById(R.id.start);
        btnStart.setOnClickListener(this);
        btnSetting = findViewById(R.id.settings);
        btnSetting.setOnClickListener(this);

        crypsorButton = (ImageView) findViewById(R.id.crypsorButton);
        crypsorButton.setClickable(true);
        crypsorButton.setOnClickListener(this);

        ivServer = (ImageView) findViewById(R.id.ivServerConnection);
        ivServer.setClickable(true);
        ivServer.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Internet erlaubt");
        } else {
            System.out.println("Internet nicht erlaubt");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, PERMISSION_INTERNET_REQUEST);
        }

        updateConnectionStatus();
        //new Thread(new checkConnectionRunnable()).start();

    }

    private void updateConnectionStatus(){
        //FIXME does not work if connection interrupted
        String connected = "false";
        if(getIntent().hasExtra("connectionStatus") == true) {
            connected = getIntent().getExtras().getString("connectionStatus");
        }else{
            new WebOperations(this).checkConnection();
        }
        if(connected.equals("true")){
            ivServer.setImageResource(R.drawable.wificonnectedborder);
        }else{
            ivServer.setImageResource(R.drawable.wifinotconnectednorder);
            Toast.makeText(this, "Not connected. Please control server settings", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("query", "");
                startActivity(intent);
                break;
            case R.id.settings:
                Intent intentSettings = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.crypsorButton:    //TODO pagehunt
                Intent intentPageHunt = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intentPageHunt);
                break;
            case R.id.ivServerConnection:
                updateConnectionStatus();
                //new WebOperations(this).checkConnection();
                break;
        }
    }

    /*class checkConnectionRunnable implements Runnable {

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                new WebOperations(MainActivity.this).checkConnection();
            }

        }
    }*/
}
