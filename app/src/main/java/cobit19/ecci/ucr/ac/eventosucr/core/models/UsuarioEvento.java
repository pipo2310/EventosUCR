package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioEvento implements Parcelable {

    private String correoUcr;
    private String idEvento;

    public UsuarioEvento() {}
    public UsuarioEvento(String correoUcr, String idEvento) {
        this.correoUcr = correoUcr;
        this.idEvento = idEvento;
    }

    public String getCorreoUcr() {
        return correoUcr;
    }
    public void setCorreoUcr(String correoUcr) {
        this.correoUcr = correoUcr;
    }

    public String getIdEvento() {
        return idEvento;
    }
    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    protected UsuarioEvento(Parcel in) {
        correoUcr = in.readString();
        idEvento = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(correoUcr);
        dest.writeString(idEvento);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UsuarioEvento> CREATOR = new Creator<UsuarioEvento>() {
        @Override
        public UsuarioEvento createFromParcel(Parcel in) {
            return new UsuarioEvento(in);
        }
        @Override
        public UsuarioEvento[] newArray(int size) {
            return new UsuarioEvento[size];
        }
    };

    @Override
    public String toString() {
        return "UsuarioEvento{" +
                "correoUcr='" + correoUcr + '\'' +
                ", idEvento='" + idEvento + '\'' +
                '}';
    }
}
