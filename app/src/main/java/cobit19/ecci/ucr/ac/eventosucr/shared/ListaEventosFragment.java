package cobit19.ecci.ucr.ac.eventosucr.shared;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.features.administracionEventosUsuario.ModificarEliminarEvento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

import static cobit19.ecci.ucr.ac.eventosucr.features.administracionEventosUsuario.ListaEventosUsuario.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaEventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaEventosFragment extends Fragment {

    private OnEventoSeleccionadoInteractionListener listener;

    private ArrayList<Evento> eventos;
    private View v;
    ListView list;


    public ListaEventosFragment() {
        eventos=new ArrayList<Evento>();
    }
    public ListaEventosFragment(ArrayList<Evento> eventos) {
        this.eventos=eventos;
    }

    public static ListaEventosFragment newInstance() {
        ListaEventosFragment fragment = new ListaEventosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_lista_eventos, container, false);

        llenarLista();
        return v;
    }

    private void llenarLista() {

        CustomListAdapter adapter = new CustomListAdapter(getActivity(), eventos);
        String nombreActividadPadre="";
        if(isAdded()) {
            nombreActividadPadre=getActivity().getClass().getSimpleName();

        }
        list = (ListView) v.findViewById(R.id.list);

        list.setAdapter(adapter);
        final String finalNombreActividadPadre = nombreActividadPadre;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Evento eventoSeleccionado = eventos.get(position);

                //Irse a otra pantalla con los extras desde esta para no hacer otra llamada a la base en la siguiente actividad
                if(finalNombreActividadPadre.equals("ListaEventosUsuario")) {
                    cambiarDePantalla(eventoSeleccionado);
                }else {
                    listener.onEventoSelecciondo(eventoSeleccionado);
                }

            }
        });
    }

    public void cambiarDePantalla(Evento evento) {
        Intent intent = new Intent(getActivity(), ModificarEliminarEvento.class);
        intent.putExtra(EXTRA_MESSAGE, evento);

        // Deseo recibir una respuesta: startActivityForResult()
        startActivityForResult(intent, 0);
        requireActivity().finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventoSeleccionadoInteractionListener) {
            listener = (OnEventoSeleccionadoInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventoSeleccionadoInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnEventoSeleccionadoInteractionListener {
        void onEventoSelecciondo(Evento evento);
    }
}
