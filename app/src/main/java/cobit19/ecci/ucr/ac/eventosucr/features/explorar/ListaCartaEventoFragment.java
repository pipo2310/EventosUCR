package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.room.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.shared.Util;

public class ListaCartaEventoFragment extends Fragment {

    private static final String ARG_CATEGORIA = "categoria";

    private boolean displayable;
    private Categoria categoria;
    private List<Evento> eventos;
    private View view;

    public ListaCartaEventoFragment() {
        displayable = false;
    }

    public ListaCartaEventoFragment(Categoria categoria, List<Evento> eventos) {
        displayable = false;
        this.categoria = categoria;
        this.eventos = eventos;
    }

    public boolean isDisplayable() {
        return displayable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoria = getArguments().getParcelable(ARG_CATEGORIA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista_carta_evento, container, false);
        TextView nombre = view.findViewById(R.id.explorar_nombre_categoria_eventos);

        // Hacer que la primera letra sea en mayuscula
        String categoriasMostrar = categoria.getCategoria();
        categoriasMostrar = categoriasMostrar.substring(0, 1).toUpperCase() + categoriasMostrar.substring(1);
        nombre.setText(categoriasMostrar);

        // Colocar el icono correcto en la lista
        ImageView contenedorIcono = view.findViewById(R.id.explorar_icono_categoria);
        contenedorIcono.setImageResource(Util.idCategoria(categoria.getCategoria()));

        // Pedimos el fragment transaction de este fragmento
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Agregamos los eventos a la vista dek fragmento
        for (Evento evento: eventos) {
            transaction.add(R.id.categorias_layout, new CartaEventoFragment(evento));
        }

        transaction.commit();

        return view;
    }
}
