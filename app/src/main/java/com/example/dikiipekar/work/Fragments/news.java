package com.example.dikiipekar.work.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;

import com.example.dikiipekar.work.R;


public class News extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View rootView =  inflater.inflate(R.layout.fragment_news, container, false);
        return rootView;
    }


}
