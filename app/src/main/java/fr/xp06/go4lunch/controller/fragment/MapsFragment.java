package fr.xp06.go4lunch.controller.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;

import fr.xp06.go4lunch.R;
import fr.xp06.go4lunch.controller.RestaurantActivity;
import fr.xp06.go4lunch.model.details.PlaceDetailsResponse;
import fr.xp06.go4lunch.model.firestore.User;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 789;

    public static final String INTENT_EXTRAS_RESULT_MAPS = "INTENT_EXTRAS_RESULT_MAPS";
    public static final String INTENT_EXTRAS_PLACEDETAILSRESPONSE_MAPS = "INTENT_EXTRAS_PLACEDETAILSRESPONSE_MAPS";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MapsFragment() {
    }

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (currentLat != null && currentLng != null) {
            updateWithPosition();
        } else {
            recoversData();
        }
    }

    @Override
    protected void updateWithPosition() {
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                LatLng currentLocation = new LatLng(currentLat, currentLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    private void setMarker() {
        for (int position = 0; mParcelableRestaurantDetails.getNearbyResults().size() > position; position++) {
            LatLng restaurant = new LatLng(mParcelableRestaurantDetails.getNearbyResults()
                    .get(position).getGeometry().getLocation().getLat(),
                    mParcelableRestaurantDetails.getNearbyResults().get(position).getGeometry().getLocation().getLng());
            mMap.addMarker(new MarkerOptions().position(restaurant)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant)))
                    .setTag(position);

            // Instantiation of CircleOptions to draw a circle around the marker
            CircleOptions circleOptions = new CircleOptions();
            // Specify the center of the circle
            circleOptions.center(new LatLng(currentLat, currentLng));
            // Radius of the circle
            circleOptions.radius(1000);
            // Colour of the border of the circle
            circleOptions.strokeColor(Color.TRANSPARENT);
            // Fill color of the circle
            circleOptions.fillColor(0x995ff00);
            // Edge width of the circle
            circleOptions.strokeWidth(2);
            // Adding the Circle to Google Maps
            mMap.addCircle(circleOptions);

            for (User user : usersList) {
                if (mParcelableRestaurantDetails.getNearbyResults().get(position).getPlaceId().equals(user.getUserChoicePlaceId())) {
                    mMap.addMarker(new MarkerOptions().position(restaurant)
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_choice)))
                            .setTag(position);
                }
            }
        }

    }

    /**
     * Call when a user click on a marker. Start a new activity to see restaurant details.
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        int position = (int) marker.getTag();
        Intent intent = new Intent(getActivity(), RestaurantActivity.class);
        intent.putExtra(INTENT_EXTRAS_RESULT_MAPS, mParcelableRestaurantDetails.getNearbyResults().get(position));
        intent.putExtra(INTENT_EXTRAS_PLACEDETAILSRESPONSE_MAPS, mParcelableRestaurantDetails.getPlaceDetailsResponses().get(position));
        startActivity(intent);
        return false;
    }

    /**
     * Clean old markers.
     * Notify fragment that the data has changed.
     */
    @Override
    protected void notifyFragment() {
        if (mMap != null) {
            mMap.clear();
            setMarker();
        }
    }

}
