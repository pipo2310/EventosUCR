package cobit19.ecci.ucr.ac.eventosucr.features.explorar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import cobit19.ecci.ucr.ac.eventosucr.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.Evento;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;

public class Explorar extends AppCompatActivity implements BlankFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorar);

        if (findViewById(R.id.explorar_lista_categorias) != null) {
            if (savedInstanceState == null) {
                CategoriaService categoriaService = new CategoriaService();

                for(Categoria categoria: categoriaService.leerListaDeCategoriasConEventos(getApplicationContext())) {
                    BlankFragment2 blankFragment2 = new BlankFragment2(categoria);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.explorar_lista_categorias, blankFragment2).commit();
                }
            }
        }
    }

    public void onListFragmentInteraction(Evento evento){
        System.out.println(evento.toString());
    }
}
