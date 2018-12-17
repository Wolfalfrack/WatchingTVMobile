package com.watchingTVmobile.watchingTV.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.watchingTVmobile.watchingTV.model.CurrentUtilisateurData;
import com.watchingTVmobile.watchingTV.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

public class GetUtilisateurTask extends AsyncTask<String, String, CurrentUtilisateurData> {

    public interface Callback {
        void preUtilisateurExecute();
        void progressUtilisateurExecute(String message);
        void postUtilisateurExecute(CurrentUtilisateurData data);
    }

    private Callback callback;

    public GetUtilisateurTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.preUtilisateurExecute();
    }

    @Override
    protected CurrentUtilisateurData doInBackground(String... strings) {
        String urlString = Utils.BASE_URL + "/api/Utilisateur";
        try{
            InputStream inputStream = Utils.httpGet(urlString);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            return gson.fromJson(reader, CurrentUtilisateurData.class);
        }catch (MalformedURLException e ){
            publishProgress("Invalid url");
        }
        catch (IOException e) {
            publishProgress("Error");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        callback.progressUtilisateurExecute(values[0]);
    }

    @Override
    protected void onPostExecute(CurrentUtilisateurData currentWeatherData) {
        super.onPostExecute(currentWeatherData);
        callback.postUtilisateurExecute(currentWeatherData);
    }

}
