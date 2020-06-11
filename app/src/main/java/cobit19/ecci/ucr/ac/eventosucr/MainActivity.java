package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.core.models.CategoriaEvento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Institucion;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Usuario;
import cobit19.ecci.ucr.ac.eventosucr.core.models.UsuarioEvento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.InstitucionService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.UsuarioEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.UsuarioService;
import cobit19.ecci.ucr.ac.eventosucr.features.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences ("COBIT19_EVENTOS_UCR", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("NOT_FIRST_RUN", false)) {
            populateDatabase();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("NOT_FIRST_RUN", true);
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Obtenemos el firebase auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Verificar si hay un usuario inscrito en la app
        if(mAuth.getCurrentUser() == null) {
            // Si no hay un usuario se envia a la pantalla de login
            cambiarDePantalla(LoginActivity.class);
        }else{
            // Si ya hay un usuario se envia a la vista de explorar
            cambiarDePantalla(MenuActivity.class);
        }
    }


    public void cambiarDePantalla(Class<?> activity) {
        Intent a =new Intent(this, activity);
        startActivity(a);
        finish();
    }

    private void populateDatabase() {
        Context context = getApplicationContext();
        InstitucionService institucionService = new InstitucionService();
        CategoriaService categoriaService = new CategoriaService();
        CategoriaEventoService categoriaEventoService = new CategoriaEventoService();
        EventoService eventoService = new EventoService();
        UsuarioService usuarioService = new UsuarioService();
        UsuarioEventoService usuarioEventoService = new UsuarioEventoService();
        ImagenService imagenService = new ImagenService();

        ArrayList<String> idInstituciones = new ArrayList<>();
        ArrayList<String> idCategorias = new ArrayList<>();
        ArrayList<String> idEventos = new ArrayList<>();

        // Creamos las instituciones
        idInstituciones.add(Long.toString(
                institucionService.insertar(context, new Institucion("Institucion musical", null))
        ));
        idInstituciones.add(Long.toString(
                institucionService.insertar(context, new Institucion("Institucion gastronomica", null))
        ));
        idInstituciones.add(Long.toString(
                institucionService.insertar(context, new Institucion("Institucion artistica", null))
        ));
        idInstituciones.add(Long.toString(
                institucionService.insertar(context, new Institucion("Institucion de baile", null))
        ));

        // Creamos las categorias
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Musica", "Representa los eventos relacionados con la musica", null))
        ));
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Comida", "Representa los eventos relacionados con comida", null))
        ));
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Arte", "Representa los eventos relacionados con el arte", null))
        ));
        idCategorias.add(Long.toString(
                categoriaService.insertar(context, new Categoria("Baile", "Representa los eventos relacionados con el baile", null))
        ));

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date());
        String detalles = "Vivamus ac pulvinar eros. Donec ut erat vel turpis tempor pharetra. " +
                "Nullam condimentum neque quis facilisis commodo. In tortor eros, suscipit " +
                "sed augue quis, accumsan efficitur orci. Sed sit amet ligula eget felis " +
                "consequat sagittis. Praesent sed sapien rutrum, consectetur massa id, " +
                "malesuada mauris.";
        String horaInicio = "11:00";
        String horaFin = "15:00";
        String ubicacion = "300 mts norte del palo de mango";
        // Creamos los eventos

        // Pertenecen a la categoria musica
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Festival", idInstituciones.get(0), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Son Latino", idInstituciones.get(0), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Cantos Gregorianos", idInstituciones.get(0), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Tributo a Zelda", idInstituciones.get(0), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Tributo a Mario Bros", idInstituciones.get(0), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));

        // Pertenecen a la categoria Arte
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Pintura", idInstituciones.get(2), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Dibujo", idInstituciones.get(2), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Lapiz y Papel", idInstituciones.get(2), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));

        // Pertenecen a la categoria Baile
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Twerking", idInstituciones.get(3), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Bailar merengue", idInstituciones.get(3), detalles, fecha, horaInicio, horaFin, ubicacion, 0.0, 0.0))
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

        // Creamos los usuarios
        usuarioService.insertar(context, new Usuario("walter.bonillagutierrez@ucr.ac.cr", "Walter", "hola1234", "Bonilla", "Gutierrez"));

        // Asociamos los eventos que le gustan al usuario walter.bonillagutierrez@ucr.ac.cr
        usuarioEventoService.insertar(context, new UsuarioEvento("walter.bonillagutierrez@ucr.ac.cr", idEventos.get(0)));
        usuarioEventoService.insertar(context, new UsuarioEvento("walter.bonillagutierrez@ucr.ac.cr", idEventos.get(1)));
        usuarioEventoService.insertar(context, new UsuarioEvento("walter.bonillagutierrez@ucr.ac.cr", idEventos.get(2)));
        usuarioEventoService.insertar(context, new UsuarioEvento("walter.bonillagutierrez@ucr.ac.cr", idEventos.get(3)));
    }
}
