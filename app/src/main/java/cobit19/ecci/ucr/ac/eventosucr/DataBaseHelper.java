package cobit19.ecci.ucr.ac.eventosucr;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Implementacion de SQLiteOpenHelper para administracion de la base de datos
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Cada vez que cambie el esquema de la base de datos DataBaseContract,
    // debemos incrementar la version de la base de datos
    public static final int DATABASE_VERSION = 10;

    // Nombre de la base de datos
    public static final String DATABASE_NAME = "AndroidStorage.db";

    // constructor de la clase, el contexto tiene la informacion global sobre el ambiente de la app
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
}
