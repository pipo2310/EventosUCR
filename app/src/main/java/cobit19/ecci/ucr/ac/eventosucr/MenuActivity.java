package cobit19.ecci.ucr.ac.eventosucr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

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
import cobit19.ecci.ucr.ac.eventosucr.fragments.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CartaEventoFragment.OnListFragmentInteractionListener{

    BottomNavigationView footerMenu;
    DrawerLayout drawer;

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
        showSelectedFragment(new ExplorarFragment());

        // Para el menu de abajo
        footerMenu = (BottomNavigationView) findViewById(R.id.menu_footer);
        footerMenu.setSelectedItemId(R.id.menu_explorar);
        footerMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_favoritos){
                    showSelectedFragment(new FavoritosFragment());
                }
                if(menuItem.getItemId() == R.id.menu_explorar){
                    showSelectedFragment(new ExplorarFragment());
                }
                if(menuItem.getItemId() == R.id.menu_buscar){
                    showSelectedFragment(new FavoritosFragment());
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
    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void onListFragmentInteraction(Evento evento){
        showSelectedFragment(new VistaEventoFragment(evento));
    }
}
