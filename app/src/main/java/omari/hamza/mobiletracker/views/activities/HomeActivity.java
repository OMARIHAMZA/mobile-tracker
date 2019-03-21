package omari.hamza.mobiletracker.views.activities;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.util.ArrayList;
import java.util.Locale;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.controllers.UserController;
import omari.hamza.mobiletracker.core.models.Contact;
import omari.hamza.mobiletracker.core.models.MyResponse;
import omari.hamza.mobiletracker.core.utils.ConnectDeviceDialog;
import omari.hamza.mobiletracker.core.utils.DemoDeviceAdminReceiver;
import omari.hamza.mobiletracker.core.utils.DeviceController;
import omari.hamza.mobiletracker.core.utils.LoadingDialog;
import omari.hamza.mobiletracker.core.utils.LocationService;
import omari.hamza.mobiletracker.core.utils.SMSListener;
import omari.hamza.mobiletracker.core.utils.UserUtils;
import omari.hamza.mobiletracker.views.fragments.DeviceFragment;
import omari.hamza.mobiletracker.views.fragments.MapFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends MasterActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SMSListener mReceiver;
    private LoadingDialog mLoadingDialog;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        setupDrawer();
        setupViewPager();
        mReceiver = new SMSListener();
        mLoadingDialog = new LoadingDialog(this);
        startService(new Intent(getApplicationContext(), LocationService.class));
        new Thread(() -> UserController.uploadContacts(getApplicationContext(), DeviceController.getVcardString(getApplicationContext()), new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                runOnUiThread(() -> Toast.makeText(HomeActivity.this, "TM", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                runOnUiThread(() -> Toast.makeText(HomeActivity.this, "MA TM", Toast.LENGTH_SHORT).show());
            }
        })).start();

        enableAdmin();
    }

    private void setupViewPager() {
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void assignUIReferences() {
        mToolbar = findViewById(R.id.mToolbar);
        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.mViewPager);
    }

    @Override
    protected void assignActions() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onSuccess(@NonNull Call call, @NonNull Response response) {

    }

    @Override
    protected void onFailed(@NonNull Call call, @NonNull Throwable t) {

    }

    private void setupDrawer() {
        PrimaryDrawerItem connectionRequestsItem = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.connection_requests);
        PrimaryDrawerItem connectDeviceItem = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.connect_device);
        PrimaryDrawerItem languageItem = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.change_language);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.logout);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withHeaderHeight(DimenHolder.fromDp(168))
                .withHeader(R.layout.custom_drawer_header)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        connectionRequestsItem,
                        connectDeviceItem,
                        languageItem,
                        new DividerDrawerItem(),
                        item2
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ((int) drawerItem.getIdentifier()) {
                        case 1: {
                            startActivity(new Intent(getApplicationContext(), ConnectionRequestsActivity.class));
                            break;
                        }
                        case 2: {
                            new ConnectDeviceDialog(this).show();
                            break;
                        }
                        case 3: {
                            if (UserUtils.getDeviceLanguage(getApplicationContext()).equalsIgnoreCase("en")) {
                                UserUtils.saveDeviceLanguage(getApplicationContext(), "ar");
                                setAppLocale("ar");
                            } else {
                                UserUtils.saveDeviceLanguage(getApplicationContext(), "en");
                                setAppLocale("en");
                            }
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                            break;
                        }
                        case 4: {
                            UserUtils.resetSharedPreferences(getApplicationContext());
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                            break;
                        }

                    }
                    return false;
                })
                .build();

        TextView usernameTextView = result.getHeader().findViewById(R.id.username_textView);
        TextView mobileTextView = result.getHeader().findViewById(R.id.phone_textView);

        usernameTextView.setText(UserUtils.getLoggedUser(getApplicationContext()).getName());
        mobileTextView.setText(UserUtils.getLoggedUser(getApplicationContext()).getMobile());

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MapFragment();
            } else if (position == 1) {
                return new DeviceFragment();
            }
            return new Fragment();
        }

        // This determines the number of tabs
        @Override
        public int getCount() {
            return 2;
        }

        // This determines the title for each tab
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {

                case 0:
                    return getString(R.string.map);

                case 1:
                    return getString(R.string.my_devices);

                default:
                    return null;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    private void setAppLocale(String localeCode) {
        UserUtils.saveDeviceLanguage(this, localeCode);
        String lang = UserUtils.getDeviceLanguage(this);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        config.setLayoutDirection(new Locale(lang));
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void enableAdmin() {
        ComponentName demoDeviceAdmin = new ComponentName(this, DemoDeviceAdminReceiver.class);
        Intent intent = new Intent(
                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                demoDeviceAdmin);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Enable Device Admin");
        startActivityForResult(intent, 0);
    }
}
