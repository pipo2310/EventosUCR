package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Imagen;

public class ImagenService {

    public static final String[] projection = {
            DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_ID,
            DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_ID_EVENTO,
            DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_IMAGEN
    };

    public static final String[] imagenesRecuperadas = {
            DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_IMAGEN
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    // Método para pasar un bitmap (imagen) a ByteArray (blob)
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public long insertar(Context context, Imagen imagen){
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        byte [] imagenBlob = getBitmapAsByteArray(imagen.getImagen());

        values.put(DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_ID_EVENTO, imagen.getIdEvento());
        values.put(DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_IMAGEN, imagenBlob);

        return db.insert(DataBaseContract.TABLE_IMAGEN_EVENTO, null, values);
    }

    // recupera la/las imagenes de un evento en especifico
    public ArrayList<Bitmap> leerImagenEvento(Context context, String idEvento){
        SQLiteDatabase db = getSQLiteDatabase(context);
        ArrayList<Bitmap> listaImagenes = new ArrayList<>();
        ContentValues values = new ContentValues();
        String selection = DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_ID_EVENTO + " = ?";
        String[] selectionArgs = {idEvento};

        Cursor cursor = db.query(
                DataBaseContract.TABLE_IMAGEN_EVENTO, // tabla
                imagenesRecuperadas, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            do{
                byte[] imagenBlob = cursor.getBlob(cursor.getColumnIndex(DataBaseContract.TABLE_IMAGEN_EVENTO_COLUMN_IMAGEN));
                Bitmap imagen = BitmapFactory.decodeByteArray(imagenBlob,0,imagenBlob.length);
                listaImagenes.add(imagen);
                imagen.recycle(); // Hay que probar esto
            }while (cursor.moveToNext());

        }
        return listaImagenes;
    }


}
