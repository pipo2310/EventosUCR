package cobit19.ecci.ucr.ac.eventosucr.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            DataBaseContract.TABLE_EVENTO_COLUMN_ID_INSTITUCION,
            DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES,
            DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
            DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO,
            DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN,
            DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION,
            DataBaseContract.TABLE_EVENTO_COLUMN_LATITUD,
            DataBaseContract.TABLE_EVENTO_COLUMN_LONGITUD

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
            evento.setIdInstitucion(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_ID_INSTITUCION)));
            evento.setDetalles(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES)));
            evento.setFecha(fecha);
            evento.setHoraInicio(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO)));
            evento.setHoraFin(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN)));
            evento.setUbicacion(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION)));
            evento.setLatitud(cursor.getDouble(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_LATITUD)));
            evento.setLongitud(cursor.getDouble(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_LONGITUD)));
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
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_ID_INSTITUCION, evento.getIdInstitucion());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES, evento.getDetalles());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA, UtilDates.parsearaString(evento.getFecha().getTime()));
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO, evento.getHoraInicio());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN, evento.getHoraFin());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION, evento.getUbicacion());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_LATITUD, evento.getLatitud());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_LONGITUD, evento.getLongitud());


        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_EVENTO, null, values);

        /* -- Como insertar Eventos a firebase -- */
        // Access a Cloud Firestore instance from your Activity
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("eventosUCR").document("eventos").set(evento);
//        return 1;
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

        /* -- Como leer eventos de direbase -- */
        // Access a Cloud Firestore instance from your Activity
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        DocumentReference docRef = db.collection("eventosUCR").document("eventos");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Evento evento = documentSnapshot.toObject(Evento.class);
//                System.out.println(evento.toString());
//            }
//        });
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

    public ArrayList<Evento> leetListaEventosCuyoNombreContiene(Context context, String nombreContiene) {
        SQLiteDatabase db = getSQLiteDatabase(context);

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE + " LIKE ?";
        String[] selectionArgs = {"%" + nombreContiene + "%"};

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
        return generarLista(cursor);
    }

    public int actualizar(Context context, Evento evento){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(
                DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE, evento.getNombre());
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_ID_INSTITUCION, evento.getIdInstitucion());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES,
                evento.getDetalles());

        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO, evento.getHoraInicio());
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN, evento.getHoraFin());
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
                UtilDates.parsearaString(evento.getFecha().getTime()));
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION, evento.getUbicacion());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_LATITUD, evento.getLatitud());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_LONGITUD, evento.getLongitud());

        // Criterio de actualizacion
        String selection = DataBaseContract.TABLE_EVENTO_COLUMN_ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {evento.getId()};
        // Actualizar la base de datos
        return db.update(DataBaseContract.TABLE_EVENTO, values,
                selection, selectionArgs);
    }

    public void eliminar (Context context, String identificacion){
        // usar la clase DataBaseHelper para realizar la operacion de eliminar
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo escritura
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        // Define el where para el borrado
        String selection = DataBaseContract.TABLE_EVENTO_COLUMN_ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {identificacion};
        // Realiza el SQL de borrado
        db.delete(DataBaseContract.TABLE_EVENTO, selection,
                selectionArgs);
    }
}
