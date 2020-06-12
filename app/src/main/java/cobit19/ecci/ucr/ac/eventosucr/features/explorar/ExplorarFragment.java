package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.room.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.room.CategoriaViewModel;


public class ExplorarFragment extends Fragment {

    // ROOM
    // Crear la variable del model view de categoria
    private CategoriaViewModel categoriaViewModel;

    public ExplorarFragment() { }

    public static ExplorarFragment newInstance() {
        ExplorarFragment fragment = new ExplorarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Creamos el view del fragmento explorar
        View view = inflater.inflate(R.layout.fragment_explorar, container, false);

        if (view.findViewById(R.id.explorar_lista_categorias) != null) {
            if (savedInstanceState == null) {

                // Le pedimos al proovedor de view models que nos de el de categorias
                categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);

                categoriaViewModel.getAllCategorias().observe(getViewLifecycleOwner(), new Observer<List<Categoria>>() {
                    @Override
                    public void onChanged(@Nullable final List<Categoria> c) {
                        // Int utilizado como id
                        int id = 1;
                        // Update the cached copy of the words in the adapter.
                        for(Categoria categoria: c) {
                            ListaCartaEventoFragment listaCartaEventoFragment = new ListaCartaEventoFragment(categoria, id);
                            getFragmentManager().beginTransaction()
                                    .add(R.id.explorar_lista_categorias, listaCartaEventoFragment).commit();

                            ++id;
                        }
                    }
                });

            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
