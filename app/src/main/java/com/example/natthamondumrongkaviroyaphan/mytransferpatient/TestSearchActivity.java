package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;

public class TestSearchActivity extends FragmentActivity implements OnMapReadyCallback {

    // Google Map
    private GoogleMap googleMap;

    // Latitude & Longitude
    private Double Latitude  = 13.844205;
    private Double Longitude  = 100.598856;

    // RadioButton
    RadioButton rdoNormal, rdoHybrid, rdoSatellite, rdoTerrain;
    Button btnSearch;
    LatLng latLng;
    MarkerOptions markerOptions;
    EditText txtKeyword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_search_activity);

         txtKeyword = (EditText) findViewById(R.id.txtKeyword);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new GeocoderTask().execute(txtKeyword.getText().toString());
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap mMap) {
        this.googleMap = mMap;
        LatLng coordinate = new LatLng(Latitude, Longitude);

        //*** Focus & Zoom
        googleMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));

        //*** Zoom Control
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        //*** Button Search


    }
    //*** An AsyncTask Background Process
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

        @Override
        protected List<Address> doInBackground(String... locationName) {
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            googleMap.clear();

            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){

                Address address = (Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);

                googleMap.addMarker(markerOptions);

                // Locate the first location
                if(i==0)
                {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        }
    }

}