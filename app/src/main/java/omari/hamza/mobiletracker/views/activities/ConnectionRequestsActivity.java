package omari.hamza.mobiletracker.views.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.controllers.UserController;
import omari.hamza.mobiletracker.core.models.MyResponse;
import omari.hamza.mobiletracker.core.utils.LoadingDialog;
import omari.hamza.mobiletracker.views.adapters.ConnectionRequestsAdapter;
import retrofit2.Call;
import retrofit2.Response;

public class ConnectionRequestsActivity extends MasterActivity {

    private LoadingDialog mLoadingDialog;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection_requests);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void assignUIReferences() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mToolbar = findViewById(R.id.mToolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void assignActions() {

    }

    @Override
    protected void getData() {
        if (mLoadingDialog == null) mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
        UserController.getConnectionRequests(this, this);
    }

    @Override
    protected void onSuccess(@NonNull Call call, @NonNull Response response) {
        mLoadingDialog.dismiss();
        if (response.isSuccessful()) {
            mRecyclerView.setAdapter(new ConnectionRequestsAdapter(this, ((MyResponse) response.body()).getContacts()));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onFailed(@NonNull Call call, @NonNull Throwable t) {
        mLoadingDialog.dismiss();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void refreshRecyclerView(){
        mRecyclerView.setAdapter(new ConnectionRequestsAdapter(this, new ArrayList<>()));
        getData();
    }
}
