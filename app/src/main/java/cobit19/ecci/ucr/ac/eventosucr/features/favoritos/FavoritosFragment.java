package cobit19.ecci.ucr.ac.eventosucr.features.favoritos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.shared.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;


public class FavoritosFragment extends Fragment {

    private View view;
    private ArrayList<Evento> listaEventos = new ArrayList<>();

    public FavoritosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        LinearLayout listaLayout = view.findViewById(R.id.favoritos_lista);

        // AUTH
        // Obtenemos el usuario
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String usuarioId = user.getEmail().replaceAll("@(.)*", "");

        //FIREBASE
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meInteresaUsuarioEvento")
                .document(usuarioId)
                .collection("eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Evento evento;
                            String eventoId;
                            // Creamos la lista de eventos de firebase
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                evento = document.toObject(Evento.class);
                                eventoId = evento.getNombre().replaceAll(" ", "");

                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.favoritos_lista, new CartaEventoFavoritos(evento), Constantes.EVENTO_FAV_TAG+eventoId)
                                        .commit();
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return view;
    }

    public void eliminarDeLista(String tag){
        Fragment fragmentToRemove = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
        if(fragmentToRemove != null){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragmentToRemove)
                    .commit();
        }
    }
}
