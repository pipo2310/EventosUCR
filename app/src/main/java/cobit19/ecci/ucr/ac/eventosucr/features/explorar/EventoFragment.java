package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.Evento;
import cobit19.ecci.ucr.ac.eventosucr.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EventoFragment extends Fragment {

    private static final String ARG_CATEGORIA = "categoria";

    private EventoService eventoService;
    private Categoria mCategoria;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventoFragment() {
        eventoService = new EventoService();
    }

    public EventoFragment(Categoria categoria) {
        eventoService = new EventoService();
        mCategoria = categoria;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventoFragment newInstance(Categoria categoria) {
        EventoFragment fragment = new EventoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORIA, categoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategoria = getArguments().getParcelable(ARG_CATEGORIA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evento_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            ArrayList<Evento> eventos = eventoService.leerListaEventosPorCategoria(context, mCategoria.getId());
            recyclerView.setAdapter(new MyEventoRecyclerViewAdapter(eventos, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Evento evento);
    }
}
