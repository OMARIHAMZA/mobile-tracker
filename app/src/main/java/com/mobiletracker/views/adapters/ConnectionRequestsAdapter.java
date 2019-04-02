package com.mobiletracker.views.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.mobiletracker.R;
import com.mobiletracker.controllers.UserController;
import com.mobiletracker.core.models.Contact;
import com.mobiletracker.core.models.MyResponse;
import com.mobiletracker.core.utils.LoadingDialog;
import com.mobiletracker.views.activities.ConnectionRequestsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionRequestsAdapter extends RecyclerView.Adapter<ConnectionRequestsAdapter.MyViewHolder> {

    private ArrayList<Contact> contacts;
    private Activity mActivity;
    private LoadingDialog mLoadingDialog;

    public ConnectionRequestsAdapter(Activity mActivity, ArrayList<Contact> contacts) {
        this.mActivity = mActivity;
        this.contacts = new ArrayList<>(contacts);
        mLoadingDialog = new LoadingDialog(mActivity);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.connection_request_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Contact currentContact = contacts.get(i);
        myViewHolder.usernameTextView.setText(currentContact.getUsername() + " (" + currentContact.getPhoneModel() + ")");
        myViewHolder.phoneTextView.setText(currentContact.getPhone());
        myViewHolder.acceptButton.setOnClickListener(v -> {
            mLoadingDialog.show();
            UserController.acceptConnectionRequest(mActivity, String.valueOf(currentContact.getConnectionRequestId()), new Callback<MyResponse>() {
                @Override
                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                    mLoadingDialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(mActivity, mActivity.getString(R.string.connection_accepted), Toast.LENGTH_SHORT).show();
                        ((ConnectionRequestsActivity) mActivity).refreshRecyclerView();
                    } else {
                        Toast.makeText(mActivity, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                    mLoadingDialog.dismiss();
                    Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        myViewHolder.rejectButton.setOnClickListener(v -> {
            mLoadingDialog.show();
            UserController.rejectConnectionRequest(mActivity, String.valueOf(currentContact.getConnectionRequestId()), new Callback<MyResponse>() {
                @Override
                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                    mLoadingDialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(mActivity, mActivity.getString(R.string.connection_rejected), Toast.LENGTH_SHORT).show();
                        ((ConnectionRequestsActivity) mActivity).refreshRecyclerView();
                    } else {
                        Toast.makeText(mActivity, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                    mLoadingDialog.dismiss();
                    Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView usernameTextView;
        private TextView phoneTextView;
        private CardView acceptButton, rejectButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.usernameTextView = itemView.findViewById(R.id.username_textView);
            this.phoneTextView = itemView.findViewById(R.id.phone_textView);
            this.acceptButton = itemView.findViewById(R.id.accept_connection_cardView);
            this.rejectButton = itemView.findViewById(R.id.reject_connection_cardView);
        }
    }
}
