package apps.eos.automaticobfuscationofsensitivequeries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebOperations {
    private final Context applicationContext;
    private final Activity activity;
    private String task;
    private String query;


    public WebOperations(Activity activity) {
        this.activity = activity;
        applicationContext = activity.getApplicationContext();
    }

    public String getEntireWebpage() {
        URL url;
        try {
            url = new URL("http://" + getServerAddress() + "/" + task);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                //System.out.println("Connecting succesfull (" + responseCode + ")");

                //connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
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
                //String textForFile = response.toString().replaceAll("\\<.*?\\>", "").trim();
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
        new Thread(new getWebpageRunnable()).start();

        /*final CountDownLatch latch = new CountDownLatch(1);
        final String[] returnString = new String[1];
        Thread uiThread = new HandlerThread("UIHandler"){
            @Override
            public void run(){
                returnString[0] = getEntireWebpage();
                latch.countDown(); // Release await() in the test thread.
            }
        };
        uiThread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        returnString[0]editNounphraseString(returnString[0]);
        */
    }
    public void getKeyqueries(String query, String nounphrases){
        this.query = query;
        this.task = "find-keyqueries?query="+query+nounphrases;
        new Thread(new getKeyqueriesRunnable()).start();
    }
    public void getSearchResults(String query, String keyqueries){
        this.query = query;
        this.task = "search?query="+query+keyqueries;
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
            try {
                String returnString = getEntireWebpage();
                //returnString = editNounphraseString(returnString);
                Intent intent = new Intent(applicationContext, ChooseNounphrasesActivity.class);
                intent.putExtra("query", query);
                intent.putExtra("nounphrases", returnString);
                activity.startActivity(intent);
                activity.finish();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    class getKeyqueriesRunnable implements Runnable {

        @Override
        public void run() {
            try {
                String returnString = getEntireWebpage();
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
                String returnString = getEntireWebpage();
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
