package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cobit19.ecci.ucr.ac.eventosucr.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplorarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplorarFragment extends Fragment {

    public ExplorarFragment() {
    }


    public static ExplorarFragment newInstance(String param1, String param2) {
        ExplorarFragment fragment = new ExplorarFragment();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explorar, container, false);

        if (view.findViewById(R.id.explorar_lista_categorias) != null) {
            if (savedInstanceState == null) {
                CategoriaService categoriaService = new CategoriaService();

                for(Categoria categoria: categoriaService.leerListaDeCategoriasConEventos(getContext())) {
                    ListaCartaEventoFragment listaCartaEventoFragment = new ListaCartaEventoFragment(categoria);
                    getFragmentManager().beginTransaction()
                            .add(R.id.explorar_lista_categorias, listaCartaEventoFragment).commit();
                }
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
