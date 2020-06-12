package cobit19.ecci.ucr.ac.eventosucr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.BuscarActivity;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.BuscarFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.CategoriasBuscarFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.CartaEventoFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.ExplorarFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.CartaEventoFavoritos;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;
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
                    /*SearchView searchView = (SearchView) findViewById(R.id.buscar_barra);*/
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
        if(menuItem.getItemId() == R.id.nav_cud_eventos){
            Intent a =new Intent(this, ListaEventosSuperUsuario.class);
            startActivity(a);
            // finalizamos la aplicacion para que NO quede en segundo plano
            finish();
        }
        else{
            drawer.closeDrawer(Gravity.LEFT, true);
        }
        return true;
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
    public void onBackPressed(){
        if (currentFragmentTag == Constantes.VISTA_EVENTO_TAG) {
            Fragment olderFragment = getSupportFragmentManager().findFragmentByTag(oldFragmentTag);
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(currentFragmentTag);
            currentFragmentTag = oldFragmentTag;
            if(oldFragmentTag == Constantes.FAVORITOS_TAG){
                VistaEventoFragment vistaEventoFragment = (VistaEventoFragment) currentFragment;
                FavoritosFragment favoritosFragment = (FavoritosFragment) olderFragment;
                String tag = vistaEventoFragment.getTagEliminar();
                if(tag != null){
                    favoritosFragment.eliminarDeLista(tag);
                }
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(currentFragment)
                    .show(olderFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            currentFragmentTag = oldFragmentTag;
            super.onBackPressed();
        }
    }

    public void onListFragmentInteraction(Evento evento){
        showItemSelectedFragment(new VistaEventoFragment(evento), Constantes.VISTA_EVENTO_TAG);
    }

    @Override
    public void onCategoriaSeleccionada(Categoria categoria) {
        EventoService eventoService = new EventoService();
        ArrayList<Evento> eventos = eventoService.leerListaEventosPorCategoria(getApplicationContext(), categoria.getId());

        ImagenService imagenService=new ImagenService();

        Bitmap imagenNula = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.ucr_evento_img);
        ImageView imagenNulaImageView = new ImageView(this);
        imagenNulaImageView.setImageBitmap(imagenNula);
        ArrayList<ImageView> imagenesdeEventos = new ArrayList<ImageView>();

        for (Evento evento : eventos){
            if(imagenService.leerImagenEvento(getApplicationContext(),evento.getId()).size()==0){
                //Imagen imagen=new Imagen(evento.getId(),imagenNula);
                imagenesdeEventos.add(imagenNulaImageView);
            }else{
                ImageView imagenExistente=new ImageView(this);
                imagenExistente.setImageBitmap(imagenService.leerImagenEvento(getApplicationContext(),evento.getId()).get(0).getImagen());
                imagenesdeEventos.add(imagenExistente);
            }
        }

        showSelectedFragment(new ListaEventosFragment(eventos, imagenesdeEventos), Constantes.LISTA_EVENTOS_TAG);
    }

    @Override
    public void onEventoSelecciondo(Evento evento) {
        showItemSelectedFragment(new VistaEventoFragment(evento), Constantes.VISTA_EVENTO_TAG);
    }
}
