package com.watchingTVmobile.watchingTV.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.model.CurrentUtilisateurData;
import com.watchingTVmobile.watchingTV.tasks.GetUtilisateurTask;


public class InscriptionFragment extends Fragment implements View.OnClickListener, GetUtilisateurTask.Callback{
    private EditText edit_textViewUsername;
    private EditText edit_textViewPassword;
    private EditText edit_textViewPasswordconf;
    private Button buttonInscrire;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription,container,false);
        edit_textViewUsername =(EditText) view.findViewById(R.id.edit_text_user);
        edit_textViewPassword =(EditText) view.findViewById(R.id.edit_text_password);
        edit_textViewPasswordconf =(EditText) view.findViewById(R.id.edit_text_conf);
        buttonInscrire =(Button) view.findViewById(R.id.button);
        buttonInscrire.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button : launchAsyncTask(); break;
        }
    }

    private void launchAsyncTask(){
        if(!edit_textViewUsername.toString().equals("") && !edit_textViewPassword.toString().equals("") && edit_textViewPasswordconf.equals(edit_textViewPassword.toString())){
            /*ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info!=null && info.isConnected()){
                GetUtilisateurTask task = new GetUtilisateurTask(this);
                task.execute(edit_textViewUsername.getText().toString().trim());
                task.execute(edit_textViewPassword.getText().toString().trim());
            }*/
        }
    }

    @Override
    public void preUtilisateurExecute() {

    }

    @Override
    public void progressUtilisateurExecute(String message) {

    }

    @Override
    public void postUtilisateurExecute(CurrentUtilisateurData data) {
        if(data != null){
            Log.v("UTILISATEUR", data.getmUtilisateur().getUsername() + data.getmUtilisateur().getPassword());
        }
    }
}
