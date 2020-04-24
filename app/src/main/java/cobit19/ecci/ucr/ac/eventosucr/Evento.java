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
    private Calendar fechaInicio;
    private Calendar fechaFin;

    public Evento() {
        fechaInicio = Calendar.getInstance();
        fechaFin = Calendar.getInstance();
    }

    public Evento(String id, String nombre, String dettalles, Calendar fechaInicio, Calendar fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.detalles = dettalles;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public Calendar getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }

    protected Evento (Parcel in) {
        id = in.readString();
        nombre = in.readString();
        detalles = in.readString();
        fechaInicio.setTime(UtilDates.parsearaDate(in.readString()));
        fechaFin.setTime(UtilDates.parsearaDate(in.readString()));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(detalles);
        dest.writeString(UtilDates.parsearaString(fechaInicio.getTime()));
        dest.writeString(UtilDates.parsearaString(fechaFin.getTime()));
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
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_FECHAINICIO, UtilDates.parsearaString(this.fechaInicio.getTime()));
        values.put(DataBaseContract.TABLE_EVENTO_COLUMN_FECHAFIN, UtilDates.parsearaString(this.fechaFin.getTime()));

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
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHAINICIO,
                DataBaseContract.TABLE_EVENTO_COLUMN_FECHAFIN
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
            fechaInicio.setTime(UtilDates.parsearaDate(
                    cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHAINICIO))));
            fechaFin.setTime(UtilDates.parsearaDate(
                    cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_EVENTO_COLUMN_FECHAFIN))));
        }
    }

    @Override
    public String toString() {
        return "Id: " + id + " Nombre: " + nombre + " Detalles: " + detalles +
                " Fecha inicio: " + UtilDates.parsearaString(fechaInicio.getTime()) +
                " Fecha fin: " + UtilDates.parsearaString(fechaFin.getTime());
    }
}
