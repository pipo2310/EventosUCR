package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.UsuarioEvento;

public class UsuarioEventoService {

    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO,
            DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO,
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private UsuarioEvento obtenerUsuarioEvento(Cursor cursor) {
        UsuarioEvento usuarioEvento = new UsuarioEvento();

        if (cursor.getCount() > 0) {
            usuarioEvento.setCorreoUcr(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO)));
            usuarioEvento.setIdEvento(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO)));
        }

        return usuarioEvento;
    }

    private ArrayList<UsuarioEvento> generarLista(Cursor cursor) {
        ArrayList<UsuarioEvento> listaUsuarioEvento = new ArrayList<UsuarioEvento>();
  
        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                listaUsuarioEvento.add(obtenerUsuarioEvento(cursor));
                cursor.moveToNext();
            }
        }

        return listaUsuarioEvento;
    }

    private ContentValues prepararValores(UsuarioEvento usuarioEvento) {
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO, usuarioEvento.getCorreoUcr());
        values.put(DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO, usuarioEvento.getIdEvento());

        return values;
    }

    public long insertar(Context context, UsuarioEvento usuarioEvento) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(usuarioEvento);

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_USUARIO_EVENTO, null, values);
    }

    public UsuarioEvento leer(Context context, String idParametroUsuario, String idParamentroEvento) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO + " = ? AND " +
                DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO + " = ?";
        String[] selectionArgs = {idParametroUsuario, idParamentroEvento};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_USUARIO_EVENTO, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerUsuarioEvento(cursor);
    }

    public ArrayList<UsuarioEvento> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_USUARIO_EVENTO, // tabla
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

    public void eliminar (Context context, String idParametroUsuario, String idParamentroEvento){
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Define el where para el borrado
        String selection = DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO + " LIKE ? AND " +
                DataBaseContract.TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {idParametroUsuario, idParamentroEvento};
        // Realiza el SQL de borrado
        db.delete(DataBaseContract.TABLE_USUARIO_EVENTO, selection, selectionArgs);
    }
}
