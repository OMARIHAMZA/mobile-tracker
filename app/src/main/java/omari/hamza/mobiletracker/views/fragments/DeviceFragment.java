package omari.hamza.mobiletracker.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.views.adapters.DevicesAdapter;

public class DeviceFragment extends Fragment {

    private RecyclerView mRecyclerView;


    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        findViewsById(view);
        assignActions();
        getData();
        return view;
    }

    private void getData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new DevicesAdapter(getActivity()));
    }

    private void assignActions() {

    }

    private void findViewsById(View view) {
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
    }

}
