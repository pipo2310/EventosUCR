package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cobit19.ecci.ucr.ac.eventosucr.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;

public class Explorar extends AppCompatActivity implements CartaEventoFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar);

        if (findViewById(R.id.explorar_lista_categorias) != null) {
            if (savedInstanceState == null) {
                CategoriaService categoriaService = new CategoriaService();

                for(Categoria categoria: categoriaService.leerListaDeCategoriasConEventos(getApplicationContext())) {
                    ListaCartaEventoFragment listaCartaEventoFragment = new ListaCartaEventoFragment(categoria);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.explorar_lista_categorias, listaCartaEventoFragment).commit();
                }
            }
        }
    }

    public void onListFragmentInteraction(Evento evento){
        System.out.println(evento.toString());
    }
}
