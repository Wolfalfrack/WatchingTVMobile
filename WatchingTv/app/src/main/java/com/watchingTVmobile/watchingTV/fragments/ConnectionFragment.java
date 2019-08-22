package com.watchingTVmobile.watchingTV.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.watchingTVmobile.watchingTV.R;

public class ConnectionFragment extends Fragment {
    private EditText edit_textViewUser;
    private EditText edit_textViewPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection,container,false);
        edit_textViewUser =(EditText) view.findViewById(R.id.editText_User);
        edit_textViewPassword =(EditText) view.findViewById(R.id.editText_pas);
        return view;
    }


}
