package com.watchingTVmobile.watchingTV.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watchingTVmobile.watchingTV.R;

public class ConnectionFragment extends Fragment {
    private TextView textViewUser;
    private TextView textViewPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection,container,false);
        textViewUser =(TextView) view.findViewById(R.id.textView_User);
        textViewPassword =(TextView) view.findViewById(R.id.textView_Password);
        return view;
    }
}
