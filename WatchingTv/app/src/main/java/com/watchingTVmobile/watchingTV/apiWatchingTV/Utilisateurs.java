package com.watchingTVmobile.watchingTV.apiWatchingTV;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.watchingTVmobile.watchingTV.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Utilisateurs extends Fragment {
    public static final String URL_PATH = "https://watchingtv20190821081903.azurewebsites.net/api/utilisateur";
    private ListView listeView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textView;

    private ArrayList<Utilisateur> utilisateurs;

//    public ArrayList<Utilisateur> getUtilisateurs() {
//
//        try {
//            URL url = new URL(URL_PATH);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            //Comme il s'agit d'une requête GET, les propriétés à setter sont moins nombreuses.
//            urlConnection.setRequestProperty("Content-type", "application/json");
//            urlConnection.setReadTimeout(10000);
//            urlConnection.setConnectTimeout(15000); // millis
//            /*urlConnection.setDoOutput(true);*/
//            urlConnection.connect();
//
//            //Comme il s'agit d'un GET, il n'y a pas d'outputstream
//            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//            StringBuilder builder = new StringBuilder();
//            String result;
//
//
//            while((result = in.readLine()) != null){
//                builder.append(result);
//            }
//
//            in.close();
//
//            utilisateurs = new ArrayList<Utilisateur>();
//            JSONArray utilisateur = new JSONArray(builder.toString());
//            for(int i =0;i<utilisateur.length();i++){
//                utilisateurs.add(new Utilisateur(utilisateur.getJSONObject(i)));
//            }
//
//
//            return utilisateurs;
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public void deleteUtilisateur(int id) {
//
//        try {
//            URL url = new URL(URL_PATH + "/" +id);
//
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            //Comme il s'agit d'une requête GET, les propriétés à setter sont moins nombreuses.
//            urlConnection.setRequestProperty("Content-type", "application/json");
//            urlConnection.setRequestMethod("DELETE");
//
//            urlConnection.setReadTimeout(10000);
//            urlConnection.setConnectTimeout(15000); // millis
//            urlConnection.connect();
//            int responseCode = urlConnection.getResponseCode();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        List<Utilisateur> infras = getUtilisateurs();
//        View view = inflater.inflate(android.support.v4.R.layout.fragment_utilisateurs, container, false);
//        CustomListAdapterUtilisateurs adapter = new CustomListAdapterUtilisateurs(getActivity(), infras); /*Remplacer le 1 par l'id de la liste infras.get(0).getId()*/
//
//        listeView = (ListView) view.findViewById(android.support.v4.R.id.listViewInfrastructure);
//        listeView.setAdapter(adapter);
///*
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Swipe);
//        textView = (TextView) view.findViewById(R.id.tvSwipe);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                },2000);
//            }
//        });
//
// */
//
//
//
//
//        return view;
//    }
}
