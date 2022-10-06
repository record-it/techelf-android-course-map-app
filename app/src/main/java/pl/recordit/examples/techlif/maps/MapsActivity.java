package pl.recordit.examples.techlif.maps;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import pl.recordit.examples.techlif.maps.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getView().setClickable(false);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng warsaw = new LatLng(52, 21);
        Marker inWarsaw = mMap.addMarker(
                new MarkerOptions()
                        .position(warsaw)
                        .title("Marker in Warsaw")
                        .anchor(0,0)
                        .alpha(0.5f)
                        .snippet("Stolica Polski")
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        inWarsaw.setTag("Dowolne dane");
        String data = (String) inWarsaw.getTag();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(warsaw);
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(3), 2000, null);
        mMap.moveCamera(cameraUpdate);
        mMap.setOnCameraMoveStartedListener((e) -> {
            Toast.makeText(this.getBaseContext(), "Camera start move event", Toast.LENGTH_SHORT).show();
        });
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(warsaw)
                .zoom(7)
                .bearing(90)
                .tilt(0)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LatLngBounds adelaideBounds = new LatLngBounds(
                new LatLng(49, 16), // SW bounds
                new LatLng(54, 23)  // NE bounds
        );
        mMap.setLatLngBoundsForCameraTarget(adelaideBounds);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

        mMap.setOnMapClickListener((e) -> {
            Toast.makeText(this.getBaseContext(), "Click on map", Toast.LENGTH_SHORT).show();
        });
        mMap.setOnMarkerClickListener(e -> {
            Toast.makeText(this.getBaseContext(), "Click on marker", Toast.LENGTH_SHORT).show();
            return true;
        });
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(37.4, -122.1))
                .radius(1000);      // metry
        Circle circle = mMap.addCircle(circleOptions);
        PolygonOptions polygonOptions = new PolygonOptions()
                .add(new LatLng(37.35, -122.0),
                        new LatLng(37.45, -122.0),
                        new LatLng(37.45, -122.2),
                        new LatLng(37.35, -122.2),
                        new LatLng(37.35, -122.0));
        Polygon polygon = mMap.addPolygon(polygonOptions);
    }
}