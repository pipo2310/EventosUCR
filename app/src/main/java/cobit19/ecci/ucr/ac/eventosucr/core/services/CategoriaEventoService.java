package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.CategoriaEvento;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;

public class CategoriaEventoService {
    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA,
            DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private CategoriaEvento obtenerCategoriaEvento(Cursor cursor) {
        CategoriaEvento categoriaEvento = new CategoriaEvento();

        if (cursor.getCount() > 0) {
            categoriaEvento.setIdCategoria(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA)));
            categoriaEvento.setIdEvento(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO)));
        }

        return categoriaEvento;
    }

    private ArrayList<CategoriaEvento> generarLista(Cursor cursor) {
        ArrayList<CategoriaEvento> listaCategoriaEvento = new ArrayList<CategoriaEvento>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                listaCategoriaEvento.add(obtenerCategoriaEvento(cursor));
                cursor.moveToNext();
            }
        }

        return listaCategoriaEvento;
    }

    public long insertar(Context context, CategoriaEvento categoriaEvento) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA, categoriaEvento.getIdCategoria());
        values.put(DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO, categoriaEvento.getIdEvento());

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_CATEGORIA_EVENTO, null, values);
    }

    public CategoriaEvento leer(Context context, String idParametroCategoria, String idParamentroEvento) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA + " = ? AND " +
                DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO + " = ?";
        String[] selectionArgs = {idParametroCategoria, idParamentroEvento};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_CATEGORIA_EVENTO, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerCategoriaEvento(cursor);
    }

    public ArrayList<CategoriaEvento> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_CATEGORIA_EVENTO, // tabla
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
}
