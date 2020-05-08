package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Permiso implements Parcelable {

    private String id;
    private String nombre;
    private String detalles;

    public Permiso() {}
    public Permiso(String nombre, String detalles) {
        this.nombre = nombre;
        this.detalles = detalles;
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

    protected Permiso (Parcel in) {
        id = in.readString();
        nombre = in.readString();
        detalles = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(detalles);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Permiso> CREATOR = new Creator<Permiso>() {
        @Override
        public Permiso createFromParcel(Parcel in) {
            return new Permiso(in);
        }
        @Override
        public Permiso[] newArray(int size) {
            return new Permiso[size];
        }
    };

    @Override
    public String toString() {
        return "Permiso{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", detalles='" + detalles + '\'' +
                '}';
    }
}
