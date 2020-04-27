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

import java.util.Calendar;
import java.util.Date;

public class Evento implements Parcelable {

    private String id;
    private String nombre;
    private String detalles;
    private Calendar fecha;
    private String horaInicio;
    private String horaFin;

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

    public Evento(String id, String nombre, String dettalles, Calendar fecha, String horaInicio,String horaFin) {
        this.id = id;
        this.nombre = nombre;
        this.detalles = dettalles;
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
        detalles = in.readString();
        fecha.setTime(UtilDates.parsearaDate(in.readString()));
        horaInicio = in.readString();
        horaFin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(detalles);
        dest.writeString(UtilDates.parsearaString(fecha.getTime()));
        dest.writeString(horaInicio);
        dest.writeString(horaFin);
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

        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE, this.nombre);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES, this.detalles);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA, UtilDates.parsearaString(this.fecha.getTime()));
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_NOMBRE, this.horaInicio);
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES, this.horaFin);


        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_EVENTO, null, values);
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
                DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES,
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHA,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO,
                DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN

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
            detalles = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_DETALLES));
            fecha.setTime(UtilDates.parsearaDate(
                    cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHA))));
            horaInicio = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAINICIO));
            horaFin = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_HORAFIN));

        }
    }

    @Override
    public String toString() {
        return "Id: " + id + " Nombre: " + nombre + " Detalles: " + detalles +
                " Fecha: " + UtilDates.parsearaString(fecha.getTime()) +
                "HoraInicio: " + horaInicio +"HoraFin: " + horaFin;

    }
}
