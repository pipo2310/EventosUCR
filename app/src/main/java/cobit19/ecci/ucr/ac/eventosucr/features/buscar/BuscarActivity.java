package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.MenuActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.shared.ListaEventosFragment;
import cobit19.ecci.ucr.ac.eventosucr.shared.SinResultadosFragment;

public class BuscarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        ListaEventosFragment.OnEventoSeleccionadoInteractionListener {

    public static final String EVENTO = "evento";
    private ArrayList<Evento> eventos = new ArrayList<Evento>();
    private ArrayList<Evento> eventosFiltrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        SearchView searchView = (SearchView) findViewById(R.id.buscar_barra);
        searchView.setOnQueryTextListener(this);

        // FIRESTORE
        // Crear la referencia a firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Esta no va a ser la forma final de hacerlo pero por ahora que hay pocos datos es aceptable
        db.collection("eventos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    eventos.add(document.toObject(Evento.class));
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        eventosFiltrados = new ArrayList<Evento>();
        for (Evento evento: eventos) {
            if (evento.getNombre().contains(query)) {
                eventosFiltrados.add(evento);
            }
        }


        if (eventosFiltrados.size() > 0) {
            ListaEventosFragment listaEventosFragment = new ListaEventosFragment(eventosFiltrados);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.actividad_buscar_resultado_busqueda, listaEventosFragment)
                    .commit();
        } else {
            SinResultadosFragment sinResultadosFragment = new SinResultadosFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.actividad_buscar_resultado_busqueda, sinResultadosFragment)
                    .commit();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onEventoSelecciondo(Evento evento) {
        Intent a =new Intent(this, MenuActivity.class);

        a.putExtra(EVENTO, evento);

        startActivity(a);
    }
}
