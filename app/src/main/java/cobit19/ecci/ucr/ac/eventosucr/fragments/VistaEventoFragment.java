package cobit19.ecci.ucr.ac.eventosucr.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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

import java.text.DateFormat;
import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.ComentariosActivity;
import cobit19.ecci.ucr.ac.eventosucr.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.ObtenerDatosDirecciones;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Imagen;
import cobit19.ecci.ucr.ac.eventosucr.core.models.UsuarioEvento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.UsuarioEventoService;

/**
 * A simple {@link Fragment} subclass.
 */
public class  VistaEventoFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 101;

    View v;
    Evento evento;
    GoogleMap VistaEventoMap;
    UsuarioEventoService usuarioEventoService = new UsuarioEventoService();
    UsuarioEvento usuarioEvento = new UsuarioEvento();
    ImagenService imagenService = new ImagenService();
    Button btnMeInteresa;
    Button btnNoMeInteresa;
    Button btnVerRuta;
    TextView comentarios;
    public static final String EXTRA_MESSAGE_VE = "EVENTO_ID";

    private boolean eliminarDeFavoritos = false;

    //Mapas
    private LatLng ubicacionEvento = new LatLng(0,0);
    private LatLng ubicacionActual = new LatLng(0,0);
    Location mLastKnownLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    StringBuilder sb;
    Object[] obtenerDir = new Object[4];


    public VistaEventoFragment() {
    }

    public VistaEventoFragment(Evento evento) {
        this.evento = evento;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_vista_evento, container, false);
        leerEvento();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.ubicacionMap);
        mapFragment.getMapAsync(this);

        //Para la localización actual del dispositivo
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mostrarImagenes();

        comentarios = v.findViewById(R.id.ir_a_comentarios);
        comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iraFragmentComentarios();
            }
        });

        //Botón para que el usuario le de like a un evento
        btnMeInteresa = (Button) v.findViewById(R.id.btnMeInteresa);
        //Botón para que el usuario le quite like a un evento
        btnNoMeInteresa = (Button) v.findViewById(R.id.btnNoMeInteresa);
        //Botón para que el usuario vea la ruta
        btnVerRuta = (Button) v.findViewById(R.id.btnVerRuta);

        //Saber si el evento existe en la lista UsuarioEvento
        usuarioEvento = usuarioEventoService.leer(getContext(), Constantes.CORREO_UCR_USUARIO, evento.getId());
        if (usuarioEvento.getCorreoUcr() == null) {
            btnMeInteresa.setVisibility(View.VISIBLE);
            btnNoMeInteresa.setVisibility(View.GONE);
        } else {
            btnNoMeInteresa.setVisibility(View.VISIBLE);
            btnMeInteresa.setVisibility(View.GONE);
        }


        btnMeInteresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DarMeGusta();
            }
        });

        btnNoMeInteresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuitarMeGusta();
            }
        });

        btnVerRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerRuta();
            }
        });


        return v;
    }

    private void iraFragmentComentarios() {
        // Pasar a la siguiente actividad
        Intent intent = new Intent(getContext(), ComentariosActivity.class);
        intent.putExtra(EXTRA_MESSAGE_VE, evento);
        // Deseo recibir una respuesta: startActivityForResult()
        startActivityForResult(intent, 0);
    }
    
    public void mostrarImagenes() {
        // Pedimos las imagenes asociadas a un evento
        ArrayList<Imagen> imagenesEvento = imagenService.leerImagenEvento(getContext(), evento.getId());
        ImageView imagen;
        LinearLayout imagenes=(LinearLayout)v.findViewById(R.id.slideImg);

        // Preguntamos si hay alguna imagen
        if(imagenesEvento.size() > 0){
            for(int i = 0; i < imagenesEvento.size(); i++) {
                // Obtenemos el ImageView
                imagen = v.findViewById(R.id.imgEvento);
                // Agregamos la imagen del evento
                imagen.setImageBitmap(imagenesEvento.get(i).getImagen());

                float scale = getContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (220 * scale + 0.5f);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        pixels
                );
                imagen.setLayoutParams(params);
                //imagen.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                //imagen.setAdjustViewBounds(true);
                if(imagen.getParent() != null) {
                    ((ViewGroup)imagen.getParent()).removeView(imagen); // <- fix
                }
                imagenes.addView(imagen);
            }

        }

    }

    //Inserta los datos de la base de datos en la vista de un evento
    public void leerEvento() {
        
        TextView nombreDeEvento = (TextView)v.findViewById(R.id.nombreDeEvento);
        nombreDeEvento.setText(evento.getNombre());
        TextView creadorEvento = (TextView)v.findViewById(R.id.creadorEvento);
        creadorEvento.setText(evento.getInstitucion(getContext()).getNombre());
        TextView fecha = (TextView) v.findViewById(R.id.fecha);
        fecha.setText(DateFormat.getDateInstance(DateFormat.FULL).format(evento.getFecha().getTime()));
        TextView hora = (TextView)v.findViewById(R.id.hora);
        hora.setText(evento.getHoraInicio() + " - "+ evento.getHoraFin());
        TextView ubicacion = (TextView)v.findViewById(R.id.ubicacion);
        ubicacion.setText(evento.getUbicacion());
        TextView detalles = (TextView)v.findViewById(R.id.descripcionDetalles);
        detalles.setText(evento.getDetalles());
    }

    //Cuando el usuario le da click al botón, añade el evento a la lista de favoritos del usuario
    public void DarMeGusta(){
        usuarioEventoService.insertar(getContext(), new UsuarioEvento(Constantes.CORREO_UCR_USUARIO, evento.getId()));
        btnNoMeInteresa.setVisibility(View.VISIBLE);
        btnMeInteresa.setVisibility(View.GONE);
        eliminarDeFavoritos = false;
    }

    //Cuando el usuario le da click al botón, elimina el evento a la lista de favoritos del usuario
    public void QuitarMeGusta(){
        usuarioEventoService.eliminar(getContext(), usuarioEvento.getCorreoUcr(), usuarioEvento.getIdEvento());
        btnMeInteresa.setVisibility(View.VISIBLE);
        btnNoMeInteresa.setVisibility(View.GONE);
        eliminarDeFavoritos = true;
    }


    //Mapas

    //Permisos para acceder a la ubicacion  del dispositivo
    protected void requestPermission(String permissionType,int requestCode) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permissionType}, requestCode
        );
    }


    //Permisos de ubicacion
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),
                            "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show();
                } else {
                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getChildFragmentManager()
                                    .findFragmentById(R.id.ubicacionMap);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        configurarMapa(googleMap);
    }

    //Permite realizar la configuración del mapa. Añade las coordenadas y el marcador  del evento correspondiente
    //Configura la cámara y el zoom
    public void configurarMapa(GoogleMap googleMap){
        VistaEventoMap = googleMap;


        //Permiso de acceso a la ubicación del dispositivo
        if (VistaEventoMap != null) {
            int permission = ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                VistaEventoMap.setMyLocationEnabled(true);
            } else {
                requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_REQUEST_CODE);
            }
        }

        //Se agregan las coordenadas del evento al mapa
        ubicacionEvento = new LatLng(evento.getLatitud(), evento.getLongitud());


        //Se agrega el marcador del evento en el mapa
        VistaEventoMap.addMarker(new MarkerOptions().position(ubicacionEvento).title(evento.getNombre()));


        // Muevo el mapa hacia la posición del marcador
        VistaEventoMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionEvento));

        //Opciones de zoom
        UiSettings mapSettings;
        mapSettings = VistaEventoMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true); //controles zoom
        mapSettings.setZoomGesturesEnabled(true); //gestos zoom
        mapSettings.setCompassEnabled(true); //brújula
        mapSettings.setMyLocationButtonEnabled(true);
        mapSettings.setRotateGesturesEnabled(true); //rotación
        mapSettings.setScrollGesturesEnabled(true); //Set Scroll

        //Mover la cámara
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ubicacionEvento)
                .zoom(15)
                .bearing(70)
                .tilt(25)
                .build();
        VistaEventoMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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

                    Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.calendar_azul);
                    VistaEventoMap.addMarker(new MarkerOptions().position(ubicacionActual).title("Mi ubicación")
                            .icon(generarBitmap(getContext(), R.drawable.ubic_actual)));


                    //Mover la cámara a la ubicación actual
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(ubicacionActual)
                            .zoom(12)
                            .bearing(90)
                            .tilt(30)
                            .build();
                    VistaEventoMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    //Obtiene el url para la solicitud hhtp
                    sb = new StringBuilder();
                    sb.append(obtenerUrl(ubicacionActual, ubicacionEvento));

                    //Obtiene las direcciones del api de google
                    ObtenerDatosDirecciones obtenerDatosDirecciones = new ObtenerDatosDirecciones(getContext());
                    obtenerDir[0] = VistaEventoMap;
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

    public String getTagEliminar() {
        String tag = null;
        if(eliminarDeFavoritos == true){
            tag = Constantes.EVENTO_FAV_TAG + evento.getId();
        }
        return tag;
    }
}
