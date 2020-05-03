package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.EventoFragment;
import cobit19.ecci.ucr.ac.eventosucr.features.explorar.Explorar;

public class MainActivity extends AppCompatActivity implements EventoFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateDatabase();
        /*setContentView(R.layout.activity_main);*/

        Intent a =new Intent(this, Explorar.class);
        startActivity(a);

        /*if (findViewById(R.id.activity_main) != null) {
            if (savedInstanceState == null) {
                LinearLayout linearLayout = findViewById(R.id.activity_main);
                CategoriaService categoriaService = new CategoriaService();
                for(Categoria categoria: categoriaService.leerListaDeCategoriasConEventos(getApplicationContext())) {
                    final TextView textView = new TextView(getApplicationContext());
                    textView.setTextSize(18);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setText(categoria.getNombre());
                    linearLayout.addView(textView);

                    EventoFragment eventoFragment = new EventoFragment(categoria);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.activity_main, eventoFragment).commit();
                }
            }
        }*/
    }

    public void cambiarDePantalla() {
        Intent a =new Intent(this,CrearEvento.class);
        startActivity(a);
    }

    public void onListFragmentInteraction(Evento evento){
        System.out.println(evento.toString());
    }

    private void populateDatabase() {
        Context context = getApplicationContext();
        CategoriaService categoriaService = new CategoriaService();
        CategoriaEventoService categoriaEventoService = new CategoriaEventoService();
        EventoService eventoService = new EventoService();

        ArrayList<String> idCategorias = new ArrayList<>();
        ArrayList<String> idEventos = new ArrayList<>();

        // Creamos las categorias
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Musica", "Representa los eventos relacionados con la musica"))
        ));
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Comida", "Representa los eventos relacionados con comida"))
        ));
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Arte", "Representa los eventos relacionados con el arte"))
        ));

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date());
        // Creamos los eventos
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Festival", "Promete", "asdf", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Son Latino", "Latino America", "", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Cantos Gregorianos", "A lo clasico", "", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Tributo a Zelda", "Filarmonica", "", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Tributo a Mario Bros", "Filarmonica", "", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Pintura", "Artistas", "", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Dibujo", "Artistas", "", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Lapiz y Papel", "No hay color", "", "", fecha, "", "", ""))
        ));

        // Asociamos a Musica
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(0), idEventos.get(0)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(0), idEventos.get(1)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(0), idEventos.get(2)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(0), idEventos.get(3)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(0), idEventos.get(4)));
        // Asociamos a Artesanias
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(2), idEventos.get(5)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(2), idEventos.get(6)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(2), idEventos.get(7)));
    }
}
