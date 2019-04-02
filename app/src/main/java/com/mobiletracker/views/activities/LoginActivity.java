package com.mobiletracker.views.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import com.mobiletracker.R;
import com.mobiletracker.controllers.UserController;
import com.mobiletracker.core.models.MyResponse;
import com.mobiletracker.core.utils.LoadingDialog;
import com.mobiletracker.core.utils.UserUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends MasterActivity {


    private MaterialEditText usernameEditText;
    private MaterialEditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void assignUIReferences() {
        usernameEditText = findViewById(R.id.phone_editText);
        passwordEditText = findViewById(R.id.password_editText);
        loginButton = findViewById(R.id.register_button);
        registerTextView = findViewById(R.id.create_account_textView);
        mLoadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void assignActions() {
        loginButton.setOnClickListener(e -> {

            boolean flag = true;

            String[] perms = {
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            for (String perm : perms) {
                if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                    enablePermissions();
                    flag = false;
                    break;
                }
            }

            if (flag) {
                launchHome();
            }
        });

        registerTextView.setOnClickListener(e -> {
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        });
    }

    @Override
    protected void getData() {
        if (UserUtils.isUserLoggedIn(getApplicationContext())) {
            usernameEditText.setText(UserUtils.getLoggedUser(getApplicationContext()).getName());
        }
    }

    @Override
    protected void onSuccess(@NonNull Call call, @NonNull Response response) {

    }

    @Override
    protected void onFailed(@NonNull Call call, @NonNull Throwable t) {

    }

    private boolean isAdminLogin() {
        return usernameEditText.getText().toString().equals("123456") && passwordEditText.getText().toString().equals("123456");
    }

    private boolean allFieldsFilled() {
        boolean allFieldsFilled = true;
        if (usernameEditText.getText().toString().equals("")) {
            allFieldsFilled = false;
            usernameEditText.setError(getString(R.string.valid_mobile));
        }
        if (passwordEditText.getText().toString().length() < 6) {
            allFieldsFilled = false;
            passwordEditText.setError(getString(R.string.valid_password));
        }
        return allFieldsFilled;
    }

    private void enablePermissions() {
        //Enable All Permission
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.permissions_required))
                .setMessage(getString(R.string.enable_all_permissions))
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 0);
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            launchHome();
        }
    }

    private void launchHome() {
        if (isAdminLogin()) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
            return;
        }
        if (allFieldsFilled()) {
            mLoadingDialog.show();
            //noinspection all
            UserController.loginUser(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new Callback<MyResponse>() {
                @Override
                public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                    if (response.isSuccessful()) {
                        UserUtils.saveToken(getApplicationContext(), response.body().getToken());
                        UserController.getUserInfo(getApplicationContext(), new Callback<MyResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                                if (response.isSuccessful()) {
                                    mLoadingDialog.dismiss();
                                    UserUtils.saveUserInfo(getApplicationContext(), response.body().getUser());
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    finish();
                                } else {
                                    mLoadingDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<MyResponse> call, @NonNull Throwable t) {
                                mLoadingDialog.dismiss();
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mLoadingDialog.dismiss();
                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MyResponse> call, @NonNull Throwable t) {
                    mLoadingDialog.dismiss();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
