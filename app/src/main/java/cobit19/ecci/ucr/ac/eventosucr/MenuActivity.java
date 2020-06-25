package cobit19.ecci.ucr.ac.eventosucr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.BuscarActivity;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.BuscarFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.CategoriasBuscarFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.CartaEventoFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.ExplorarFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.CartaEventoFavoritos;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.administracionEventosUsuario.ListaEventosUsuario;
import cobit19.ecci.ucr.ac.eventosucr.features.login.LoginActivity;
import cobit19.ecci.ucr.ac.eventosucr.features.vistaEvento.VistaEventoFragment;
import cobit19.ecci.ucr.ac.eventosucr.room.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.shared.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.shared.ListaEventosFragment;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        CartaEventoFragment.OnListFragmentInteractionListener,
        CartaEventoFavoritos.OnFavoritosItemListener,
        CategoriasBuscarFragment.OnCategoriaSeleccionadaInteractionListener,
        ListaEventosFragment.OnEventoSeleccionadoInteractionListener {

    public static final String ACTIVIDAD = "actvidad";

    BottomNavigationView footerMenu;
    DrawerLayout drawer;
    String currentFragmentTag = null;
    String oldFragmentTag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // nav bar de arriba
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Para el menu lateral
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Para el menu de abajo
        footerMenu = (BottomNavigationView) findViewById(R.id.menu_footer);

        //Fragmento que se muestra al inicio
        Intent a =  getIntent();
        Evento evento = (Evento) a.getParcelableExtra(BuscarActivity.EVENTO);
        if (evento != null) {
            showSelectedFragment(new VistaEventoFragment(evento), Constantes.VISTA_EVENTO_TAG);
        } else {
            String type = getIntent().getStringExtra("From");
            if (type != null) {
                switch (type) {
                    case "notifFragVista":
                        showSelectedFragment(new ExplorarFragment(), Constantes.EXPLORAR_TAG);
                        // Marcado por defecto el explorar
                        footerMenu.setSelectedItemId(R.id.menu_explorar);
                        break;
                    case "notifFragFavoritos":
                        showSelectedFragment(new FavoritosFragment(), Constantes.FAVORITOS_TAG);
                        break;
                }
            }else {
                showSelectedFragment(new ExplorarFragment(), Constantes.EXPLORAR_TAG);
                // Marcado por defecto el explorar
                footerMenu.setSelectedItemId(R.id.menu_explorar);
            }
        }

        // Listener de la opciones del menú
        footerMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_favoritos){
                    showSelectedFragment(new FavoritosFragment(), Constantes.FAVORITOS_TAG);
                }
                if(menuItem.getItemId() == R.id.menu_explorar){
                    showSelectedFragment(new ExplorarFragment(), Constantes.EXPLORAR_TAG);
                }
                if(menuItem.getItemId() == R.id.menu_buscar){
                    showSelectedFragment(new BuscarFragment(), Constantes.BUSCAR_TAG);
                }
                return true;
            }
        });
    }


    /**
     * Metodo para el menu lateral aqui se pone donde se va la aplicacion cada vez que se toca un item
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_cud_eventos:
                cambiarDePantalla(ListaEventosUsuario.class);
                break;
            case R.id.nav_cerrar_sesion:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                cambiarDePantalla(LoginActivity.class);
                break;
            default:
                drawer.closeDrawer(Gravity.LEFT, true);
                break;
        }
        return true;
    }

    public void cambiarDePantalla(Class<?> activity) {
        Intent a =new Intent(this, activity);
        startActivity(a);
        finish();
    }

    /**
     * Metodo que se usa para indicar cual es el feagment que se va a ver
     * @param fragment
     */
    private void showSelectedFragment(Fragment fragment, String tag){
        oldFragmentTag = currentFragmentTag;
        currentFragmentTag = tag;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    /**
     * Metodo para pasar a un fragment pero que el fragment anterior quede en cola para que se pueda regresar a el
     * @param fragment
     */
    private void showItemSelectedFragment(Fragment fragment, String tag){
        oldFragmentTag = currentFragmentTag;
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(currentFragmentTag);
        currentFragmentTag = tag;
        getSupportFragmentManager()
                .beginTransaction()
                .hide(currentFragment)
                .add(R.id.container_fragment, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void OnFavoritosItemListener(Evento evento){
        showItemSelectedFragment(new VistaEventoFragment(evento), Constantes.VISTA_EVENTO_TAG);
    }

    @Override
    public void onBackPressed() {
        // Preguntamos si la vista en la que estamos es el explorar
        if (currentFragmentTag == Constantes.EXPLORAR_TAG) {
            super.onBackPressed();
        } else if (currentFragmentTag == Constantes.LISTA_EVENTOS_TAG) {
            // Cargamos el fragmento buscar
            showSelectedFragment(new BuscarFragment(), Constantes.BUSCAR_TAG);
        } else if (currentFragmentTag == Constantes.VISTA_EVENTO_TAG) {
            Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(oldFragmentTag);
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(currentFragmentTag);

            // Preguntamos si se llego a la vista del evento atraves de favoritos
            if (oldFragmentTag == Constantes.FAVORITOS_TAG) {
                // Regresamos al punto exacto en donde quedo en el favoritos
                FavoritosFragment favoritosFragment = (FavoritosFragment) oldFragment;
                VistaEventoFragment vistaEventoFragment = (VistaEventoFragment) currentFragment;

                // Preguntamos si hay que eliminar el tag de favoritos
                String tag = vistaEventoFragment.getTagEliminar();
                if(tag != null){
                    favoritosFragment.eliminarDeLista(tag);
                }

                onBackFragment(oldFragment, currentFragment, Constantes.FAVORITOS_TAG);

            } else if (oldFragmentTag == Constantes.EXPLORAR_TAG) {
                onBackFragment(oldFragment, currentFragment, Constantes.EXPLORAR_TAG);

            } else if (oldFragmentTag == Constantes.LISTA_EVENTOS_TAG) {
                onBackFragment(oldFragment, currentFragment, Constantes.LISTA_EVENTOS_TAG);
            } else {
                showSelectedFragment(new ExplorarFragment(), Constantes.EXPLORAR_TAG);
                // Marcado por defecto el explorar
                footerMenu.setSelectedItemId(R.id.menu_explorar);
            }
        } else {
            showSelectedFragment(new ExplorarFragment(), Constantes.EXPLORAR_TAG);
            // Marcado por defecto el explorar
            footerMenu.setSelectedItemId(R.id.menu_explorar);
        }
    }

    public void onBackFragment(Fragment oldFragment, Fragment currentFragment, String tag) {
        currentFragmentTag = tag;
        getSupportFragmentManager()
                .beginTransaction()
                .remove(currentFragment)
                .show(oldFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void onListFragmentInteraction(Evento evento){
        showItemSelectedFragment(new VistaEventoFragment(evento), Constantes.VISTA_EVENTO_TAG);
    }

    @Override
    public void onCategoriaSeleccionada(Categoria categoria) {
        ArrayList<Evento> eventos = new ArrayList<>();
        // FIRESTORE
        // Crear la referencia a firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Pedimos la lista de eventos de una categoria
        db.collection("categoriaEventos")
                .document(categoria.getCategoria())
                .collection("eventos")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Creamos la lista de eventos de firebase
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                eventos.add(document.toObject(Evento.class));
                            }
                            showSelectedFragment(new ListaEventosFragment(eventos), Constantes.LISTA_EVENTOS_TAG);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onEventoSelecciondo(Evento evento) {
        showItemSelectedFragment(new VistaEventoFragment(evento), Constantes.VISTA_EVENTO_TAG);
    }
}
