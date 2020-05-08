package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import cobit19.ecci.ucr.ac.eventosucr.DataBaseContract;
import cobit19.ecci.ucr.ac.eventosucr.DataBaseHelper;

public class Categoria implements Parcelable {

    private String id;
    private String nombre;
    private String detalles;
    private Bitmap imagen;

    public Categoria() {}

    public Categoria(String nombre, String detalles, Bitmap imagen) {
        this.nombre = nombre;
        this.detalles = detalles;
        this.imagen = imagen;
    }

    public Categoria(String id, String nombre, String detalles, Bitmap imagen) {
        this.id = id;
        this.nombre = nombre;
        this.detalles = detalles;
        this.imagen = imagen;
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
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    protected Categoria (Parcel in) {
        id = in.readString();
        nombre = in.readString();
        detalles = in.readString();
        imagen = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(detalles);
        dest.writeParcelable(imagen, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }
        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };




    @Override
    public String toString() {
        return "Categoria{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", detalles='" + detalles + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}