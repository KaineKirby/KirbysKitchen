package android.project.kirbyskitchen;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RestaurantActivity extends AppCompatActivity implements OnMapReadyCallback{
    //pulling the value for the id of the current contact

    private GoogleMap mMap;
    String address = "Sydney";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_restaurant);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    //the getAddress method gets the city that we will be displaying on the google map
    public void getAddress(View v) {
        EditText addressTxt = (EditText) findViewById(R.id.address_text);
        address = addressTxt.getText().toString();
        onMapReady(mMap);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //create a geocoder to take user input under address field and translate that to the google map api
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> add = geocoder.getFromLocationName(address, 1);
            //if the address does not reach the list for some reason
            if(add.size() < 0) {
                Toast.makeText(this, "Please enter a valid address.", Toast.LENGTH_LONG).show();
                //if the address is valid and can be found proceed with showing it on the google api
            } else {
                Address currAddress = add.get(0);
                LatLng currLocation = new LatLng(currAddress.getLatitude(), currAddress.getLongitude());

                mMap.addMarker(new MarkerOptions().position(currLocation).title(address));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            }
            //catch any errors that arise while attempting to translate address with geocoder
        } catch (IOException e) {
            Toast.makeText(this, "Please enter a valid address.", Toast.LENGTH_LONG).show();
        } catch (IndexOutOfBoundsException e2) {
            Toast.makeText(this, "Please enter a valid address.", Toast.LENGTH_LONG).show();
        }

    }
}