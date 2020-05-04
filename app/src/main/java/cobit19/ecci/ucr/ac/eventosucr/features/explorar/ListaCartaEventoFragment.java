package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaCartaEventoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaCartaEventoFragment extends Fragment {

    private static final String ARG_CATEGORIA = "categoria";

    private Categoria categoria;
    private int layoutId;
    private View view;

    public ListaCartaEventoFragment() {
        // Required empty public constructor
    }

    public ListaCartaEventoFragment(Categoria categoria) {
        this.categoria = categoria;
        layoutId = 1000 + Integer.parseInt(categoria.getId());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoria Parameter 1.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaCartaEventoFragment newInstance(Categoria categoria) {
        ListaCartaEventoFragment fragment = new ListaCartaEventoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORIA, categoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoria = getArguments().getParcelable(ARG_CATEGORIA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_carta_evento, container, false);
        TextView nombre = view.findViewById(R.id.explorar_nombre_categoria_eventos);
        nombre.setText(categoria.getNombre());

        if (view.findViewById(R.id.explorar_categoria_lista_eventos) != null) {
            if (savedInstanceState == null) {
                HorizontalScrollView horizontalScrollView = view.findViewById(R.id.explorar_categoria_lista_eventos);
                LinearLayout linearLayout =  new LinearLayout(getContext());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setId(layoutId);
                horizontalScrollView.addView(linearLayout);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventoService eventoService = new EventoService();
        ArrayList<Evento> eventos = eventoService.leerListaEventosPorCategoria(getContext(), categoria.getId());

        for (Evento evento: eventos) {
            getFragmentManager().beginTransaction()
                    .add(layoutId, new CartaEventoFragment(evento)).commit();
        }
    }
}
