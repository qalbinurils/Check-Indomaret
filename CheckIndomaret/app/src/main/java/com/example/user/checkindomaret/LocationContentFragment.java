package com.example.user.checkindomaret;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationContentFragment extends Fragment implements View.OnClickListener {
    private ImageButton mButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_maps, container, false);
        mButton = (ImageButton) view.findViewById(R.id.gomap);
        mButton.setOnClickListener(LocationContentFragment.this);
        return view;

    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);
    }

}
