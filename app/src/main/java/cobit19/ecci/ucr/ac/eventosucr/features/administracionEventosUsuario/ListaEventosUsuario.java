package cobit19.ecci.ucr.ac.eventosucr.features.administracionEventosUsuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.shared.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.MenuActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.shared.ListaEventosFragment;

public class ListaEventosUsuario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ListaEventosFragment.OnEventoSeleccionadoInteractionListener {

    ArrayList<Evento> eventosPruebaFireBase = new ArrayList<Evento>();
    public final static String EXTRA_MESSAGE="evento";
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_usuario);

        // nav bar de arriba
        Toolbar toolbar = findViewById(R.id.toolbar_LE);
        setSupportActionBar(toolbar);

        // Para el menu lateral
        drawer = findViewById(R.id.drawer_layout_LE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view_LE);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvento();
            }
        });

        leerEventos();
    }


    public void addEvento() {
        Intent intent = new Intent(this, CrearEvento.class);

        // Deseo recibir una respuesta: startActivityForResult()
        startActivity(intent);
        finish();
    }


    public void leerEventos() {
        String usuarioId = Constantes.CORREO_UCR_USUARIO.replaceAll("@(.)*", "");
        // FIRESTORE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Pedimos la lista de eventos del usuario
        db.collection("usuarioEventosCreado")
                .document(usuarioId)
                .collection("eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                             // Creamos la lista de eventos de firebase
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                eventosPruebaFireBase.add(document.toObject(Evento.class));
                            }

                            ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventosPruebaFireBase);
                            getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.nav_home){
            Intent a =new Intent(this, MenuActivity.class);
            startActivity(a);
            // finalizamos la aplicacion para que NO quede en segundo plano
            finish();
        }
        else{
            drawer.closeDrawer(Gravity.LEFT, true);
        }
        return true;
    }

    @Override
    public void onEventoSelecciondo(Evento evento) { }
}
