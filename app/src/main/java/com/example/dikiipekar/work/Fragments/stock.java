package com.example.dikiipekar.work.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.dikiipekar.work.R;

public class Stock extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

           View rootView =  inflater.inflate(R.layout.fragment_stock,container,false);
        return rootView;
    }


}
