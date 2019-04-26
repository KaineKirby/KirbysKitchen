package android.project.kirbyskitchen.Restaurant;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyRestaurantsActivity extends AsyncTask<Object, String, String> {
    private String googlePlacesData;
    private GoogleMap mMap;
    String dataURL;
    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        dataURL = (String)objects[1];
        URLDownloadActivity urlDownload = new URLDownloadActivity();
        try {
            googlePlacesData = urlDownload.readURL(dataURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }
    @Override
    protected void onPostExecute(String s) {
        System.out.println("STRING S: " + s);
        List<HashMap<String, String>> nearbyRestaurantsList;
        ParseDataActivity p = new ParseDataActivity();
        nearbyRestaurantsList = p.parse(s);
        System.out.println("RESTRANT: " + nearbyRestaurantsList.get(0).get("place_name"));
        showNearbyRestaurants(nearbyRestaurantsList);

    }
    private void showNearbyRestaurants(List<HashMap<String,String>> nearbyRestaurantList) {
        for(int i = 0; i < nearbyRestaurantList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyRestaurantList.get(i);
            System.out.println("NEARBY RESTAURANT GET: " + nearbyRestaurantList.get(i).get("place_name"));

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
