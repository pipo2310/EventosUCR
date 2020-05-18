package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    PlacesClient placesClient;
    private double latitud;
    private double longitud;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String apikey = getResources().getString(R.string.google_maps_key);
        latitud = 9.9370;
        longitud = -84.0510;
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.btnConfirmarLocation);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addLocationAEvento();
                }catch (Exception e){}

            }
        });

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(MapActivity.this, apikey);
        }
        placesClient = Places.createClient(this);

        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete);

// Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(this);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG,Place.Field.NAME));

        autocompleteSupportFragment.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();

                        Toast.makeText(MapActivity.this, ""+latLng.latitude, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(MapActivity.this, ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void addLocationAEvento() {

        SharedPreferences sharedPref = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("latitud", Double.toString(latitud));
        editor.putString("longitud", Double.toString(longitud));
        editor.apply();
        /*
        Intent i =new Intent(this,CrearEvento.class);
        Bundle b = new Bundle();
        b.putDouble("Longitud",1.0);
        b.putDouble("Latitud",1.0);
        i.putExtras(b);

        startActivity(i);
         */
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // setear la ubicacion
        LatLng ubicacion = new LatLng(latitud, longitud);
        // agregar marcador
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicacion:"));
        // mover camara hacia el marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,17));
        mMap.setOnMapClickListener(this);
        // Asignamos el evento de clic largo en el mapa
        mMap.setOnMapLongClickListener(this);

        //mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        // cuando se hace clic sobre el mapa se muestran las coordenadas
        // nos movemos a la posicion donde hizo clic
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        // mostramos las coordenadas
        Toast.makeText(getApplicationContext(), "Si desea seleccionar esta ubicacion debe dejar presionado",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Agregamos un marcador cuando el usuario deja presionado un punto en el mapa
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(latLng.toString())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        // Mensaje
        Toast.makeText(getApplicationContext(),
                "Ubicacion seleccionada: " + latLng.toString(), Toast.LENGTH_LONG).show();
        latitud= latLng.latitude;
        longitud= latLng.longitude;
    }
}
