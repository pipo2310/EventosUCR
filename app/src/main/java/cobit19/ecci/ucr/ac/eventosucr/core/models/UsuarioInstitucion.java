package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioInstitucion implements Parcelable {

    private String idInstituicion;
    private String correoUcr;

    public UsuarioInstitucion(String idInstituicion, String correoUcr) {
        this.idInstituicion = idInstituicion;
        this.correoUcr = correoUcr;
    }

    public UsuarioInstitucion(){}

    protected UsuarioInstitucion(Parcel in) {
        idInstituicion = in.readString();
        correoUcr = in.readString();
    }

    public static final Creator<UsuarioInstitucion> CREATOR = new Creator<UsuarioInstitucion>() {
        @Override
        public UsuarioInstitucion createFromParcel(Parcel in) {
            return new UsuarioInstitucion(in);
        }

        @Override
        public UsuarioInstitucion[] newArray(int size) {
            return new UsuarioInstitucion[size];
        }
    };

    public String getIdInstituicion() {
        return idInstituicion;
    }

    public void setIdInstituicion(String idInstituicion) {
        this.idInstituicion = idInstituicion;
    }

    public String getCorreoUcr() {
        return correoUcr;
    }

    public void setCorreoUcr(String correoUcr) {
        this.correoUcr = correoUcr;
    }

    @Override
    public String toString() {
        return "UsuarioInstitucion{" +
                "idInstituicion='" + idInstituicion + '\'' +
                ", correoUcr='" + correoUcr + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idInstituicion);
        dest.writeString(correoUcr);
    }
}
