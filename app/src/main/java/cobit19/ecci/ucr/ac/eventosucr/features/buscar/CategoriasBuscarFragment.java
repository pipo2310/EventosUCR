package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.room.Categoria;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriasBuscarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasBuscarFragment extends Fragment {

    private OnCategoriaSeleccionadaInteractionListener listener;
    List<Categoria> categorias;
    View view;

    public CategoriasBuscarFragment() {}

    public CategoriasBuscarFragment(List<Categoria> categorias) {
        this.categorias=categorias;
    }

    public static CategoriasBuscarFragment newInstance() {
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
        view = inflater.inflate(R.layout.fragment_categorias_buscar, container, false);
        llenarListaDeCategorias();
        return view;
    }

    private void llenarListaDeCategorias() {
        final CustomGridAdapterCategorias adapter = new CustomGridAdapterCategorias(getActivity(), categorias);
        GridView grid = (GridView) view.findViewById(R.id.gridCategorias);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoriaSeleccionada = categorias.get(position);

                listener.onCategoriaSeleccionada(categoriaSeleccionada);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoriaSeleccionadaInteractionListener) {
            listener = (OnCategoriaSeleccionadaInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategoriaSeleccionadaInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnCategoriaSeleccionadaInteractionListener {
        void onCategoriaSeleccionada(Categoria categoria);
    }
}
