package android.project.kirbyskitchen.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseDataActivity {

    private HashMap<String, String> getRestaurant(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String lat = "";
        String lon = "";
        String ref = "";

        try {
            if(!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            lat = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            lon = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            ref = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name", placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", lat);
            googlePlacesMap.put("lng", lon);
            googlePlacesMap.put("reference", ref);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesMap;
    }
    private List<HashMap<String,String>> getRestaurants(JSONArray jArray) {
        int jlength = jArray.length();
        List <HashMap<String, String>>restaurantList = new ArrayList<>();
        HashMap<String,String> placeMap = null;
        for(int i = 0; i < jlength; i++) {
            try {
                placeMap = getRestaurant((JSONObject) jArray.get(i));
                restaurantList.add(placeMap);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return restaurantList;
    }
    public List<HashMap<String, String>> parse(String jData) {
        JSONArray jArray = null;
        JSONObject jObject;

        try{
            jObject = new JSONObject(jData);
            jArray = jObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRestaurants(jArray);
    }
}
