package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.MenuActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;
import cobit19.ecci.ucr.ac.eventosucr.shared.ListaEventosFragment;
import cobit19.ecci.ucr.ac.eventosucr.shared.SinResultadosFragment;

public class BuscarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        ListaEventosFragment.OnEventoSeleccionadoInteractionListener {

    public static final String EVENTO = "evento";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        SearchView searchView = (SearchView) findViewById(R.id.buscar_barra);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        EventoService eventoService = new EventoService();
        ArrayList<Evento> eventos = eventoService.leetListaEventosCuyoNombreContiene(getApplicationContext(), query);

        if (eventos.size() > 0) {
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

            ListaEventosFragment listaEventosFragment = new ListaEventosFragment(eventos, imagenesdeEventos);

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
