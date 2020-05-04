package cobit19.ecci.ucr.ac.eventosucr;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Esta clase define lo que conforma un evento
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Evento implements Parcelable {

    private String id;
    private String nombre;
    private String institucion;
    private String detalles;
    private Calendar fecha;
    private String ubicacion;//Ubicacion escrita...200 mts oeste...
    //private double latitud;
    //private double longitud;
    private String horaInicio;
    private String horaFin;

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }



    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }



    public Evento() {
        fecha = Calendar.getInstance();
    }

    public Evento(String nombre, String institucion,String detalles, Calendar fecha, String horaInicio,String horaFin,String ubicacion) {
        this.nombre = nombre;
        this.detalles = detalles;
        this.ubicacion=ubicacion;
        this.institucion=institucion;
        this.fecha=fecha;
        this.horaInicio=horaInicio;
        this.horaFin=horaFin;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalles() {
        return detalles;
    }
    public void setDetalles(String dettalles) {
        this.detalles = dettalles;
    }



    protected Evento (Parcel in) {
        id = in.readString();
        nombre = in.readString();
        institucion=in.readString();
        detalles = in.readString();
        long milliseconds = in.readLong();
        String timezone_id = in.readString();
        fecha = new GregorianCalendar(TimeZone.getTimeZone(timezone_id));
        fecha.setTimeInMillis(milliseconds);
        //fecha.setTime(UtilDates.parsearaDate(in.readString()));
        horaInicio = in.readString();
        horaFin = in.readString();
        ubicacion=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(institucion);
        dest.writeString(detalles);
        dest.writeLong(fecha.getTimeInMillis());
        dest.writeString(fecha.getTimeZone().getID());
        //dest.writeString(UtilDates.parsearaString(fecha.getTime()));
        dest.writeString(horaInicio);
        dest.writeString(horaFin);
        dest.writeString(ubicacion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }
        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public long insertar(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de insertar
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo escritura
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();
        //values.put(DataBaseContract.TABLE_EVENTO_COLUMN_ID, this.id);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE, this.nombre);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION, this.institucion);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES, this.detalles);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA, UtilDates.parsearaString(this.fecha.getTime()));
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO, this.horaInicio);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN, this.horaFin);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION, this.ubicacion);


        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_EVENTO, null, values);
    }

    public ArrayList<Evento> leerEventos(Context context) {//Solo devuelve el nombre del evento

        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        ArrayList<Evento> nombresEventos=new ArrayList<Evento>();
        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DataBaseContract.TABLE_EVENTO_COLUMN_ID,
                DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE,
                DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION,
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
                DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN,
                DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES




        };

        // Filtro para el WHERE
        //String selection = DataBaseContract.TABLE_EVENTO_COLUMN_ID + " = ?";
        //String[] selectionArgs = {idParametro};

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

        if (cursor.getCount() > 0) {
            do{
                Evento eventoALeer=new Evento();
                eventoALeer.setId(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_ID)));
                //id = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_ID));
                eventoALeer.setNombre(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE)));
                //nombre = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE));
                eventoALeer.setInstitucion(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION)));
                //institucion = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION));
                eventoALeer.setHoraInicio(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO)));
                //horaInicio = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO));
                eventoALeer.setHoraFin(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN)));
                //horaFin = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN));
                eventoALeer.setDetalles(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES)));
                //detalles = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES));
                eventoALeer.setUbicacion(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION)));
                //ubicacion = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION));
                try {
                    eventoALeer.fecha.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //fecha = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA));


                //fecha = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA));

                //fecha = (UtilDates.StringToDate(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA))));

                //masInfo = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_MASINFO));
                nombresEventos.add(eventoALeer);
                eventoALeer=null;

            }while (cursor.moveToNext());
        }
        return nombresEventos;
    }


    public void leer(Context context, String idParametro) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DataBaseContract.TABLE_EVENTO_COLUMN_ID,
                DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE,
                DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION,
                DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES,
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN,
                DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION

        };

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

        if (cursor.getCount() > 0) {
            id = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_ID));
            nombre = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE));
            institucion = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION));
            detalles = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES));
            fecha.setTime(UtilDates.parsearaDate(
                    cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA))));
            horaInicio = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO));
            horaFin = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN));
            ubicacion= cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION));

        }
    }

    public void leerLista(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DataBaseContract.TABLE_EVENTO_COLUMN_ID,
                DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE,
                DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION,
                DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES,
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN,
                DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION

        };

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

        ArrayList<Evento> eventos = new ArrayList<Evento>();
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while(!cursor.isAfterLast()) {
                id = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_ID));
                nombre = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE));
                institucion = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION));
                detalles = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES));
                fecha.setTime(UtilDates.parsearaDate(
                        cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA))));
                horaInicio = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO));
                horaFin = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN));
                ubicacion= cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION));

                eventos.add(new Evento(nombre, institucion, detalles, fecha, horaInicio, horaFin, ubicacion));

                cursor.moveToNext();
            }
        }
    }

    public int actualizar(Context context){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(
                DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE, getNombre());
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_INSTITUCION, getInstitucion());
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES,
                getDetalles());

        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO, getHoraInicio());
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN, getHoraFin());
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
                UtilDates.parsearaString(this.getFecha().getTime()));
        values.put(
                DataBaseContract.TABLE_EVENTO_COLUMN_UBICACION, getUbicacion());

        // Criterio de actualizacion
        String selection = DataBaseContract.TABLE_EVENTO_COLUMN_ID + " LIKE ?";
        // Se detallan los argumentos
        String[] selectionArgs = {getId()};
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

    @Override
    public String toString() {
        return "Id: " + id + " Nombre: " + nombre +" Institucion: " + institucion + " Detalles: " + detalles +
                " Fecha: " + UtilDates.parsearaString(fecha.getTime()) +
                "HoraInicio: " + horaInicio +"HoraFin: " + horaFin+" Ubicacion: " + ubicacion ;

    }
}
