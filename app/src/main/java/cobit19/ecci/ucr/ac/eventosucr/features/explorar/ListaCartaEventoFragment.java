package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.room.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.shared.Util;

public class ListaCartaEventoFragment extends Fragment {

    private static final String ARG_CATEGORIA = "categoria";

    private Categoria categoria;
    private int layoutId;
    private View view;

    public ListaCartaEventoFragment() {
        // Required empty public constructor
    }

    public ListaCartaEventoFragment(Categoria categoria, int id) {
        this.categoria = categoria;
        layoutId = 1000 + id;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoria Parameter 1.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaCartaEventoFragment newInstance(Categoria categoria) {
        ListaCartaEventoFragment fragment = new ListaCartaEventoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORIA, categoria);
        fragment.setArguments(args);
        return fragment;
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

        if (view.findViewById(R.id.explorar_categoria_lista_eventos) != null) {
            if (savedInstanceState == null) {
                HorizontalScrollView horizontalScrollView = view.findViewById(R.id.explorar_categoria_lista_eventos);
                LinearLayout linearLayout =  new LinearLayout(getContext());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setId(layoutId);
                horizontalScrollView.addView(linearLayout);

                // FIRESTORE
                // Crear la referencia a firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("categoriaEventos")
                        .document(categoria.getCategoria())
                        .collection("eventos")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Evento evento;
                                    // Creamos la lista de eventos de firebase
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        evento = document.toObject(Evento.class);

                                        getFragmentManager().beginTransaction()
                                                .add(layoutId, new CartaEventoFragment(evento)).commit();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        }

        return view;
    }
}
