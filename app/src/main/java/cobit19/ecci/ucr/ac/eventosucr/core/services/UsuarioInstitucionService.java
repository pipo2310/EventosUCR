package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.UsuarioInstitucion;

public class UsuarioInstitucionService {
    public static final String[] projection = {
            DataBaseContract.TABLE_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO,
            DataBaseContract.TABLE_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION
    };


    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private long insertar(Context context, UsuarioInstitucion usuarioInstitucion){
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values =  new ContentValues();

        values.put(DataBaseContract.TABLE_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION,
                usuarioInstitucion.getIdInstituicion());
        values.put(DataBaseContract.TABLE_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO,
                usuarioInstitucion.getCorreoUcr());

        return db.insert(DataBaseContract.TABLE_USUARIO_INSTITUCION, null,values);
    }
}
