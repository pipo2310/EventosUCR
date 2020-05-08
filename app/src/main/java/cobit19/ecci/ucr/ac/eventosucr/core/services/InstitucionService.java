package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.io.ByteArrayOutputStream;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Institucion;

public class InstitucionService {
    public static final String[] projection = {
            DataBaseContract.TABLE_INSTITUCION_COLUMN_ID,
            DataBaseContract.TABLE_INSTITUCION_COLUMN_NOMBRE,
            DataBaseContract.TABLE_INSTITUCION_COLUMN_LOGO
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    public long insertar(Context context, Institucion institucion){
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = new ContentValues();

        byte [] logoBlob = getBitmapAsByteArray(institucion.getLogo());

        values.put(DataBaseContract.TABLE_INSTITUCION_COLUMN_NOMBRE, institucion.getNombre());
        values.put(DataBaseContract.TABLE_INSTITUCION_COLUMN_LOGO, logoBlob);

        return db.insert(DataBaseContract.TABLE_INSTITUCION, null, values);


    }

    // Recupera los datos de una sola institucion
    public Institucion leer(Context context, String idInstitucion){
        SQLiteDatabase db = getSQLiteDatabase(context);
        Institucion institucion = new Institucion();

        ContentValues values = new ContentValues();
        String selection = DataBaseContract.TABLE_INSTITUCION_COLUMN_ID + " = ?";
        String[] selectionArgs = {idInstitucion};

        Cursor cursor = db.query(DataBaseContract.TABLE_INSTITUCION,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            institucion.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_INSTITUCION_COLUMN_NOMBRE)));
            byte[] logoBlob = cursor.getBlob(cursor.getColumnIndex(DataBaseContract.TABLE_INSTITUCION_COLUMN_LOGO));
            Bitmap logo = BitmapFactory.decodeByteArray(logoBlob,0,logoBlob.length);
            institucion.setLogo(logo);
        }
        return institucion;
    }

    public int actualizar(Context context, Institucion institucion){
        SQLiteDatabase db = getSQLiteDatabase(context);
        ContentValues values = new ContentValues();
        byte [] logoBlob = getBitmapAsByteArray(institucion.getLogo());

        values.put(DataBaseContract.TABLE_INSTITUCION_COLUMN_NOMBRE, institucion.getNombre());
        values.put(DataBaseContract.TABLE_INSTITUCION_COLUMN_LOGO, logoBlob);

        String selection = DataBaseContract.TABLE_INSTITUCION_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {institucion.getId()};

        return db.update(DataBaseContract.TABLE_INSTITUCION, values, selection, selectionArgs);
    }

    public void eliminar(Context context, String id){
        SQLiteDatabase db = getSQLiteDatabase(context);
        String selection = DataBaseContract.TABLE_INSTITUCION_COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {id};
        db.delete(DataBaseContract.TABLE_INSTITUCION, selection, selectionArgs);
    }
}
