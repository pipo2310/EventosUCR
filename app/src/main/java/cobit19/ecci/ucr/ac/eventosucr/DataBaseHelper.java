package cobit19.ecci.ucr.ac.eventosucr;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Implementacion de SQLiteOpenHelper para administracion de la base de datos
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Cada vez que cambie el esquema de la base de datos DataBaseContract,
    // debemos incrementar la version de la base de datos
    public static final int DATABASE_VERSION = 19;

    // Nombre de la base de datos
    public static final String DATABASE_NAME = "AndroidStorage.db";

    // constructor de la clase, el contexto tiene la informacion global sobre el ambiente de la app
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // implementamos el metodo para la creacion de la base de datos
    public void onCreate(SQLiteDatabase db) {
        // Crear la base de datos de la app
        db.execSQL(DataBaseContract.SQL_CREATE_INSTITUCION);
        db.execSQL(DataBaseContract.SQL_CREATE_CATEGORIA);
        db.execSQL(DataBaseContract.SQL_CREATE_EVENTO);
        db.execSQL(DataBaseContract.SQL_CREATE_IMAGEN_EVENTO);
        db.execSQL(DataBaseContract.SQL_CREATE_CATEGORIA_EVENTO);
        db.execSQL(DataBaseContract.SQL_CREATE_PERMISO);
        db.execSQL(DataBaseContract.SQL_CREATE_ROL);
        db.execSQL(DataBaseContract.SQL_CREATE_ROL_PERMISO);
        db.execSQL(DataBaseContract.SQL_CREATE_USUARIO);
        db.execSQL(DataBaseContract.SQL_CREATE_USUARIO_INSTITUCION);
        db.execSQL(DataBaseContract.SQL_CREATE_ROL_USUARIO_INSTITUCION);
        db.execSQL(DataBaseContract.SQL_CREATE_USUARIO_EVENTO);

    }
    // implementamos el metodo para la actualizacion de la base de datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Administracion de actualizaciones
        // Borrado inverso a como se insertan
        db.execSQL(DataBaseContract.SQL_DELETE_USUARIO_EVENTO);
        db.execSQL(DataBaseContract.SQL_DELETE_ROL_USUARIO_INSTITUCION);
        db.execSQL(DataBaseContract.SQL_DELETE_USUARIO_INSTITUCION);
        db.execSQL(DataBaseContract.SQL_DELETE_USUARIO);
        db.execSQL(DataBaseContract.SQL_DELETE_ROL_PERMISO);
        db.execSQL(DataBaseContract.SQL_DELETE_ROL);
        db.execSQL(DataBaseContract.SQL_DELETE_PERMISO);
        db.execSQL(DataBaseContract.SQL_DELETE_CATEGORIA_EVENTO);
        db.execSQL(DataBaseContract.SQL_DELETE_IMAGEN_EVENTO);
        db.execSQL(DataBaseContract.SQL_DELETE_EVENTO);
        db.execSQL(DataBaseContract.SQL_DELETE_CATEGORIA);
        db.execSQL(DataBaseContract.SQL_DELETE_INSTITUCION);
        onCreate(db);
    }
    // inplementamos el metodo para volver a la version anterior de la base de datos
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
