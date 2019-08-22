package com.watchingTVmobile.watchingTV.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.adapters.FavorisPageAdapter;


public class FavorisFragment extends Fragment {

    private SmartTabLayout mSmartTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoris, container, false);

        mSmartTabLayout = (SmartTabLayout) view.findViewById(R.id.tab_view_pager_fav);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_fav);
        mViewPager.setAdapter(new FavorisPageAdapter(getChildFragmentManager(), getContext()));
        mSmartTabLayout.setViewPager(mViewPager);

        return view;
    }
}
