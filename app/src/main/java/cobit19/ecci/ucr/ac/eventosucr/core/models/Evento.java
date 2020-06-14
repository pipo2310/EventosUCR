package cobit19.ecci.ucr.ac.eventosucr.core.models;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Esta clase define lo que conforma un evento
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import cobit19.ecci.ucr.ac.eventosucr.shared.UtilDates;

public class Evento implements Parcelable {

    private String nombre;
    private String organizador;
    private String detalles;
    private Calendar fecha = Calendar.getInstance();
    private String ubicacion;//Ubicacion escrita...200 mts oeste...
    private double latitud;
    private double longitud;
    private String horaInicio;
    private String horaFin;
    private String urlImagen;
    private String imagenUltimaModificacion;
    private List<String> categorias = new ArrayList<>();
    private List<String> usuariosInteresados = new ArrayList<>();



    private Long timestamp;

    public Evento() { }

    public Evento(String nombre, String organizador, String detalles, Calendar fecha, String horaInicio, String horaFin, String ubicacion, double latitud, double longitud,String urlImagen, List<String> categorias) {
        this.nombre = nombre;
        this.organizador = organizador;
        this.detalles = detalles;
        this.ubicacion=ubicacion;
        this.fecha=fecha;
        this.horaInicio=horaInicio;
        this.horaFin=horaFin;
        this.latitud=latitud;
        this.longitud=longitud;
        this.urlImagen=urlImagen;
        this.categorias = categorias;
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
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Exclude
    public Calendar getFecha() {
        return fecha;
    }
    @Exclude
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }


    public Timestamp getTimestamp() {
        return new Timestamp(fecha.getTime());
    }
    public void setTimestamp(Timestamp timestamp) {
        fecha.setTime(timestamp.toDate());
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

    public String getOrganizador() {
        return organizador;
    }
    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getImagenUltimaModificacion() {
        return imagenUltimaModificacion;
    }
    public void setImagenUltimaModificacion(String imagenUltimaModificacion) {
        this.imagenUltimaModificacion = imagenUltimaModificacion;
    }

    public List<String> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public List<String> getUsuariosInteresados() {
        return usuariosInteresados;
    }
    public void setUsuariosInteresados(List<String> usuariosInteresados) {
        this.usuariosInteresados = usuariosInteresados;
    }

    protected Evento (Parcel in) {
        nombre = in.readString();
        organizador = in.readString();
        detalles = in.readString();
        // Proceso para agregar la fecha
        long milliseconds = in.readLong();
        String timezone_id = in.readString();
        fecha = new GregorianCalendar(TimeZone.getTimeZone(timezone_id));
        fecha.setTimeInMillis(milliseconds);

        horaInicio = in.readString();
        horaFin = in.readString();
        ubicacion=in.readString();
        latitud=in.readDouble();
        longitud=in.readDouble();
        urlImagen=in.readString();
        imagenUltimaModificacion = in.readString();

        categorias=in.createStringArrayList();
        usuariosInteresados=in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(organizador);
        dest.writeString(detalles);
        dest.writeLong(fecha.getTimeInMillis());
        dest.writeString(fecha.getTimeZone().getID());
        dest.writeString(horaInicio);
        dest.writeString(horaFin);
        dest.writeString(ubicacion);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeString(urlImagen);
        dest.writeString(imagenUltimaModificacion);
        dest.writeStringList(categorias);
        dest.writeStringList(usuariosInteresados);
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
        return "Nombre: " + nombre + " Detalles: " + detalles +
                " Fecha: " + UtilDates.parsearaString(fecha.getTime()) +
                "HoraInicio: " + horaInicio +"HoraFin: " + horaFin+" Ubicacion: " + ubicacion +
                "Latitud: " + latitud + " Longitud: " + longitud;

    }
}
