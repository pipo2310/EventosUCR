package cobit19.ecci.ucr.ac.eventosucr.features.favoritos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.UsuarioEventoService;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.FavoritosFragment;

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

        fecha.setText(UtilDates.obtenerFechaParaExplorarEventoCarta(evento.getFecha()));
        nombre.setText(evento.getNombre());
        institucion.setText("Institución: " + evento.getInstitucion(getContext()).getNombre());

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
        UsuarioEventoService usuarioEventoService = new UsuarioEventoService();
        usuarioEventoService.eliminar(getContext(), UtilDates.CORREO_UCR_USUARIO, evento.getId());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new FavoritosFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFavoritosItemListener) {
            listener = (OnFavoritosItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
