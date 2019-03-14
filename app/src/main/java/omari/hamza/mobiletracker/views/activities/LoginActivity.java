package omari.hamza.mobiletracker.views.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.controllers.UserController;
import omari.hamza.mobiletracker.core.api.UserServices;
import omari.hamza.mobiletracker.core.models.MyResponse;
import omari.hamza.mobiletracker.core.models.User;
import omari.hamza.mobiletracker.core.utils.LoadingDialog;
import omari.hamza.mobiletracker.core.utils.RetrofitClientInstance;
import omari.hamza.mobiletracker.core.utils.UserUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends MasterActivity {


    private final String CUSTOM_FONT = "cocon.ttf";

    private MaterialEditText mobileEditText;
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
        mobileEditText = findViewById(R.id.phone_editText);
        passwordEditText = findViewById(R.id.password_editText);
        loginButton = findViewById(R.id.login_button);
        registerTextView = findViewById(R.id.create_account_textView);
        mLoadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void assignActions() {
        loginButton.setOnClickListener(e -> {
            if (isAdminLogin()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return;
            }
            if (allFieldsFilled()) {
                mLoadingDialog.show();
                //noinspection all
                UserController.loginUser(mobileEditText.getText().toString(), passwordEditText.getText().toString(), new Callback<MyResponse>() {
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
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        });

        registerTextView.setOnClickListener(e -> {
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        });
    }

    @Override
    protected void getData() {
        if (UserUtils.isUserLoggedIn(getApplicationContext())) {
            mobileEditText.setText(UserUtils.getLoggedUser(getApplicationContext()).getMobile());
        }
    }

    private boolean isAdminLogin() {
        return mobileEditText.getText().toString().equals("123456") && passwordEditText.getText().toString().equals("123456");
    }

    private boolean allFieldsFilled() {
        boolean allFieldsFilled = true;
        if (mobileEditText.getText().toString().length() < 13) {
            allFieldsFilled = false;
            mobileEditText.setError(getString(R.string.valid_mobile));
        }
        if (passwordEditText.getText().toString().length() < 6) {
            allFieldsFilled = false;
            passwordEditText.setError(getString(R.string.valid_password));
        }
        return allFieldsFilled;
    }
}
