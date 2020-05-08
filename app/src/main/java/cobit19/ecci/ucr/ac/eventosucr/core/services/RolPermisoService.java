package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.RolPermiso;

public class RolPermisoService {
    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_ROL,
            DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_PERMISO
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private RolPermiso obtenerRolPermiso(Cursor cursor) {
        RolPermiso rolPermiso = new RolPermiso();

        if (cursor.getCount() > 0) {
            rolPermiso.setIdRol(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_ROL)));
            rolPermiso.setIdPermiso(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_PERMISO)));
        }

        return rolPermiso;
    }

    private ArrayList<RolPermiso> generarLista(Cursor cursor) {
        ArrayList<RolPermiso> listaRolPermiso = new ArrayList<RolPermiso>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                listaRolPermiso.add(obtenerRolPermiso(cursor));
                cursor.moveToNext();
            }
        }

        return listaRolPermiso;
    }

    private ContentValues prepararValores(RolPermiso rolPermiso) {
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_ROL, rolPermiso.getIdRol());
        values.put(DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_PERMISO, rolPermiso.getIdPermiso());

        return values;
    }

    public long insertar(Context context, RolPermiso rolPermiso) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(rolPermiso);

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_ROL_PERMISO, null, values);
    }

    public RolPermiso leer(Context context, String idParametroRol, String idParamentroPermiso) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_ROL + " = ? AND " +
                DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_PERMISO + " = ?";
        String[] selectionArgs = {idParametroRol, idParamentroPermiso};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_ROL_PERMISO, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerRolPermiso(cursor);
    }

    public ArrayList<RolPermiso> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_ROL_PERMISO, // tabla
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

    public void eliminar (Context context, String idParametroRol, String idParamentroPermiso){
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Define el where para el borrado
        String selection = DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_ROL + " LIKE ? AND " +
                DataBaseContract.TABLE_ROL_PERMISO_COLUMN_ID_PERMISO + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {idParametroRol, idParamentroPermiso};
        // Realiza el SQL de borrado
        db.delete(DataBaseContract.TABLE_ROL_PERMISO, selection, selectionArgs);
    }
}
