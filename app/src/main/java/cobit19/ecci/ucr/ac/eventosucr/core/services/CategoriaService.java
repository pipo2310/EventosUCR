package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.Categoria;

public class CategoriaService {

    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_CATEGORIA_COLUMN_ID,
            DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE,
            DataBaseContract.TABLE_CATEGORIA_COLUMN_DETALLES
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private Categoria obtenerCategoria(Cursor cursor) {
        Categoria categoria = new Categoria();

        if (cursor.getCount() > 0) {
            categoria.setId(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_COLUMN_ID)));
            categoria.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE)));
            categoria.setDetalles(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_COLUMN_DETALLES)));
        }

        return categoria;
    }

    private ArrayList<Categoria> generarLista(Cursor cursor) {
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                categorias.add(obtenerCategoria(cursor));
                cursor.moveToNext();
            }
        }

        return categorias;
    }

    public long insertar(Context context, Categoria categoria) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE, categoria.getNombre());
        values.put(DataBaseContract.TABLE_CATEGORIA_COLUMN_DETALLES, categoria.getDetalles());

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_CATEGORIA, null, values);
    }

    public Categoria leer(Context context, String idParametro) {
        SQLiteDatabase db = getSQLiteDatabase(context);



        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_CATEGORIA_COLUMN_ID + " = ?";
        String[] selectionArgs = {idParametro};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_CATEGORIA, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerCategoria(cursor);
    }

    public ArrayList<Categoria> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_CATEGORIA, // tabla
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

    public ArrayList<Categoria> leerListaDeCategoriasConEventos(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        String sql = "SELECT * " +
                "FROM " + DataBaseContract.TABLE_CATEGORIA + " categoria " +
                "WHERE EXISTS (SELECT * " +
                "FROM " + DataBaseContract.TABLE_CATEGORIA_EVENTO + " categoriaEvento " +
                "WHERE categoria." + DataBaseContract.TABLE_CATEGORIA_COLUMN_ID + " = " +
                "categoriaEvento." + DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA + ")";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        return generarLista(cursor);
    }
}
