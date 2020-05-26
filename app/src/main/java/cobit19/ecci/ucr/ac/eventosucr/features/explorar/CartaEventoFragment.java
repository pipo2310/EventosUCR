package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Imagen;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartaEventoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartaEventoFragment extends Fragment {

    private static final String ARG_EVENTO = "evento";
    private Evento evento;
    private OnListFragmentInteractionListener listener;

    public CartaEventoFragment() {
        // Required empty public constructor
    }

    public CartaEventoFragment(Evento evento) {
        this.evento = evento;
    }

    public static CartaEventoFragment newInstance(Evento evento) {
        CartaEventoFragment fragment = new CartaEventoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EVENTO, evento);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            evento = getArguments().getParcelable(ARG_EVENTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carta_evento, container, false);
        TextView fecha = view.findViewById(R.id.explorar_evento_carta_fecha1);
        TextView nombre = view.findViewById(R.id.explorar_evento_carta_nombre1);
        TextView institucion = view.findViewById(R.id.explorar_evento_carta_institucion1);

        fecha.setText(UtilDates.obtenerFechaParaExplorarEventoCarta(evento.getFecha()));
        nombre.setText("Evento: " + evento.getNombre());
        institucion.setText("Institución: " + evento.getInstitucion(getContext()).getNombre());

        ImageView imagenEvento = view.findViewById(R.id.explorar_evento_carta_imagen1);
        ImagenService imagenService = new ImagenService();
        ArrayList<Imagen> imagenes = imagenService.leerImagenEvento(getContext(),evento.getId());
        if(imagenes.size()>0){ imagenEvento.setImageBitmap(imagenes.get(0).getImagen());}

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (null != listener) {
                listener.onListFragmentInteraction(evento);
            }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
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

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Evento evento);
    }
}
