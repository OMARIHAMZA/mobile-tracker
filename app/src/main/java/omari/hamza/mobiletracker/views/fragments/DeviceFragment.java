package omari.hamza.mobiletracker.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.controllers.UserController;
import omari.hamza.mobiletracker.core.models.MyResponse;
import omari.hamza.mobiletracker.core.utils.LoadingDialog;
import omari.hamza.mobiletracker.views.activities.MainActivity;
import omari.hamza.mobiletracker.views.adapters.DevicesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LoadingDialog mLoadingDialog;

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
        if (getContext() == null || getActivity() == null) return;
        if (mLoadingDialog == null) mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.show();
        UserController.getContacts(getContext(), new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                mLoadingDialog.dismiss();
                if (response.isSuccessful()) {
                    mRecyclerView.setAdapter(new DevicesAdapter(getActivity(), response.body().getContacts()));
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                mLoadingDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void assignActions() {

    }

    private void findViewsById(View view) {
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
    }

}
