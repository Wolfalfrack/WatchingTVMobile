package com.watchingTVmobile.watchingTV.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.watchingTVmobile.watchingTV.R;


public class InscriptionFragment extends Fragment {
    private EditText edit_textViewUsername;
    private EditText edit_textViewPassword;
    private EditText edit_textViewPasswordconf;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription,container,false);
        edit_textViewUsername =(EditText) view.findViewById(R.id.edit_text_user);
        edit_textViewPassword =(EditText) view.findViewById(R.id.edit_text_password);
        edit_textViewPasswordconf =(EditText) view.findViewById(R.id.edit_text_conf);
        return view;
    }
}
