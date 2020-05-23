package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.CustomGridAdapterCategorias;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.fragments.shared.ListaEventosFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriasBuscarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasBuscarFragment extends Fragment {

    ArrayList<Categoria> categorias;
    View v;
    GridView grid;

    public CategoriasBuscarFragment() {}

    public CategoriasBuscarFragment(ArrayList<Categoria> categorias) {
        this.categorias=categorias;
    }

    public static CategoriasBuscarFragment newInstance(String param1, String param2) {
        CategoriasBuscarFragment fragment = new CategoriasBuscarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_categorias_buscar, container, false);
        llenarLista();
        //Llenar lista de categorias
        return v;
    }

    private void llenarLista() {
        final CustomGridAdapterCategorias adapter = new CustomGridAdapterCategorias(getActivity(), categorias);
        grid = (GridView) v.findViewById(R.id.gridCategorias);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Categoria categoriaSeleccionada = categorias.get(position);

                //Irse a otra pantalla con los extras desde esta para no hacer otra llamada a la base en la siguiente actividad
                //adapter.getFilter().filter(categoriaSeleccionada.getNombre());
                cambiarDePantalla(categoriaSeleccionada);


            }
        });

    }

    private void cambiarDePantalla(Categoria categoriaSeleccionada) {
        /*EventoService eventoService =new EventoService();
        ArrayList<Evento>eventosCategoria=new ArrayList<Evento>();
        eventosCategoria=eventoService.leerListaEventosPorCategoria(getActivity().getApplicationContext(),categoriaSeleccionada.getId());
        ListaEventosFragment listaEventosFragment = new ListaEventosFragment(eventosCategoria);//Lista de eventos como parametro
        getFragmentManager().beginTransaction()
                .replace(R.id.buscarFragmentVistaLista, listaEventosFragment).commit();*/
        //Filtrar lista por categoria seleccionada
        //Hace algo con la categoria seleccionada(eg: cargar los eventos de esa categoria)
    }
}
