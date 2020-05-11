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

import cobit19.ecci.ucr.ac.eventosucr.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.CustomGridAdapterCategorias;
import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriasBuscarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasBuscarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Categoria> categorias;
    View v;
    GridView grid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriasBuscarFragment() {
        // Required empty public constructor
    }

    public CategoriasBuscarFragment(ArrayList<Categoria> categorias) {
        this.categorias=categorias;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriasBuscarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriasBuscarFragment newInstance(String param1, String param2) {
        CategoriasBuscarFragment fragment = new CategoriasBuscarFragment();
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
        v=inflater.inflate(R.layout.fragment_categorias_buscar, container, false);
        llenarLista();
        //Llenar lista de categorias
        return v;
    }

    private void llenarLista() {
        CustomGridAdapterCategorias adapter = new CustomGridAdapterCategorias(getActivity(), categorias);
        grid = (GridView) v.findViewById(R.id.gridCategorias);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Categoria categoriaSeleccionada = categorias.get(position);
                //Irse a otra pantalla con los extras desde esta para no hacer otra llamada a la base en la siguiente actividad
                cambiarDePantalla(categoriaSeleccionada);


            }
        });

    }

    private void cambiarDePantalla(Categoria categoriaSeleccionada) {
        //Hace algo con la categoria seleccionada(eg: cargar los eventos de esa categoria)
    }
}
