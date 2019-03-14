package omari.hamza.mobiletracker.views.activities;

import android.bluetooth.BluetoothClass;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import omari.hamza.mobiletracker.R;
import omari.hamza.mobiletracker.core.utils.DeviceController;

public class DeviceDetailsActivity extends MasterActivity implements OnMapReadyCallback {

    private Toolbar mToolbar;
    private MapView mapView;
    private GoogleMap googleMap;

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
        mToolbar.setTitle("Samsung Galaxy S9+");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapView = findViewById(R.id.mapView);

        //--
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
            DeviceController.ringDevice(getApplicationContext());
        });
        vibrationTextView.setOnClickListener(e -> {
            DeviceController.vibrateDevice(getApplicationContext());
        });
        contactTextView.setOnClickListener(e -> {
            Toast.makeText(this, DeviceController.findContactByName(getApplicationContext(), "MOM"), Toast.LENGTH_SHORT).show();
        });
        allContactsTextView.setOnClickListener(e -> {
            DeviceController.getAllContacts(getApplicationContext());
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        googleMap = mGoogleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng latLng = new LatLng(35.5407, 35.7953);
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        // Clears the previously touched position
        googleMap.clear();

        // Animating to the touched position
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

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
