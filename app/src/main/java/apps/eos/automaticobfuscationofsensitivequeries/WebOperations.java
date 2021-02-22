package apps.eos.automaticobfuscationofsensitivequeries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebOperations {
    private final Context applicationContext;
    private final Activity activity;
    private String task;
    private String query;
    private String filename;


    public WebOperations(Activity activity) {
        this.activity = activity;
        applicationContext = activity.getApplicationContext();
    }

    public String getEntireWebpage() {
        //TODO Use JSoup
        URL url;
        try {
            url = new URL("http://" + getServerAddress() + "/" + task);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
               BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                long time1 = System.currentTimeMillis();
                while ((inputLine = in.readLine()) != null) {
                    long time2 = System.currentTimeMillis();
                    if ((time2 - time1) / 1000 >= 20) {
                        break;
                    }
                    response.append(inputLine);
                }
                connection.disconnect();
                return response.toString();
            } else {
                System.out.println("Connection failed (" + responseCode + ")");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void checkConnection(){
        this.task = "is-connected";
        new Thread(new checkConnectionRunnable()).start();
    }

    public void getNounphrases(String query){
        this.query = query;
        this.task = "find-nounphrases?query="+query;
        this.filename = this.task + ".txt";
        new Thread(new getWebpageRunnable()).start();
    }

    public void getKeyqueries(String query, String nounphrases){
        this.query = query;
        this.task = "find-keyqueries?query="+query+nounphrases;
        this.filename = "find-keyqueries?query="+query + ".txt";
        new Thread(new getKeyqueriesRunnable()).start();
    }

    public void getSearchResults(String query, String keyqueries){
        this.query = query;
        this.task = "search?query="+query+keyqueries;
        this.filename = "search?query="+query + ".txt";
        new Thread(new getSearchResultsRunnable()).start();
    }

    public void loadExampleQuery() {
        this.task = "random-query";
        new Thread(new RandomQueryRunnable()).start();
    }


    private String getServerAddress() {
        SharedPreferences speicher;
        SharedPreferences.Editor editor;
        speicher = applicationContext.getSharedPreferences("Settings", 0);
        editor = speicher.edit();
        String address = speicher.getString("Server-IP", null);
        address += ":8080";
        return address;
    }



    private boolean exists (String filename){
        File file = new File(applicationContext.getFilesDir(), this.filename);
        if(file.exists()){
            return true;
        }
        return false;
    }
    private String readData(String filename)
    {
        try
        {
            FileInputStream fin = applicationContext.openFileInput(this.filename);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }
            fin.close();
            return temp.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }


    private void writeData(String data) {
        //TODO query free space: https://developer.android.com/training/data-storage/app-specific#query-free-space
        try {
            FileOutputStream fos = applicationContext.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
            //System.out.println("Data written.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilename(){
        return this.filename;
    }


    private String getReturnString(){
        String filename = getFilename();
        //System.out.println(filename);
        String returnString;
        if(exists(filename)){
            returnString = readData(filename);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("File exists and is read");
        }else{
            returnString = getEntireWebpage();
            writeData(returnString);
            //System.out.println("File does not exist.");
        }
        return returnString;
    }

    //Runnables

    class checkConnectionRunnable implements Runnable {

        @Override
        public void run() {
            try {
                String string = getEntireWebpage();
                if(string == null){
                    string = "false";
                }
                Intent intent = new Intent(applicationContext, MainActivity.class);
                intent.putExtra("connectionStatus", string);
                activity.startActivity(intent);
                activity.finish();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    class RandomQueryRunnable implements Runnable {

        @Override
        public void run() {
            try {
                String string = getEntireWebpage();
                Intent intent = new Intent(applicationContext, Main2Activity.class);
                intent.putExtra("query", string);
                activity.startActivity(intent);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    class getWebpageRunnable implements Runnable {

        @Override
        public void run() {
            String returnString = getReturnString();
            Intent intent = new Intent(applicationContext, ChooseNounphrasesActivity.class);
            intent.putExtra("query", query);
            intent.putExtra("nounphrases", returnString);
            activity.startActivity(intent);
            activity.finish();

        }
    }

    class getKeyqueriesRunnable implements Runnable {

        @Override
        public void run() {
            try {
                String returnString = getReturnString();
                Intent intent = new Intent(applicationContext, ProgressBarActivityGetSearchResults.class);
                intent.putExtra("query", query);
                intent.putExtra("keyqueries", returnString);
                activity.startActivity(intent);
                activity.finish();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    class getSearchResultsRunnable implements Runnable {

        @Override
        public void run() {
            try {
                String returnString = getReturnString();
                Intent intent = new Intent(applicationContext, SearchResultsActivity.class);
                intent.putExtra("query", query);
                intent.putExtra("results", returnString);
                activity.startActivity(intent);
                activity.finish();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
