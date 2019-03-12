package omari.hamza.mobiletracker.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class MasterActivity extends AppCompatActivity {


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

    @Override
    public void setContentView(int layoutResID) {
        this.contentViewResource = layoutResID;
    }
}
