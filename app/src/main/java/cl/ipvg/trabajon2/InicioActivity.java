package cl.ipvg.trabajon2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    EditText txtlatitud, txtlongitud;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        txtlatitud = findViewById(R.id.txtlatitud);
        txtlongitud = findViewById(R.id.txtlongitud);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng chile = new LatLng(-33.4717787,-70.9594905);
        mMap.addMarker(new MarkerOptions().position(chile).title("Chile"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chile));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        txtlatitud.setText(""+latLng.latitude);
        txtlongitud.setText(""+latLng.longitude);
        mMap.clear();
        LatLng chile = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(chile).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chile));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        txtlatitud.setText(""+latLng.latitude);
        txtlongitud.setText(""+latLng.longitude);
        mMap.clear();
        LatLng chile = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(chile).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chile));

    }
}