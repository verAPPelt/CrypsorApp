package apps.eos.automaticobfuscationofsensitivequeries;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import eos.apps.automaticobfuscationofsensitivequeries.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;
    Button btnServer;
    TextView tvInfo, tvIP;
    EditText etServerIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnServer = (Button) findViewById(R.id.btnserver);
        btnServer.setOnClickListener(this);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setClickable(true);
        tvInfo.setOnClickListener(this);
        tvIP = (TextView) findViewById(R.id.tvIP);
        tvIP.setVisibility(View.INVISIBLE);
        etServerIP = (EditText) findViewById(R.id.etServerIP);
        etServerIP.setVisibility(View.INVISIBLE);

        String ip = "12345";
        speicher = getApplicationContext().getSharedPreferences("Settings", 0);
        editor = speicher.edit();
        if(speicher.getString("Server-IP", null)!= null){
            btnServer.setText("Change Server");
        }else{
            btnServer.setText("Add Server");
        }

    }

    private void getData(){
        if(speicher.getString("Server-IP", null) != null){
            Toast.makeText(this, "IP: "+speicher.getString("Server-IP", null), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No server saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveData(String ip){
        editor.putString("Server-IP", ip);
        editor.commit();
        Toast.makeText(this, "Changes applied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnserver:
                if(etServerIP.getVisibility() == View.VISIBLE){
                    saveData(etServerIP.getText().toString());
                    tvIP.setVisibility(View.INVISIBLE);
                    etServerIP.setVisibility(View.INVISIBLE);
                }else{
                    tvIP.setVisibility(View.VISIBLE);
                    etServerIP.setVisibility(View.VISIBLE);
                    etServerIP.setText(speicher.getString("Server-IP", null));
                }

                break;
            case R.id.tvInfo:
                //TODO tooltip hinweis
                //Animation animation = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.fadeout);
                //btnServer.startAnimation(animation);
                break;
        }
    }


}
