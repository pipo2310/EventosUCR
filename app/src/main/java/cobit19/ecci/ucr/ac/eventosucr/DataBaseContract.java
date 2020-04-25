package cobit19.ecci.ucr.ac.eventosucr;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Esta clase define el esquema de la base de datos de la app
 * Esta clase debe usarse en toda la app
 */

public final class DataBaseContract {
    // Tabla Evento
    public static final String TABLE_EVENTO = "Evento";
    public static final String TABLE_EVENTO_COLUMN_ID = "id";
    public static final String TABLE_EVENTO_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_EVENTO_COLUMN_DETALLES = "detalles";
    public static final String TABLE_EVENTO_COLUMN_FECHAINICIO = "fechaInicio";
    public static final String TABLE_EVENTO_COLUMN_FECHAFIN = "fechaFin";

    //Tabla Categoria
    public static final String TABLE_CATEGORIA = "Categoria";
    public static final String TABLE_CATEGORIA_COLUMN_ID = "id";
    public static final String TABLE_CATEGORIA_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_CATEGORIA_COLUMN_DETALLES = "detalles";

    //Table Relacion del Evento con una Categoria
    public static final String TABLE_CATEGORIA_EVENTO = "CategoriaEvento";
    public static final String TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA = "idCategoria";
    public static final String TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO = "idEvento";

    private DataBaseContract() {}

    public static final String SQL_CREATE_EVENTO = "CREATE TABLE " + TABLE_EVENTO + " (" +
            TABLE_EVENTO_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_EVENTO_COLUMN_NOMBRE +" TEXT," +
            TABLE_EVENTO_COLUMN_DETALLES + " TEXT," +
            TABLE_EVENTO_COLUMN_FECHAINICIO + " TEXT," +
            TABLE_EVENTO_COLUMN_FECHAFIN + " TEXT" +
            ");";

    public static final String SQL_DELETE_EVENTO = "DROP TABLE IF EXISTS " + TABLE_EVENTO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_EVENTO + "';";

    public static final String SQL_CREATE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + " (" +
            TABLE_CATEGORIA_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_CATEGORIA_COLUMN_NOMBRE + " TEXT," +
            TABLE_CATEGORIA_COLUMN_DETALLES + " TEXT" +
            ");";

    public static final String SQL_DELETE_CATEGORIA = "DROP TABLE IF EXIST " + TABLE_CATEGORIA + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_CATEGORIA + "';";

    public static final String SQL_CREATE_CATEGORIA_EVENTO = "CREATE TABLE " + TABLE_CATEGORIA_EVENTO + " (" +
            TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA + " INTEGER," +
            TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO + " INTEGER," +
            "PRIMARY KEY (" + TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA + "," + TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO + ")" +
            ");";

    public static final String SQL_DELETE_CATEGORIA_EVENTO = "DROP TABLE IF EXIST " + TABLE_CATEGORIA_EVENTO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_CATEGORIA_EVENTO + "';";
}
