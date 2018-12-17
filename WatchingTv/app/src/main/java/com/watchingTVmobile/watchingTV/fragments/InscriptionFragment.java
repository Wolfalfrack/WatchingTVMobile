package com.watchingTVmobile.watchingTV.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watchingTVmobile.watchingTV.R;


public class InscriptionFragment extends Fragment {
    private TextView textViewUsername;
    private TextView textViewPassword;
    private TextView textViewPasswordconf;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription,container,false);
        textViewUsername =(TextView) view.findViewById(R.id.edit_text_user);
        textViewPassword =(TextView) view.findViewById(R.id.edit_text_password);
        textViewPasswordconf =(TextView) view.findViewById(R.id.edit_text_conf);
        return view;
    }
}
