package cobit19.ecci.ucr.ac.eventosucr.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.shared.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class  VistaEventoFragment extends Fragment implements OnMapReadyCallback {
    View v;
    Evento evento;
    GoogleMap VistaEventoMap;
    Button btnMeInteresa;
    Button btnNoMeInteresa;
    private boolean eliminarDeFavoritos = false;

    // Por ahora voy a hacerlo asi, pero hay que cambiarlo para que siempre sea el id del usuario actual
    private String usuarioId = Constantes.CORREO_UCR_USUARIO.replaceAll("@(.)*", "");

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


        //Botón para que el usuario le de like a un evento
        btnMeInteresa = (Button)v.findViewById(R.id.btnMeInteresa);
        //Botón para que el usuario le quite like a un evento
        btnNoMeInteresa = (Button)v.findViewById(R.id.btnNoMeInteresa);

        String eventoId = evento.getNombre().replaceAll(" ", "");
        // FIRESTORE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Saber si el evento existe en la lista UsuarioEvento
        db.collection("meInteresaUsuarioEvento").document(usuarioId)
                .collection("eventos")
                .document(eventoId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    btnNoMeInteresa.setVisibility(View.VISIBLE);
                    btnMeInteresa.setVisibility(View.GONE);
                } else {
                    btnMeInteresa.setVisibility(View.VISIBLE);
                    btnNoMeInteresa.setVisibility(View.GONE);
                }
            }
        });


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
        creadorEvento.setText(evento.getOrganizador());
        TextView fecha = (TextView) v.findViewById(R.id.fecha);
        fecha.setText(DateFormat.getDateInstance(DateFormat.FULL).format(evento.getFecha().getTime()));
        TextView hora = (TextView)v.findViewById(R.id.hora);
        hora.setText(evento.getHoraInicio() + " - "+ evento.getHoraFin());
        TextView ubicacion = (TextView)v.findViewById(R.id.ubicacion);
        ubicacion.setText(evento.getUbicacion());
        TextView detalles = (TextView)v.findViewById(R.id.descripcionDetalles);
        detalles.setText(evento.getDetalles());

        ImageView imagenEvento = v.findViewById(R.id.imgEvento);
        // Agregamos a imagen por medio de un URL
        Glide.with(v)
                .load(evento.getUrlImagen())
                // Vemos si podemos utilizar o no la imagen del cache
                .signature(new ObjectKey(evento.getImagenUltimaModificacion()))
                .into(imagenEvento);
    }

    //Cuando el usuario le da click al botón, añade el evento a la lista de favoritos del usuario
    public void DarMeGusta(){
        String eventoId = evento.getNombre().replaceAll(" ", "");

        // FIRESTORE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Guardamos el eventos en la lista de favoritos
        db.collection("meInteresaUsuarioEvento")
                .document(usuarioId)
                .collection("eventos")
                .document(eventoId)
                .set(evento);

        db.collection("usuarioEventosCreado")
                .document(evento.getOrganizador())
                .collection("eventos")
                .document(eventoId)
                .update("usuariosInteresados", FieldValue.arrayUnion(usuarioId));

        btnNoMeInteresa.setVisibility(View.VISIBLE);
        btnMeInteresa.setVisibility(View.GONE);
        eliminarDeFavoritos = false;
    }

    //Cuando el usuario le da click al botón, elimina el evento a la lista de favoritos del usuario
    public void QuitarMeGusta(){
        String eventoId = evento.getNombre().replaceAll(" ", "");

        // FIRESTORE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Eliminamos el evento de la lista de favoritos
        db.collection("meInteresaUsuarioEvento")
                .document(usuarioId)
                .collection("eventos")
                .document(eventoId)
                .delete();
        // Eliminamos el usuario de la lista de me interesa
        db.collection("usuarioEventosCreado")
                .document(evento.getOrganizador())
                .collection("eventos")
                .document(eventoId)
                .update("usuariosInteresados", FieldValue.arrayRemove(usuarioId));

        btnMeInteresa.setVisibility(View.VISIBLE);
        btnNoMeInteresa.setVisibility(View.GONE);
        eliminarDeFavoritos = true;
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

    public String getTagEliminar() {
        String tag = null;
        if(eliminarDeFavoritos == true){
            tag = Constantes.EVENTO_FAV_TAG + evento.getNombre();
        }
        return tag;
    }
}
