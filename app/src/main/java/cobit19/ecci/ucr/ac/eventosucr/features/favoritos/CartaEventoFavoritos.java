package cobit19.ecci.ucr.ac.eventosucr.features.favoritos;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.shared.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.shared.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

public class CartaEventoFavoritos extends Fragment {

    private Evento evento;
    private View view;
    private OnFavoritosItemListener listener;

    public CartaEventoFavoritos(){
    }

    public CartaEventoFavoritos(Evento evento){
        this.evento = evento;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.carta_evento_favoritos, container, false);

        TextView fecha = view.findViewById(R.id.favoritos_carta_fecha);
        TextView nombre = view.findViewById(R.id.favoritos_carta_nombre);
        TextView institucion = view.findViewById(R.id.favoritos_carta_Institucion);
        
        // Obtenemos el ImageView
        ImageView imagenEvento = view.findViewById(R.id.favoritos_carta_imagen);
        // Agregamos a imagen por medio de un URL
        Glide.with(this)
                .load(evento.getUrlImagen())
                // Vemos si podemos utilizar o no la imagen del cache
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imagenEvento);
            

        fecha.setText(UtilDates.obtenerFechaParaExplorarEventoCarta(evento.getFecha()));
        nombre.setText(evento.getNombre());
        institucion.setText("Organizador: " + evento.getOrganizador());

        FloatingActionButton btn_like = view.findViewById(R.id.favoritos_carta_btn_like);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuitarMeGusta();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.OnFavoritosItemListener(evento);
                }
            }
        });

        return view;
    }

    public void QuitarMeGusta(){
        String usuarioId = Constantes.CORREO_UCR_USUARIO.replaceAll("@(.)*", "");
        String eventoId = evento.getNombre().replaceAll(" ", "");
        
        //FIREBASE
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meInteresaUsuarioEvento")
                .document(usuarioId)
                .collection("eventos")
                .document(eventoId)
                .delete();

        db.collection("usuarioEventosCreado")
                .document(evento.getOrganizador())
                .collection("eventos")
                .document(eventoId)
                .update("usuariosInteresados", FieldValue.arrayRemove(usuarioId));

        String tag = Constantes.EVENTO_FAV_TAG+eventoId;
        Fragment fragmentToRemove = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .remove(fragmentToRemove)
                .commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFavoritosItemListener) {
            listener = (OnFavoritosItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFavoritosItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFavoritosItemListener {
        void OnFavoritosItemListener(Evento evento);
    }
}
