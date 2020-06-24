package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
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

        // Le pedimos al proovedor de view models que nos de el de categorias
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);

        categoriaViewModel.getAllCategorias().observe(getViewLifecycleOwner(), new Observer<List<Categoria>>() {
            @Override
            public void onChanged(@Nullable final List<Categoria> c) {

                // Update the cached copy of the words in the adapter.
                for(Categoria categoria: c) {
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
                                        if (!task.getResult().isEmpty()) {
                                            // Pedimos el fragment transaction de este fragmento
                                            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                                            List<Evento> eventos = new ArrayList<Evento>();
                                            for (QueryDocumentSnapshot document: task.getResult()) {
                                                eventos.add(document.toObject(Evento.class));
                                            }

                                            // Creamos el fragmento que contiene los eventos de un categoria
                                            ListaCartaEventoFragment listaCartaEventoFragment = new ListaCartaEventoFragment(categoria, eventos);
                                            // Agregamos el fragmento
                                            transaction.add(R.id.explorar_lista_categorias, listaCartaEventoFragment);
                                            // Hacemos commit del fragmento
                                            transaction.commit();
                                        }
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}