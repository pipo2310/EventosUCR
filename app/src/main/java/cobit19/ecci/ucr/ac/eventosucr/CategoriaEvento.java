package cobit19.ecci.ucr.ac.eventosucr;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class CategoriaEvento implements Parcelable {

    private String idCategoria;
    private String idEvento;

    public CategoriaEvento() {}

    public CategoriaEvento(String idCategoria, String idEvento) {
        this.idCategoria = idCategoria;
        this.idEvento = idEvento;
    }

    public String getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdEvento() {
        return idEvento;
    }
    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    protected CategoriaEvento (Parcel in) {
        idCategoria = in.readString();
        idEvento = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCategoria);
        dest.writeString(idEvento);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoriaEvento> CREATOR = new Creator<CategoriaEvento>() {
        @Override
        public CategoriaEvento createFromParcel(Parcel in) {
            return new CategoriaEvento(in);
        }
        @Override
        public CategoriaEvento[] newArray(int size) {
            return new CategoriaEvento[size];
        }
    };

    public long insertar(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de insertar
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo escritura
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();

        values.put(DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA, this.idCategoria);
        values.put(DataBaseContract.TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO, this.idEvento);

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_CATEGORIA_EVENTO, null, values);
    }

    @Override
    public String toString() {
        return "Id categoria: " + idCategoria + " Id evento: " + idEvento;
    }
}
