package omari.hamza.mobiletracker.views.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.iconics.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.controllers.UserController;
import omari.hamza.mobiletracker.core.models.Contact;
import omari.hamza.mobiletracker.core.models.ContactsResponse;
import omari.hamza.mobiletracker.core.models.MyResponse;
import omari.hamza.mobiletracker.core.utils.DeviceController;
import omari.hamza.mobiletracker.core.utils.GetContactDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceDetailsActivity extends MasterActivity implements OnMapReadyCallback {

    private Toolbar mToolbar;
    private MapView mapView;
    private GoogleMap googleMap;
    private Contact mContact;

    private TextView deviceTitleTextView, phoneTextView;
    private TextView soundTextView, vibrationTextView, locationTextView, contactTextView, allContactsTextView, eraseDataTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_device_details);
        super.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void assignUIReferences() {
        mToolbar = findViewById(R.id.mToolbar);
        mToolbar.setTitle(((Contact) getIntent().getSerializableExtra("contact")).getPhoneBrand() + " " + ((Contact) ((Contact) getIntent().getSerializableExtra("contact"))).getPhoneModel());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapView = findViewById(R.id.mapView);

        //--
        deviceTitleTextView = findViewById(R.id.device_title_textView);
        phoneTextView = findViewById(R.id.phone_textView);
        soundTextView = findViewById(R.id.sound_textView);
        vibrationTextView = findViewById(R.id.vibration_textView);
        locationTextView = findViewById(R.id.location_textView);
        contactTextView = findViewById(R.id.contact_textView);
        allContactsTextView = findViewById(R.id.all_contacts_textView);
        eraseDataTextView = findViewById(R.id.erase_data_textView);
    }

    @Override
    protected void assignActions() {
        soundTextView.setOnClickListener(e -> {
            DeviceController.sendRingCommand(DeviceDetailsActivity.this, mContact.getPhone());
        });
        vibrationTextView.setOnClickListener(e -> {
            DeviceController.vibrateDeviceCommand(DeviceDetailsActivity.this, mContact.getPhone());
        });
        locationTextView.setOnClickListener(e -> {
            DeviceController.sendLocationCommand(DeviceDetailsActivity.this, mContact.getPhone());
        });
        contactTextView.setOnClickListener(e -> {
            new GetContactDialog(DeviceDetailsActivity.this, mContact.getPhone()).show();
        });
        allContactsTextView.setOnClickListener(v -> {
            Toast.makeText(this, "Please Wait...", Toast.LENGTH_SHORT).show();
            UserController.getAllContacts(getApplicationContext(), new Callback<ContactsResponse>() {
                @Override
                public void onResponse(Call<ContactsResponse> call, Response<ContactsResponse> response) {
                    if (response.body().isSuccess()) {
                        try {
                            File file = new File(Environment.getExternalStorageDirectory() + "/" + response.body().getFriendContacts().get(0).getFriendName() + "_Contacts.vcf");
                            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
                            writer.write(response.body().getFriendContacts().get(0).getContacts());
                            writer.close();
                            Toast.makeText(DeviceDetailsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DeviceDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DeviceDetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ContactsResponse> call, Throwable t) {
                    Toast.makeText(DeviceDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void getData() {
        mContact = (Contact) getIntent().getSerializableExtra("contact");
        deviceTitleTextView.setText(mContact.getUsername() + " (" + mContact.getPhoneBrand() + " " + mContact.getPhoneModel() + ")");
        phoneTextView.setText(mContact.getPhone());
    }

    @Override
    protected void onSuccess(@NonNull Call call, @NonNull Response response) {

    }

    @Override
    protected void onFailed(@NonNull Call call, @NonNull Throwable t) {

    }

    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        googleMap = mGoogleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (mContact.getLongitude() == null || mContact.getLongitude().equals("null")) return;

        LatLng latLng = new LatLng(Double.valueOf(mContact.getLatitude()), Double.valueOf(mContact.getLongitude()));
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(deviceTitleTextView.getText().toString());


        // Clears the previously touched position
        googleMap.clear();

        // Animating to the touched position
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(mContact.getLatitude()), Double.valueOf(mContact.getLongitude())), 12.0f));


        // Placing a marker on the touched position
        googleMap.addMarker(markerOptions);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
