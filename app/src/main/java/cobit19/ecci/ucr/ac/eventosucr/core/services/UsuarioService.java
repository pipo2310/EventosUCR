package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Usuario;

public class UsuarioService {

    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_USUARIO_COLUMN_CORREO_UCR,
            DataBaseContract.TABLE_USUARIO_COLUMN_CONTRASENIA,
            DataBaseContract.TABLE_USUARIO_COLUMN_NOMBRE,
            DataBaseContract.TABLE_USUARIO_COLUMN_APELLIDO1,
            DataBaseContract.TABLE_USUARIO_COLUMN_APELLIDO2,
    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private Usuario obtenerUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();

        if (cursor.getCount() > 0) {
            usuario.setCorreoUcr(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_USUARIO_COLUMN_CORREO_UCR)));
            usuario.setContrasenia(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_USUARIO_COLUMN_CONTRASENIA)));
            usuario.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_USUARIO_COLUMN_NOMBRE)));
            usuario.setApellido1(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_USUARIO_COLUMN_APELLIDO1)));
            usuario.setApellido2(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_USUARIO_COLUMN_APELLIDO2)));
        }

        return usuario;
    }

    private ArrayList<Usuario> generarLista(Cursor cursor) {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                usuarios.add(obtenerUsuario(cursor));
                cursor.moveToNext();
            }
        }

        return usuarios;
    }

    private ContentValues prepararValores(Usuario usuario) {
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_USUARIO_COLUMN_CONTRASENIA, usuario.getContrasenia());
        values.put(DataBaseContract.TABLE_USUARIO_COLUMN_NOMBRE, usuario.getNombre());
        values.put(DataBaseContract.TABLE_USUARIO_COLUMN_APELLIDO1, usuario.getApellido1());
        values.put(DataBaseContract.TABLE_USUARIO_COLUMN_APELLIDO2, usuario.getApellido2());

        return values;
    }

    public long insertar(Context context, Usuario usuario) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(usuario);
        values.put(DataBaseContract.TABLE_USUARIO_COLUMN_CORREO_UCR, usuario.getCorreoUcr());

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_USUARIO, null, values);
    }

    public Usuario leer(Context context, String idParametro) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_USUARIO_COLUMN_CORREO_UCR + " = ?";
        String[] selectionArgs = {idParametro};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_USUARIO, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerUsuario(cursor);
    }

    public ArrayList<Usuario> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_USUARIO, // tabla
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

    public long actualizar(Context context, Usuario usuario) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        ContentValues values = prepararValores(usuario);

        // Criterio de actualizacion
        String selection = DataBaseContract.TABLE_USUARIO_COLUMN_CORREO_UCR + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {usuario.getCorreoUcr()};
        // Actualizar la base de datos
        return db.update(DataBaseContract.TABLE_USUARIO, values, selection, selectionArgs);
    }

    public void eliminar (Context context, String idParametro){
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Define el where para el borrado
        String selection = DataBaseContract.TABLE_USUARIO_COLUMN_CORREO_UCR + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {idParametro};
        // Realiza el SQL de borrado
        db.delete(DataBaseContract.TABLE_USUARIO, selection, selectionArgs);
    }
}
