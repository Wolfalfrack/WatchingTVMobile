package com.watchingTVmobile.watchingTV.network.recherche;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.watchingTVmobile.watchingTV.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RechercheAsyncTaskLoader extends AsyncTaskLoader<RechercheResponse> {

    private Context mContext;

    private String mQuery;
    private String mPage;

    public RechercheAsyncTaskLoader(Context context, String query, String page) {
        super(context);
        this.mContext = context;
        this.mQuery = query;
        this.mPage = page;
    }

    @Override
    public RechercheResponse loadInBackground() {

        try {
            String urlString = "https://api.themoviedb.org/3/" + "search/multi"
                    + "?"
                    + "api_key=" + mContext.getResources().getString(R.string.MOVIE_DB_API_KEY)
                    + "&"
                    + "query=" + mQuery
                    + "&"
                    + "page=" + mPage
                    +"&language="+ "fr";
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() != 200) return null;

            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String jsonString = "";
            while (scanner.hasNext()) {
                jsonString += scanner.nextLine();
            }

            // Parse JSON
            JSONObject searchJsonObject = new JSONObject(jsonString);
            RechercheResponse rechercheResponse = new RechercheResponse();
            rechercheResponse.setPage(searchJsonObject.getInt("page"));
            rechercheResponse.setTotalPages(searchJsonObject.getInt("total_pages"));
            JSONArray resultsJsonArray = searchJsonObject.getJSONArray("results");
            List<RechercheResultat> rechercheResultats = new ArrayList<>();
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                JSONObject result = (JSONObject) resultsJsonArray.get(i);
                RechercheResultat rechercheResultat = new RechercheResultat();
                switch (result.getString("media_type")) {
                    case "movie":
                        rechercheResultat.setId(result.getInt("id"));
                        rechercheResultat.setPosterPath(result.getString("poster_path"));
                        rechercheResultat.setName(result.getString("title"));
                        rechercheResultat.setMediaType("movie");
                        rechercheResultat.setOverview(result.getString("overview"));
                        rechercheResultat.setReleaseDate(result.getString("release_date"));
                        break;
                    case "tv":
                        rechercheResultat.setId(result.getInt("id"));
                        rechercheResultat.setPosterPath(result.getString("poster_path"));
                        rechercheResultat.setName(result.getString("name"));
                        rechercheResultat.setMediaType("tv");
                        rechercheResultat.setOverview(result.getString("overview"));
                        rechercheResultat.setReleaseDate(result.getString("first_air_date"));
                        break;
                }
                rechercheResultats.add(rechercheResultat);
            }
            rechercheResponse.setResults(rechercheResultats);

            return rechercheResponse;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
