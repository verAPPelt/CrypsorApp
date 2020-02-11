package com.shashank.platform.alphabankui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebOperations {
    public WebOperations(){

    }

    public String getEntireWebpage(String query, String size, String ip, String task) {
        URL url;
        try {
            url = new URL("http://"+ip+"/"+task+"?query="+query+",size="+size);
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
