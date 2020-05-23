package cobit19.ecci.ucr.ac.eventosucr.fragments.shared;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.CustomListAdapter;

import cobit19.ecci.ucr.ac.eventosucr.ListaEventosSuperUsuario;
import cobit19.ecci.ucr.ac.eventosucr.ModificarEliminarEvento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Imagen;

import static cobit19.ecci.ucr.ac.eventosucr.ListaEventosSuperUsuario.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaEventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaEventosFragment extends Fragment {

    private ArrayList<Evento> eventos;
    ArrayList<ImageView> imagenesdeEventos=new ArrayList<ImageView>();
    private View v;
    ListView list;
    CustomListAdapter adapter1;


    public ListaEventosFragment() {
        // Required empty public constructor
    }
    public ListaEventosFragment(ArrayList<Evento> eventos,ArrayList<ImageView> imagenesdeEventos) {
        this.eventos=eventos;
        this.imagenesdeEventos=imagenesdeEventos;

    }

    public static ListaEventosFragment newInstance(String param1, String param2) {
        ListaEventosFragment fragment = new ListaEventosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), eventos,imagenesdeEventos);
        String nombreActividadPadre="";
        if(isAdded()) {
            nombreActividadPadre=getActivity().getClass().getSimpleName();

        }
        list = (ListView) v.findViewById(R.id.list);
        adapter1=adapter;
        list.setAdapter(adapter);
        final String finalNombreActividadPadre = nombreActividadPadre;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Evento eventoSeleccionado = eventos.get(position);

                //Irse a otra pantalla con los extras desde esta para no hacer otra llamada a la base en la siguiente actividad
                if(finalNombreActividadPadre.equals("ListaEventosSuperUsuario")) {
                    cambiarDePantalla(eventoSeleccionado);
                }else{
                    //Irse a vista de un evento con fragment
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


}
