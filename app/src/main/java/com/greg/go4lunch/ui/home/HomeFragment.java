package com.greg.go4lunch.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.greg.go4lunch.R;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final float DEFAULT_ZOOM = 17.0f;
    public static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mapView = view.findViewById(R.id.map);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        locationAccuracy();
        zoomOnLocation();
        noLandMarksFilter(googleMap);
    }

    // ---------------------------- Location accuracy ----------------------------------------------
    private void locationAccuracy(){
        LatLng santaMonica = new LatLng(34.017434, -118.491768);
        mMap.addMarker(new MarkerOptions().position(santaMonica).title("I'm here and I'm hungry !"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(santaMonica));
    }

    // ---------------------------- Zoom level -----------------------------------------------------
    private void zoomOnLocation(){
        mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));
    }

    // ---------------------------- No Landmarks on Maps -------------------------------------------
    private void noLandMarksFilter(GoogleMap googleMap){
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }
}