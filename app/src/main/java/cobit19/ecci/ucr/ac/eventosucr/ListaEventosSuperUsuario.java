package cobit19.ecci.ucr.ac.eventosucr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.fragments.shared.ListaEventosFragment;

public class ListaEventosSuperUsuario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Evento>eventos;
    ListView list;
    public final static String EXTRA_MESSAGE="evento";
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_super_usuario);

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
        EventoService eventoService=new EventoService();
        eventos=eventoService.leerLista(getApplicationContext());
        //llamar a fragmento con vista de listaeventossuperusuario id=(listaEventosFragment) y la lista de eventos de parametros para hacer vuelta del adapter
        ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventos);
        getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        //getFragmentManager().beginTransaction()
          //      .add(R.id.listaEventosFragmentVista, listaEventosFragment).commit();
/*
        CustomListAdapter adapter = new CustomListAdapter(this, eventos);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Evento eventoSeleccionado = eventos.get(position);
                //Irse a otra pantalla con los extras desde esta para no hacer otra llamada a la base en la siguiente actividad
                cambiarDePantalla(eventoSeleccionado);


            }
        });

 */

    }
/*
    public void cambiarDePantalla(Evento evento) {
        Intent intent = new Intent(this, ModificarEliminarEvento.class);
        intent.putExtra(EXTRA_MESSAGE, evento);

        // Deseo recibir una respuesta: startActivityForResult()
        startActivityForResult(intent, 0);
        finish();
    }
*/
    /**
     * Metodo para el menu lateral aqui se pone donde se va la aplicacion cada vez que se toca un item
     * @param menuItem
     * @return
     */
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
}
