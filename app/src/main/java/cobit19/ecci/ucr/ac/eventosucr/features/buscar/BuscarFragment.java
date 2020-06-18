package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.room.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.room.CategoriaViewModel;


public class BuscarFragment extends Fragment {

    // ROOM
    // Crear la variable del model view de categoria
    private CategoriaViewModel categoriaViewModel;

    public BuscarFragment() {}
    public BuscarFragment(String query) {
        System.out.println(query);
    }

    public static BuscarFragment newInstance(String param1, String param2) {
        return new BuscarFragment();
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

        // Aqui es donde vamos a meter los fragmentos a la vista
        Button button =  view.findViewById(R.id.buscar_boton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuscarActivity.class);
                startActivity(intent);
            }
        });

        // Le pedimos al proovedor de view models que nos de el de categorias
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);

        // Pedimos las categorias y dejamos un listener en caso de que estas cambien
        categoriaViewModel.getAllCategorias().observe(getViewLifecycleOwner(), new Observer<List<Categoria>>() {
            @Override
            public void onChanged(@Nullable final List<Categoria> c) {
                // Update the cached copy of the words in the adapter.
                CategoriasBuscarFragment categoriasBuscarFragment = new CategoriasBuscarFragment(c);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.categoriasBuscar, categoriasBuscarFragment).commit();
            }
        });

        return view;
    }
}
