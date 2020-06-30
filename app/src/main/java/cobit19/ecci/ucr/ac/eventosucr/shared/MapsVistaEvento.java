package cobit19.ecci.ucr.ac.eventosucr.shared;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.features.vistaEvento.ObtenerDatosDirecciones;

public class MapsVistaEvento extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap vistaEventoMap;
    //Mapas
    private LatLng ubicacionEvento = new LatLng(0,0);
    private LatLng ubicacionActual = new LatLng(0,0);
    Location mLastKnownLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    StringBuilder sb;
    Object[] obtenerDir = new Object[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_vista_evento);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        //Para la localización actual del dispositivo
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        configurarMapa(googleMap);



    }

    public void configurarMapa(GoogleMap googleMap){
        vistaEventoMap = googleMap;

        double latitudEvento = getIntent().getDoubleExtra("latitud", 0);
        double longitudEvento = getIntent().getDoubleExtra("longitud", 0);
        String nombreEvento = getIntent().getStringExtra("nombre");

        //Se agregan las coordenadas del evento al mapa
        ubicacionEvento = new LatLng(latitudEvento, longitudEvento);
        //Se agrega el marcador del evento en el mapa
        vistaEventoMap.addMarker(new MarkerOptions().position(ubicacionEvento).title(nombreEvento));
        // Muevo el mapa hacia la posición del marcador
        vistaEventoMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionEvento));

        //Opciones de zoom
        UiSettings mapSettings;
        mapSettings = vistaEventoMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true); //controles zoom
        mapSettings.setZoomGesturesEnabled(true); //gestos zoom
        mapSettings.setCompassEnabled(true); //brújula
        mapSettings.setMyLocationButtonEnabled(true);
        mapSettings.setRotateGesturesEnabled(true); //rotación
        //mapSettings.setScrollGesturesEnabled(true); //Set Scroll

        //Mover la cámara
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ubicacionEvento)
                .zoom(15)
                .bearing(70)
                .tilt(25)
                .build();
        vistaEventoMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        obtenerRuta();
    }


    //Url para obtener las rutas desde un punto origen a un punto destino
    private String  obtenerUrl(LatLng orig, LatLng dest){
        //Asigna el valor de los parámetros
        String origen = "origin=" + orig.latitude + "," + orig.longitude;
        String destino = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=driving";
        String key = "key=AIzaSyAqZYlg_jAJqO6q9-tZa6-ntIZc_dgqUb4";
        String params = origen + "&" + destino + "&" + mode  + "&" + key;
        //Url para pedir direcciones
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + params;

        return url;
    }



    //Obtiene la ubicación actual del dispositivo
    //Marca la ruta desde la ubicación actual hasta la ubicación del evento

    private void obtenerRuta() {
        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    mLastKnownLocation = task.getResult();
                    //Obtiene la ubicación del dispositivo
                    ubicacionActual = new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude());

                    vistaEventoMap.addMarker(new MarkerOptions().position(ubicacionActual).title("Mi ubicación")
                            .icon(generarBitmap(getApplicationContext(), R.drawable.ubic_actual)));


                    //Mover la cámara a la ubicación actual
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(ubicacionActual)
                            .zoom(12)
                            .bearing(90)
                            .tilt(30)
                            .build();
                    vistaEventoMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    //Obtiene el url para la solicitud hhtp
                    sb = new StringBuilder();
                    sb.append(obtenerUrl(ubicacionActual, ubicacionEvento));

                    //Obtiene las direcciones del api de google
                    ObtenerDatosDirecciones obtenerDatosDirecciones = new ObtenerDatosDirecciones(getApplicationContext());
                    obtenerDir[0] = vistaEventoMap;
                    obtenerDir[1] = sb.toString();
                    obtenerDir[2] = new LatLng(ubicacionActual.latitude, ubicacionActual.longitude);
                    obtenerDir[3] = new LatLng(ubicacionEvento.latitude, ubicacionEvento.longitude);

                    obtenerDatosDirecciones.execute(obtenerDir);
                }
            }
        });
    }

    //Permite pasar drawables a bitmap (para agregar un imagen a un marcador)
    public static BitmapDescriptor generarBitmap(Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
