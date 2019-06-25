package com.example.plkvoice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plkvoice.Interface.UILConfig;
import com.nostra13.universalimageloader.core.ImageLoader;


public class HomeFragment extends Fragment {
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageLoader.getInstance().init(UILConfig.config(HomeFragment.this.getActivity()));
        Toolbar toolbar = this.getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        return view;
    }
}
