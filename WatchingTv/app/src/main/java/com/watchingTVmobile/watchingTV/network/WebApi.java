package com.watchingTVmobile.watchingTV.network;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebApi  {

    public static final String BASE_URL="https://watchingtv20181217122242.azurewebsites.net";

    public InputStream sendRequest(URL url) throws Exception{
        try{
            HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return urlConnection.getInputStream();
            }
        }catch (Exception e){
            throw new Exception("");
        }
        return null;
    }



}
