package com.mobiletracker.core.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import com.mobiletracker.R;
import com.mobiletracker.controllers.UserController;
import com.mobiletracker.core.models.MyResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectDeviceDialog {

    private Dialog mDialog;
    private Activity mActivity;
    private LoadingDialog mLoadingDialog;

    public ConnectDeviceDialog(Activity mActivity) {
        this.mActivity = mActivity;
        mLoadingDialog = new LoadingDialog(mActivity);
        mDialog = new Dialog(mActivity);
        mDialog.setContentView(R.layout.device_connect_dialog);
        MaterialEditText mobileEditText = mDialog.findViewById(R.id.phone_editText);
        Button mButton = mDialog.findViewById(R.id.connect_device_button);

        mButton.setOnClickListener(v -> {
            if (mobileEditText.getText().toString().length() < 13) {
                Toast.makeText(mActivity, mActivity.getString(R.string.valid_mobile), Toast.LENGTH_SHORT).show();
                return;
            }
            mobileEditText.setEnabled(false);
            mButton.setEnabled(false);
            UserController.addConnection(mActivity, mobileEditText.getText().toString(), new Callback<MyResponse>() {
                @Override
                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                    mDialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(mActivity, mActivity.getString(R.string.request_sent), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                    Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    mButton.setEnabled(true);
                    mobileEditText.setEnabled(true);
                }
            });
        });

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show() {
        mDialog.show();
    }
}
