package omari.hamza.mobiletracker.views.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import omari.hamza.mobiletracker.R;

public class SignupActivity extends MasterActivity {

    private MaterialEditText usernameEditText, passwordEditText, mobileEditText;
    private Button signupButton;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_signup);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void assignUIReferences() {
        mToolbar = findViewById(R.id.mToolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        //noinspection all
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void assignActions() {

    }

    @Override
    protected void getData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
