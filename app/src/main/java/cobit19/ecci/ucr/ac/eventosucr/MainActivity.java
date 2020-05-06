package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences ("PREFERENCES", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("FIRST_RUN", true)) {
            populateDatabase();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("FIRST_RUN", false);
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent a =new Intent(this, MenuActivity.class);
        startActivity(a);
    }


    public void cambiarDePantalla() {
        Intent a =new Intent(this,CrearEvento.class);
        startActivity(a);
    }

    public void irMenu(){
        Intent a =new Intent(this, MenuActivity.class);
        startActivity(a);
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
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Baile", "Representa los eventos relacionados con el baile"))
        ));

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date());
        // Creamos los eventos
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("FestivalUCR", "UCR", "Música, ventas y más", fecha, "10:00", "07:00", "Universidad de Costa Rica"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Bailongo", "Son Latino", "Ven a bailar merengue, salsa y bachata", fecha, "07:00", "10:00", "Facultad de Educación"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Cantos Gregorianos", "Artes Dramáticas", "Evento artístico",fecha, "05:00", "09:00", "Facultad de Artes Dramáticas"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Tributo a Zelda", "Filarmonica", "Para los amantes de Zelda",fecha, "03:00", "07:00", "Teatro Nacional"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Tributo a Coldplay", "UCR", "Las mejores canciones de Coldplay",fecha, "09:00", "12:00", "Hard Rock Café"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Pintura", "Escuela de Artes", "Si eres amante de la pintura, este evento es para vos!",fecha, "09:00", "01:00", "Pretil UCR"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Aprende a dibujar", "Escuela de Artes", "Quieres aprender a dibujar? Te esperamos",fecha, "01:00", "03:00", "Pretil UCR"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Lapiz y Papel", "Feucr", "Obra de teatro",fecha, "02:00", "03:00", "Universidad de Costa Rica"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Muevelo", "Escuela de Música", "Taller de baile gratis",fecha, "09:00", "12:00", "Facultad de Música"))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Bailar merengue", "Escuela de Música", "Aprende a bailar en una clase",fecha, "10:00", "12:00", "Pretil UCR"))
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
        // Asociamos a Bailes
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(3), idEventos.get(1)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(3), idEventos.get(8)));
        categoriaEventoService.insertar(context, new CategoriaEvento(idCategorias.get(3), idEventos.get(9)));
    }
}
