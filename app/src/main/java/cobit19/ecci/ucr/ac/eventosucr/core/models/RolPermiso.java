package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RolPermiso implements Parcelable {

    private String idRol;
    private String idPermiso;

    public RolPermiso() {}
    public RolPermiso(String idRol, String idPermiso) {
        this.idRol = idRol;
        this.idPermiso = idPermiso;
    }

    public String getIdRol() {
        return idRol;
    }
    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getIdPermiso() {
        return idPermiso;
    }
    public void setIdPermiso(String idPermiso) {
        this.idPermiso = idPermiso;
    }

    protected RolPermiso (Parcel in) {
        idRol = in.readString();
        idPermiso = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idRol);
        dest.writeString(idPermiso);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RolPermiso> CREATOR = new Creator<RolPermiso>() {
        @Override
        public RolPermiso createFromParcel(Parcel in) {
            return new RolPermiso(in);
        }
        @Override
        public RolPermiso[] newArray(int size) {
            return new RolPermiso[size];
        }
    };

    @Override
    public String toString() {
        return "RolPermiso{" +
                "idRol='" + idRol + '\'' +
                ", idPermiso='" + idPermiso + '\'' +
                '}';
    }
}
