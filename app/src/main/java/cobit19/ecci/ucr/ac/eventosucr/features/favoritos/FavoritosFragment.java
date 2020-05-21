package cobit19.ecci.ucr.ac.eventosucr.features.favoritos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.UsuarioEventoService;

public class FavoritosFragment extends Fragment {

    private View view;
    private ArrayList<Evento> listaEventos = new ArrayList<>();

    public FavoritosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        LinearLayout listaLayout = view.findViewById(R.id.favoritos_lista);

        UsuarioEventoService usuarioEventoService = new UsuarioEventoService();

        listaEventos = usuarioEventoService.listaEventosPorUsuario(getContext(), Constantes.CORREO_UCR_USUARIO);

        for (Evento evento: listaEventos){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.favoritos_lista, new CartaEventoFavoritos(evento), Constantes.EVENTO_FAV_TAG+evento.getId())
                    .commit();
        }

        return view;
    }

    public void eliminarDeLista(String tag){
        Fragment fragmentToRemove = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
        if(fragmentToRemove != null){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragmentToRemove)
                    .commit();
        }
    }
}
