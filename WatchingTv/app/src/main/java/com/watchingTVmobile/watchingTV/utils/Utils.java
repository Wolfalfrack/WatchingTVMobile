package com.watchingTVmobile.watchingTV.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    public static final String BASE_URL = "https://watchingtv20181217122242.azurewebsites.net/";

    public static InputStream httpGet(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.connect();

        return connection.getInputStream();
    }

}