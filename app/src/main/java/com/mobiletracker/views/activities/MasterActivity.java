package com.mobiletracker.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import com.mobiletracker.R;
import com.mobiletracker.core.utils.MContextWrapper;
import com.mobiletracker.core.utils.UserUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MasterActivity extends AppCompatActivity implements Callback {


    private int contentViewResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(contentViewResource);
        assignUIReferences();
        assignActions();
        getData();
    }

    protected abstract void assignUIReferences();

    protected abstract void assignActions();

    protected abstract void getData();

    protected abstract void onSuccess(@NonNull Call call, @NonNull Response response);

    protected abstract void onFailed(@NonNull Call call, @NonNull Throwable t);

    protected boolean checkFields(EditText... editTexts) {
        boolean allFieldsFilled = true;
        for (EditText editText : editTexts) {
            if (editText.getText().toString().equals("")) {
                allFieldsFilled = false;
                editText.setError(getString(R.string.required_field));
            }
        }
        return allFieldsFilled;
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        onSuccess(call, response);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        onFailed(call, t);
    }

    @Override
    public void setContentView(int layoutResID) {
        this.contentViewResource = layoutResID;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MContextWrapper.wrap(newBase, UserUtils.getDeviceLanguage(newBase.getApplicationContext())));
    }
}
