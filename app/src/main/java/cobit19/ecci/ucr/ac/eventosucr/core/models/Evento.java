package cobit19.ecci.ucr.ac.eventosucr.core.models;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Esta clase define lo que conforma un evento
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.core.services.InstitucionService;

public class Evento implements Parcelable {

    private InstitucionService institucionService = new InstitucionService();

    private String id;
    private String nombre;
    private String idInstitucion;
    private String detalles;
    private Calendar fecha = Calendar.getInstance();
    private String ubicacion;//Ubicacion escrita...200 mts oeste...
    private double latitud;
    private double longitud;
    private String horaInicio;
    private String horaFin;

    public Evento() { }

    public Evento(String nombre, String idInstitucion, String detalles, Calendar fecha, String horaInicio, String horaFin, String ubicacion, double latitud, double longitud) {
        this.nombre = nombre;
        this.detalles = detalles;
        this.ubicacion=ubicacion;
        this.idInstitucion=idInstitucion;
        this.fecha=fecha;
        this.horaInicio=horaInicio;
        this.horaFin=horaFin;
        this.latitud=latitud;
        this.longitud=longitud;
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

    public String getIdInstitucion() {
        return idInstitucion;
    }
    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Institucion getInstitucion(Context context) {
        return institucionService.leer(context, idInstitucion);
    }

    public String getDetalles() {
        return detalles;
    }
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Calendar getFecha() {
        return fecha;
    }
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getLatitud() {
        return latitud;
    }
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }
    public void setLongitud(double longitud) {
        this.longitud = longitud;
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

    protected Evento (Parcel in) {
        id = in.readString();
        nombre = in.readString();
        idInstitucion=in.readString();
        detalles = in.readString();
        long milliseconds = in.readLong();
        String timezone_id = in.readString();
        fecha = new GregorianCalendar(TimeZone.getTimeZone(timezone_id));
        fecha.setTimeInMillis(milliseconds);
        //fecha.setTime(UtilDates.parsearaDate(in.readString()));
        horaInicio = in.readString();
        horaFin = in.readString();
        ubicacion=in.readString();
        latitud=in.readDouble();
        longitud=in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(idInstitucion);
        dest.writeString(detalles);
        dest.writeLong(fecha.getTimeInMillis());
        dest.writeString(fecha.getTimeZone().getID());
        //dest.writeString(UtilDates.parsearaString(fecha.getTime()));
        dest.writeString(horaInicio);
        dest.writeString(horaFin);
        dest.writeString(ubicacion);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
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

    @Override
    public String toString() {
        return "Id: " + id + " Nombre: " + nombre +" Institucion: " + idInstitucion + " Detalles: " + detalles +
                " Fecha: " + UtilDates.parsearaString(fecha.getTime()) +
                "HoraInicio: " + horaInicio +"HoraFin: " + horaFin+" Ubicacion: " + ubicacion +
                "Latitud: " + latitud + " Longitud: " + longitud;

    }
}
