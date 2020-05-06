package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;

public class EventoService {

    // Define cuales columnas quiere solicitar // en este caso todas las de la clase
    public static final String[] projection = {
            DataBaseContract.TABLE_EVENTO_COLUMN_ID,
            DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE,
            DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION,
            DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES,
            DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
            DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO,
            DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN,
            DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION

    };

    private SQLiteDatabase getSQLiteDatabase(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        return dataBaseHelper.getReadableDatabase();
    }

    private Evento obtenerEvento(Cursor cursor) {
        Evento evento = new Evento();
        Calendar fecha = Calendar.getInstance();

        if (cursor.getCount() > 0) {
            fecha.setTime(UtilDates.parsearaDate(
                    cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA))));

            evento.setId(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_ID)));
            evento.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE)));
            evento.setInstitucion(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION)));
            evento.setDetalles(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES)));
            evento.setFecha(fecha);
            evento.setHoraInicio(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO)));
            evento.setHoraFin(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN)));
            evento.setUbicacion(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION)));
        }

        return evento;
    }

    private ArrayList<Evento> generarLista(Cursor cursor) {
        ArrayList<Evento> eventos = new ArrayList<Evento>();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                eventos.add(obtenerEvento(cursor));
                cursor.moveToNext();
            }
        }

        return eventos;
    }

    public long insertar(Context context, Evento evento) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE, evento.getNombre());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION, evento.getInstitucion());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES, evento.getDetalles());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA, UtilDates.parsearaString(evento.getFecha().getTime()));
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO, evento.getHoraInicio());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION, evento.getUbicacion());


        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_EVENTO, null, values);
    }

    public Evento leer(Context context, String idParametro) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_EVENTO_COLUMN_ID + " = ?";
        String[] selectionArgs = {idParametro};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_EVENTO, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();
        return obtenerEvento(cursor);
    }

    public ArrayList<Evento> leerLista(Context context) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_EVENTO, // tabla
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

    public ArrayList<Evento> leerListaEventosPorCategoria(Context context, String idParametroCategoria) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        String sql = "SELECT * " +
                "FROM " + DataBaseContract.TABLE_CATEGORIA_EVENTO + " categoriaEvento " +
                "INNER JOIN " + DataBaseContract.TABLE_EVENTO + " evento " +
                "ON categoriaEvento." + DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO + " = evento." + DataBaseContract.TABLE_EVENTO_COLUMN_ID + " " +
                "WHERE categoriaEvento." + DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA + "= ?";
        String[] selectionArgs = {idParametroCategoria};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return generarLista(cursor);
    }
}
