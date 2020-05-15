package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Rol;

public class RolService {
    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_ROL_COLUMN_ID,
            DataBaseContract.TABLE_ROL_COLUMN_NOMBRE,
            DataBaseContract.TABLE_ROL_COLUMN_DETALLES,
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private Rol obtenerRol(Cursor cursor) {
        Rol rol = new Rol();

        if (cursor.getCount() > 0) {
            rol.setId(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_ROL_COLUMN_ID)));
            rol.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_ROL_COLUMN_NOMBRE)));
            rol.setDetalles(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_ROL_COLUMN_DETALLES)));
        }

        return rol;
    }

    private ArrayList<Rol> generarLista(Cursor cursor) {
        ArrayList<Rol> roles = new ArrayList<Rol>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                roles.add(obtenerRol(cursor));
                cursor.moveToNext();
            }
        }

        return roles;
    }

    private ContentValues prepararValores(Rol rol) {
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_ROL_COLUMN_NOMBRE, rol.getNombre());
        values.put(DataBaseContract.TABLE_ROL_COLUMN_DETALLES, rol.getDetalles());

        return values;
    }

    public long insertar(Context context, Rol rol) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(rol);

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_ROL, null, values);
    }

    public Rol leer(Context context, String idParametro) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_ROL_COLUMN_ID + " = ?";
        String[] selectionArgs = {idParametro};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_ROL, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerRol(cursor);
    }

    public ArrayList<Rol> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_ROL, // tabla
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

    public long actualizar(Context context, Rol rol) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(rol);

        // Criterio de actualizacion
        String selection = DataBaseContract.TABLE_ROL_COLUMN_ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {rol.getId()};
        // Actualizar la base de datos
        return db.update(DataBaseContract.TABLE_ROL, values, selection, selectionArgs);
    }

    public void eliminar (Context context, String idParametro){
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Define el where para el borrado
        String selection = DataBaseContract.TABLE_ROL_COLUMN_ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {idParametro};
        // Realiza el SQL de borrado
        db.delete(DataBaseContract.TABLE_ROL, selection, selectionArgs);
    }
}
