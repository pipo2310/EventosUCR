package cobit19.ecci.ucr.ac.eventosucr.room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categoria")
public class Categoria implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "categoria")
    private String categoria;

    public Categoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    protected Categoria (Parcel in) {
        categoria = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoria);
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
}
