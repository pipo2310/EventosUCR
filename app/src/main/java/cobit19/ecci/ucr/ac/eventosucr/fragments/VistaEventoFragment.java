package cobit19.ecci.ucr.ac.eventosucr.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.ArrayList;

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
    View v;
    Evento evento;
    GoogleMap VistaEventoMap;
    UsuarioEventoService usuarioEventoService = new UsuarioEventoService();
    UsuarioEvento usuarioEvento = new UsuarioEvento();
    ImagenService imagenService = new ImagenService();
    Button btnMeInteresa;
    Button btnNoMeInteresa;
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


        // Pedimos las imagenes asociadas a un evento
        ArrayList<Imagen> imagenesEvento = imagenService.leerImagenEvento(getContext(), evento.getId());

        // Preguntamos si hay alguna imagen
        if(imagenesEvento.size() > 0){
            // Obtenemos el ImageView
            ImageView imagen = v.findViewById(R.id.imgEvento);
            // Agregamos la imagen del evento
            imagen.setImageBitmap(imagenesEvento.get(0).getImagen());
        }

        //Botón para que el usuario le de like a un evento
        btnMeInteresa = (Button)v.findViewById(R.id.btnMeInteresa);
        //Botón para que el usuario le quite like a un evento
        btnNoMeInteresa = (Button)v.findViewById(R.id.btnNoMeInteresa);

        //Saber si el evento existe en la lista UsuarioEvento
        usuarioEvento = usuarioEventoService.leer(getContext(), "walter.bonillagutierrez@ucr.ac.cr", evento.getId());
        if (usuarioEvento.getCorreoUcr() == null) {
            btnMeInteresa.setVisibility(View.VISIBLE);
            btnNoMeInteresa.setVisibility(View.GONE);
        }
        else {
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

        return v;
    }


    //Inserta los datos de la base de datos en la vista de un evento
    void leerEvento() {
        
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
        usuarioEventoService.insertar(getContext(), usuarioEvento);
        btnNoMeInteresa.setVisibility(View.VISIBLE);
        btnMeInteresa.setVisibility(View.GONE);
    }

    //Cuando el usuario le da click al botón, elimina el evento a la lista de favoritos del usuario
    public void QuitarMeGusta(){
        usuarioEventoService.eliminar(getContext(), usuarioEvento.getCorreoUcr(), usuarioEvento.getIdEvento());
        btnMeInteresa.setVisibility(View.VISIBLE);
        btnNoMeInteresa.setVisibility(View.GONE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        configurarMapa(googleMap);
    }

    //Permite realizar la configuración del mapa. Añade las coordenadas y el marcador  del evento correspondiente
    //Configura la cámara y el zoom
    public void configurarMapa(GoogleMap googleMap){
        VistaEventoMap = googleMap;

        //Se agregan las coordenadas del mapa
        LatLng ubicacionEvento = new LatLng(evento.getLatitud(), evento.getLongitud());

        //Se agrega el marcador en el mapa
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


        //Mover la cámara
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ubicacionEvento)
                .zoom(50)
                .bearing(70)
                .tilt(25)
                .build();
        VistaEventoMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
