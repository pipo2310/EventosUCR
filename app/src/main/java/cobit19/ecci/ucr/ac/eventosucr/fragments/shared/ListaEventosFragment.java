package cobit19.ecci.ucr.ac.eventosucr.fragments.shared;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.CustomListAdapter;
import cobit19.ecci.ucr.ac.eventosucr.Evento;
import cobit19.ecci.ucr.ac.eventosucr.ModificarEliminarEvento;
import cobit19.ecci.ucr.ac.eventosucr.R;

import static cobit19.ecci.ucr.ac.eventosucr.ListaEventosSuperUsuario.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaEventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaEventosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Evento> eventos;
    private View v;
    ListView list;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaEventosFragment() {
        // Required empty public constructor
    }
    public ListaEventosFragment(ArrayList<Evento> eventos) {
        this.eventos=eventos;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaEventosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaEventosFragment newInstance(String param1, String param2) {
        ListaEventosFragment fragment = new ListaEventosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_lista_eventos, container, false);
        llenarLista();
        // Inflate the layout for this fragment
        return v;
    }

    private void llenarLista() {
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), eventos);
        list = (ListView) v.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Evento eventoSeleccionado = eventos.get(position);
                //Irse a otra pantalla con los extras desde esta para no hacer otra llamada a la base en la siguiente actividad
                cambiarDePantalla(eventoSeleccionado);


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


}
