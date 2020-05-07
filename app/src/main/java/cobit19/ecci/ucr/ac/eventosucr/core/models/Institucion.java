package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Institucion implements Parcelable {

    private String id;
    private String nombre;
    private Bitmap logo;

    protected Institucion(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        logo = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Institucion> CREATOR = new Creator<Institucion>() {
        @Override
        public Institucion createFromParcel(Parcel in) {
            return new Institucion(in);
        }

        @Override
        public Institucion[] newArray(int size) {
            return new Institucion[size];
        }
    };

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

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public Institucion(){}

    public Institucion(String id, String nombre, Bitmap logo){
        this.id = id;
        this.nombre = nombre;
        this.logo = logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeParcelable(logo, flags);
    }

    @Override
    public String toString() {
        return "Institucion{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", logo=" + logo +
                '}';
    }
}
