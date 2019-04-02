package com.mobiletracker.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import com.mobiletracker.R;
import com.mobiletracker.controllers.UserController;
import com.mobiletracker.core.models.Contact;
import com.mobiletracker.core.models.MyResponse;
import com.mobiletracker.core.utils.LoadingDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    //    private AirMapView mapView;
    private GoogleMap googleMap;
    private MapView mapView;
    private LoadingDialog mLoadingDialog;
    private ArrayList<Contact> contacts;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViewsById(view);
        getData();

        mapView.onCreate(savedInstanceState);
        return view;
    }

    private void getData() {
        if (getContext() == null || getActivity() == null) return;
        if (mLoadingDialog == null) mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.show();
        UserController.getContacts(getContext(), new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                mLoadingDialog.dismiss();
                contacts = new ArrayList<>(response.body().getContacts());
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                boolean includedPoints = false;
                if (googleMap != null) {
                    for (Contact contact : contacts) {
                        try {
                            MarkerOptions marker = new MarkerOptions()
                                    .position(new LatLng(Double.valueOf(contact.getLatitude()), Double.valueOf(contact.getLongitude())))
                                    .anchor(0.5f, 0.5f)
                                    .title(contact.getUsername() + " (" + contact.getPhoneBrand() + " " + contact.getPhoneModel() + ")");
                            googleMap.addMarker(marker);
                            builder.include(marker.getPosition());
                            includedPoints = true;
                        } catch (Exception ignored) {

                        }
                    }
                    if (!includedPoints) return;
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);
                    googleMap.animateCamera(cu);
                }

            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                mLoadingDialog.dismiss();
            }
        });
    }

    private void findViewsById(View view) {
        mapView = view.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        googleMap = mGoogleMap;
        if (contacts == null) {
            getData();
        } else {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            boolean includedPoints = false;
            for (Contact contact : contacts) {
                try {
                    MarkerOptions marker = new MarkerOptions()
                            .position(new LatLng(Double.valueOf(contact.getLatitude()), Double.valueOf(contact.getLongitude())))
                            .anchor(0.5f, 0.5f)
                            .title(contact.getUsername() + " (" + contact.getPhoneBrand() + " " + contact.getPhoneModel() + ")");
                    googleMap.addMarker(marker);
                    builder.include(marker.getPosition());
                    includedPoints = true;
                } catch (Exception ignored) {

                }
            }
            if (!includedPoints) return;
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            googleMap.animateCamera(cu);
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
