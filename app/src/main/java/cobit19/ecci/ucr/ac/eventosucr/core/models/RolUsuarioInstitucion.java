package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RolUsuarioInstitucion implements Parcelable {

    private String idRol;
    private String correoUcr;
    private String idInstitucion;

    public RolUsuarioInstitucion() {}
    public RolUsuarioInstitucion(String idRol, String correoUcr, String idInstitucion) {
        this.idRol = idRol;
        this.correoUcr = correoUcr;
        this.idInstitucion = idInstitucion;
    }

    public String getIdRol() {
        return idRol;
    }
    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getCorreoUcr() {
        return correoUcr;
    }
    public void setCorreoUcr(String correoUcr) {
        this.correoUcr = correoUcr;
    }

    public String getIdInstitucion() {
        return idInstitucion;
    }
    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    protected RolUsuarioInstitucion(Parcel in) {
        idRol = in.readString();
        correoUcr = in.readString();
        idInstitucion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idRol);
        dest.writeString(correoUcr);
        dest.writeString(idInstitucion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RolUsuarioInstitucion> CREATOR = new Creator<RolUsuarioInstitucion>() {
        @Override
        public RolUsuarioInstitucion createFromParcel(Parcel in) {
            return new RolUsuarioInstitucion(in);
        }
        @Override
        public RolUsuarioInstitucion[] newArray(int size) {
            return new RolUsuarioInstitucion[size];
        }
    };

    @Override
    public String toString() {
        return "RolUsuarioInstitucion{" +
                "idRol='" + idRol + '\'' +
                ", correoUcr='" + correoUcr + '\'' +
                ", idInstitucion='" + idInstitucion + '\'' +
                '}';
    }
}
