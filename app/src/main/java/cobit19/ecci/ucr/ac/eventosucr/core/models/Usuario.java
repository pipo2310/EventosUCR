package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Usuario implements Parcelable {

    private String correoUcr;
    private String nombre;
    private String contrasenia;
    private String apellido1;
    private String apellido2;

    public Usuario() {}
    public Usuario(String correoUcr, String nombre, String contrasenia, String apellido1, String apellido2) {
        this.correoUcr = correoUcr;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public String getCorreoUcr() {
        return correoUcr;
    }
    public void setCorreoUcr(String correoUcr) {
        this.correoUcr = correoUcr;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getApellido1() {
        return apellido1;
    }
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    protected Usuario(Parcel in) {
        correoUcr = in.readString();
        nombre = in.readString();
        contrasenia = in.readString();
        apellido1 = in.readString();
        apellido2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(correoUcr);
        dest.writeString(nombre);
        dest.writeString(contrasenia);
        dest.writeString(apellido1);
        dest.writeString(apellido2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }
        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public String toString() {
        return "Usuario{" +
                "correoUcr='" + correoUcr + '\'' +
                ", nombre='" + nombre + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                '}';
    }
}
