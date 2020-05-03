package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cobit19.ecci.ucr.ac.eventosucr.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    private static final String ARG_EVENTO = "evento";
    private Evento evento;
    private OnListFragmentInteractionListener listener;

    public BlankFragment() {
        // Required empty public constructor
    }

    public BlankFragment(Evento evento) {
        this.evento = evento;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param evento Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(Evento evento) {
        BlankFragment fragment = new BlankFragment();
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
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView fecha = view.findViewById(R.id.explorar_evento_carta_fecha1);
        TextView nombre = view.findViewById(R.id.explorar_evento_carta_nombre1);
        TextView institucion = view.findViewById(R.id.explorar_evento_carta_institucion1);

        fecha.setText(UtilDates.obtenerFechaParaExplorarEventoCarta(evento.getFecha()));
        nombre.setText(evento.getNombre());
        institucion.setText(evento.getInstitucion());

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
