package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.RolUsuarioInstitucion;

public class RolUsuarioInstitucionService {
    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL,
            DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO,
            DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION,
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private RolUsuarioInstitucion obtenerRolUsuarioInstitucion(Cursor cursor) {
        RolUsuarioInstitucion rolUsuarioInstitucion = new RolUsuarioInstitucion();

        if (cursor.getCount() > 0) {
            rolUsuarioInstitucion.setIdRol(cursor.getString(cursor.getColumnIndex(
                    DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL)));
            rolUsuarioInstitucion.setCorreoUcr(cursor.getString(cursor.getColumnIndex(
                    DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO)));
            rolUsuarioInstitucion.setIdInstitucion(cursor.getString(cursor.getColumnIndex(
                    DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION)));
        }

        return rolUsuarioInstitucion;
    }

    private ArrayList<RolUsuarioInstitucion> generarLista(Cursor cursor) {
        ArrayList<RolUsuarioInstitucion> listaRolUsuarioInstitucion = new ArrayList<RolUsuarioInstitucion>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                listaRolUsuarioInstitucion.add(obtenerRolUsuarioInstitucion(cursor));
                cursor.moveToNext();
            }
        }

        return listaRolUsuarioInstitucion;
    }

    private ContentValues prepararValores(RolUsuarioInstitucion rolUsuarioInstitucion) {
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL, rolUsuarioInstitucion.getIdRol());
        values.put(DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO, rolUsuarioInstitucion.getCorreoUcr());
        values.put(DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION, rolUsuarioInstitucion.getIdInstitucion());

        return values;
    }

    public long insertar(Context context, RolUsuarioInstitucion rolUsuarioInstitucion) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(rolUsuarioInstitucion);

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION, null, values);
    }

    public RolUsuarioInstitucion leer(Context context, String idParametroRol, String idParamentroUsuario,
                                      String idParamentroInstitucion) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL + " = ? AND " +
                DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + " = ? AND " +
        DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + " = ?";
        String[] selectionArgs = {idParametroRol, idParamentroUsuario, idParamentroInstitucion};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerRolUsuarioInstitucion(cursor);
    }

    public ArrayList<RolUsuarioInstitucion> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION, // tabla
                projection, // columnas
                null, // where
                null, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return generarLista(cursor);
    }

    public void eliminar (Context context, String idParametroRol, String idParamentroUsuario,
                          String idParamentroInstitucion){
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Define el where para el borrado
        String selection = DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL + " LIKE ? AND " +
                DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + " LIKE ? AND " +
                DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {idParametroRol, idParamentroUsuario, idParamentroInstitucion};
        // Realiza el SQL de borrado
        db.delete(DataBaseContract.TABLE_ROL_USUARIO_INSTITUCION, selection, selectionArgs);
    }
}
