package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.fragments.shared.ListaEventosFragment;

public class BuscarFragment extends Fragment {

    ArrayList<Evento> eventos;
    ArrayList<Categoria> categorias;

    public BuscarFragment() {}

    public static BuscarFragment newInstance(String param1, String param2) {
        BuscarFragment fragment = new BuscarFragment();
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
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        /*Evento evento=new Evento();
        eventos=evento.leerEventos(getActivity().getApplicationContext());
        ListaEventosFragment listaEventosFragment = new ListaEventosFragment(eventos);//Lista de eventos como parametro
        getFragmentManager().beginTransaction()
                .add(R.id.buscarFragmentVistaLista, listaEventosFragment).commit();

        CategoriaService categoriaService=new CategoriaService();
        categorias=categoriaService.leerLista(getActivity().getApplicationContext());
        CategoriasBuscarFragment categoriasBuscarFragment=new CategoriasBuscarFragment(categorias);
        getFragmentManager().beginTransaction()
                .add(R.id.categoriasBuscar, categoriasBuscarFragment).commit();*/


        return view;
    }
}
