package com.mobiletracker.views.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import com.mobiletracker.R;
import com.mobiletracker.controllers.UserController;
import com.mobiletracker.core.models.MyResponse;
import com.mobiletracker.core.models.User;
import com.mobiletracker.core.utils.LoadingDialog;
import com.mobiletracker.core.utils.UserUtils;
import retrofit2.Call;
import retrofit2.Response;

public class SignupActivity extends MasterActivity {

    private MaterialEditText usernameEditText, passwordEditText, mobileEditText;
    private Button signupButton;
    private Toolbar mToolbar;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_signup);
        super.onCreate(savedInstanceState);
        mLoadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void assignUIReferences() {
        mToolbar = findViewById(R.id.mToolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        //noinspection all
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usernameEditText = findViewById(R.id.username_editText);
        passwordEditText = findViewById(R.id.password_editText);
        mobileEditText = findViewById(R.id.phone_editText);
        signupButton = findViewById(R.id.register_button);
    }

    @Override
    protected void assignActions() {
        signupButton.setOnClickListener(v -> {
            if (checkFields(usernameEditText, passwordEditText, mobileEditText)) {
                mLoadingDialog.show();
                UserController.registerUser(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        mobileEditText.getText().toString(),
                        Build.BRAND,
                        Build.MODEL,
                        this);
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onSuccess(@NonNull Call call, @NonNull Response response) {
        mLoadingDialog.dismiss();
        if (response.isSuccessful()) {
            UserUtils.saveToken(getApplicationContext(), ((MyResponse) response.body()).getToken());
            UserUtils.saveUserInfo(getApplicationContext(), new User(usernameEditText.getText().toString(),
                    mobileEditText.getText().toString()));
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onFailed(@NonNull Call call, @NonNull Throwable t) {
        mLoadingDialog.dismiss();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
