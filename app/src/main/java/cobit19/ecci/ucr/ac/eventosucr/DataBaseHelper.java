package cobit19.ecci.ucr.ac.eventosucr;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Implementacion de SQLiteOpenHelper para administracion de la base de datos
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Cada vez que cambie el esquema de la base de datos DataBaseContract,
    // debemos incrementar la version de la base de datos
    public static final int DATABASE_VERSION = 10;

    // Nombre de la base de datos
    public static final String DATABASE_NAME = "AndroidStorage.db";

    // constructor de la clase, el contexto tiene la informacion global sobre el ambiente de la app
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        /*populateDatabase(context);*/
    }

    // implementamos el metodo para la creacion de la base de datos
    public void onCreate(SQLiteDatabase db) {
        // Crear la base de datos de la app
        db.execSQL(DataBaseContract.SQL_CREATE_EVENTO);
        db.execSQL(DataBaseContract.SQL_CREATE_CATEGORIA);
        db.execSQL(DataBaseContract.SQL_CREATE_CATEGORIA_EVENTO);
    }
    // implementamos el metodo para la actualizacion de la base de datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Administracion de actualizaciones
        db.execSQL(DataBaseContract.SQL_DELETE_EVENTO);
        db.execSQL(DataBaseContract.SQL_DELETE_CATEGORIA);
        db.execSQL(DataBaseContract.SQL_DELETE_CATEGORIA_EVENTO);
        onCreate(db);
    }
    // inplementamos el metodo para volver a la version anterior de la base de datos
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void populateDatabase(Context context) {
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
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Twerking", "Muevelo muevelo", "", "", fecha, "", "", ""))
        ));
        idEventos.add(Long.toString(
                eventoService.insertar(context, new Evento("Bailar merengue", "Menealo", "", "", fecha, "", "", ""))
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
