package cobit19.ecci.ucr.ac.eventosucr.core.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Imagen implements Parcelable {

    private String id;
    private String idEvento;
    private Bitmap imagen;

    public Imagen(){

    }

    public Imagen(String id, String idEvento, Bitmap imagen){
        this.id = id;
        this.idEvento = idEvento;
        this.imagen = imagen;
    }

    protected Imagen(Parcel in) {
        id = in.readString();
        idEvento = in.readString();
        imagen = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Imagen> CREATOR = new Creator<Imagen>() {
        @Override
        public Imagen createFromParcel(Parcel in) {
            return new Imagen(in);
        }

        @Override
        public Imagen[] newArray(int size) {
            return new Imagen[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idEvento);
        dest.writeParcelable(imagen, flags);
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id='" + id + '\'' +
                ", idEvento='" + idEvento + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}
