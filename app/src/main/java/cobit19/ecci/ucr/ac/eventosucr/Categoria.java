package cobit19.ecci.ucr.ac.eventosucr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class Categoria implements Parcelable {

    private String id;
    private String nombre;
    private String detalles;

    public Categoria() {}

    public Categoria(String id, String nombre, String detalles) {
        this.id = id;
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

    protected Categoria (Parcel in) {
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

    public long insertar(Context context) {
        // usar la clase DataBaseHelper para realizar la operacion de insertar
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        // Obtiene la base de datos en modo escritura
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        // Crear un mapa de valores donde las columnas son las llaves
        ContentValues values = new ContentValues();

        values.put(DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE, this.nombre);
        values.put(DataBaseContract.TABLE_CATEGORIA_COLUMN_DETALLES, this.detalles);

        // Insertar la nueva fila
        return db.insert(DataBaseContract.TABLE_CATEGORIA, null, values);
    }

    public void leer(Context context, String idParametro) {
        // usar la clase DataBaseHelper para realizar la operacion de leer
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        // Obtiene la base de datos en modo lectura
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        // Define cuales columnas quiere solicitar // en este caso todas las de la clase
        String[] projection = {
                DataBaseContract.TABLE_CATEGORIA_COLUMN_ID,
                DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE,
                DataBaseContract.TABLE_CATEGORIA_COLUMN_DETALLES
        };

        // Filtro para el WHERE
        String selection = DataBaseContract.TABLE_CATEGORIA_COLUMN_ID + " = ?";
        String[] selectionArgs = {idParametro};

        // Resultados en el cursor
        Cursor cursor = db.query(
                DataBaseContract.TABLE_CATEGORIA, // tabla
                projection, // columnas
                selection, // where
                selectionArgs, // valores del where
                null, // agrupamiento
                null, // filtros por grupo
                null // orden
        );

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            id = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_COLUMN_ID));
            nombre = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_COLUMN_NOMBRE));
            detalles = cursor.getString(cursor.getColumnIndex(DataBaseContract.TABLE_CATEGORIA_COLUMN_DETALLES));
        }
    }

    @Override
    public String toString() {
        return "Id: " + id + " Nombre: " + nombre + " Detalles: " + detalles;
    }
}
