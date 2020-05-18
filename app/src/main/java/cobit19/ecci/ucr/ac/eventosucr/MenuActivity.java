package cobit19.ecci.ucr.ac.eventosucr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.CartaEventoFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.ExplorarFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.CartaEventoFavoritos;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CartaEventoFragment.OnListFragmentInteractionListener, CartaEventoFavoritos.OnFavoritosItemListener {

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

        //Fragmento que se muestra al inicio
        showSelectedFragment(new ExplorarFragment(), UtilDates.EXPLORAR_TAG);

        // Para el menu de abajo
        footerMenu = (BottomNavigationView) findViewById(R.id.menu_footer);
        // Marcado por defecto el explorar
        footerMenu.setSelectedItemId(R.id.menu_explorar);
        // Listener de la opciones del menú
        footerMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_favoritos){
                    showSelectedFragment(new FavoritosFragment(), UtilDates.FAVORITOS_TAG);
                }
                if(menuItem.getItemId() == R.id.menu_explorar){
                    showSelectedFragment(new ExplorarFragment(), UtilDates.EXPLORAR_TAG);
                }
                if(menuItem.getItemId() == R.id.menu_buscar){
                    showSelectedFragment(new FavoritosFragment(), UtilDates.FAVORITOS_TAG);
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

    public void onListFragmentInteraction(Evento evento){
        showItemSelectedFragment(new VistaEventoFragment(evento), UtilDates.VISTA_EVENTO_TAG);
    }

    public void OnFavoritosItemListener(Evento evento){
        showItemSelectedFragment(new VistaEventoFragment(evento), UtilDates.VISTA_EVENTO_TAG);
    }

    @Override
    public void onBackPressed(){
        if (currentFragmentTag == UtilDates.VISTA_EVENTO_TAG) {
            Fragment olderFragment = getSupportFragmentManager().findFragmentByTag(oldFragmentTag);
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(currentFragmentTag);
            currentFragmentTag = oldFragmentTag;
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
}
