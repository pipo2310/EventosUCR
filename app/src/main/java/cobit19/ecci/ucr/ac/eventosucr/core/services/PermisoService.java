package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Permiso;

public class PermisoService {

    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_PERMISO_COLUMN_ID,
            DataBaseContract.TABLE_PERMISO_COLUMN_NOMBRE,
            DataBaseContract.TABLE_PERMISO_COLUMN_DETALLES,
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private Permiso obtenerPermiso(Cursor cursor) {
        Permiso permiso = new Permiso();

        if (cursor.getCount() > 0) {
            permiso.setId(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_PERMISO_COLUMN_ID)));
            permiso.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_PERMISO_COLUMN_NOMBRE)));
            permiso.setDetalles(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_PERMISO_COLUMN_DETALLES)));
        }

        return permiso;
    }

    private ArrayList<Permiso> generarLista(Cursor cursor) {
        ArrayList<Permiso> permisos = new ArrayList<Permiso>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                permisos.add(obtenerPermiso(cursor));
                cursor.moveToNext();
            }
        }

        return permisos;
    }

    private ContentValues prepararValores(Permiso permiso) {
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_PERMISO_COLUMN_NOMBRE, permiso.getNombre());
        values.put(DataBaseContract.TABLE_PERMISO_COLUMN_DETALLES, permiso.getDetalles());

        return values;
    }

    public long insertar(Context context, Permiso permiso) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(permiso);

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_PERMISO, null, values);
    }

    public Permiso leer(Context context, String idParametro) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_PERMISO_COLUMN_ID + " = ?";
        String[] selectionArgs = {idParametro};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_PERMISO, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerPermiso(cursor);
    }

    public ArrayList<Permiso> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_PERMISO, // tabla
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

    public long actualizar(Context context, Permiso permiso) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(permiso);

        // Criterio de actualizacion
        String selection = DataBaseContract.TABLE_PERMISO_COLUMN_ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {permiso.getId()};
        // Actualizar la base de datos
        return db.update(DataBaseContract.TABLE_PERMISO, values, selection, selectionArgs);
    }

    public void eliminar (Context context, String idParametro){
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Define el where para el borrado
        String selection = DataBaseContract.TABLE_PERMISO_COLUMN_ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {idParametro};
        // Realiza el SQL de borrado
        db.delete(DataBaseContract.TABLE_PERMISO, selection, selectionArgs);
    }
}
