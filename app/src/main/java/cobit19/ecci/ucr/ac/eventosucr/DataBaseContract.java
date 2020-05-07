package cobit19.ecci.ucr.ac.eventosucr;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Christian on 27/4/2020.
 * Esta clase define el esquema de la base de datos de la app
 * Esta clase debe usarse en toda la app
 */

public final class DataBaseContract {
    // Tabla Evento
    public static final String TABLE_EVENTO = "Evento";
    public static final String TABLE_EVENTO_COLUMN_ID = "id";
    public static final String TABLE_EVENTO_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_EVENTO_COLUMN_ID_INSTITUCION = "idInstitucion";
    public static final String TABLE_EVENTO_COLUMN_DETALLES = "detalles";
    public static final String TABLE_EVENTO_COLUMN_FECHA = "fecha";
    public static final String TABLE_EVENTO_COLUMN_HORAINICIO = "horaInicio";
    public static final String TABLE_EVENTO_COLUMN_HORAFIN = "horaFin";
    public static final String TABLE_EVENTO_COLUMN_UBICACION = "ubicacion";
    public static final String TABLE_EVENTO_COLUMN_LATITUD = "latitud";
    public static final String TABLE_EVENTO_COLUMN_LONGITUD = "longitud";


    //Tabla Categoria
    public static final String TABLE_CATEGORIA = "Categoria";
    public static final String TABLE_CATEGORIA_COLUMN_ID = "id";
    public static final String TABLE_CATEGORIA_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_CATEGORIA_COLUMN_DETALLES = "detalles";
    public static final String TABLE_CATEGORIA_COLUMN_IMAGEN = "imagen";

    //Table Relacion del Evento con una Categoria
    public static final String TABLE_CATEGORIA_EVENTO = "CategoriaEvento";
    public static final String TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA = "idCategoria";
    public static final String TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO = "idEvento";

    //Tabla Institucion
    public static final String TABLE_INSTITUCION = "Institucion";
    public static final String TABLE_INSTITUCION_COLUMN_ID = "id";
    public static final String TABLE_INSTITUCION_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_INSTITUCION_COLUMN_LOGO = "logo";

    //Tabla UsuarioInstitucion
    public static final String TABLE_USUARIO_INSTITUCION = "UsuarioInstitucion";
    public static final String TABLE_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO = "correoUcr";
    public static final String TABLE_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION = "idInstitucion";

    //Tabla ImagenEvento
    public static final String TABLE_IMAGEN_EVENTO = "ImagenEvento";
    public static final String TABLE_IMAGEN_EVENTO_COLUMN_ID = "id";
    public static final String TABLE_IMAGEN_EVENTO_COLUMN_ID_EVENTO = "idEvento";
    public static final String TABLE_IMAGEN_EVENTO_COLUMN_IMAGEN = "imagen";

    // Comandos utilizados para referenciar llaves foráneas
    //public static final String FOREIGN_KEY = "FOREIGN KEY (";

    private DataBaseContract() {}

    public static final String SQL_CREATE_EVENTO = "CREATE TABLE " + TABLE_EVENTO + " (" +
            TABLE_EVENTO_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_EVENTO_COLUMN_NOMBRE +" TEXT," +
            TABLE_EVENTO_COLUMN_ID_INSTITUCION +" INTEGER," +
            TABLE_EVENTO_COLUMN_DETALLES + " TEXT," +
            TABLE_EVENTO_COLUMN_FECHA + " TEXT," +
            TABLE_EVENTO_COLUMN_HORAINICIO + " TEXT," +
            TABLE_EVENTO_COLUMN_HORAFIN + " TEXT," +
            TABLE_EVENTO_COLUMN_UBICACION + " TEXT," +
            TABLE_EVENTO_COLUMN_LATITUD + " REAL," +
            TABLE_EVENTO_COLUMN_LONGITUD + " REAL," +
            "FOREIGN KEY (" + TABLE_EVENTO_COLUMN_ID_INSTITUCION + ") REFERENCES " +
            TABLE_INSTITUCION + " (" + TABLE_INSTITUCION_COLUMN_ID + ")" +
            ");";

    public static final String SQL_DELETE_EVENTO = "DROP TABLE IF EXISTS " + TABLE_EVENTO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_EVENTO + "';";

    public static final String SQL_CREATE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + " (" +
            TABLE_CATEGORIA_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_CATEGORIA_COLUMN_NOMBRE + " TEXT," +
            TABLE_CATEGORIA_COLUMN_DETALLES + " TEXT," +
            TABLE_CATEGORIA_COLUMN_IMAGEN + " BLOB" +
            ");";

    public static final String SQL_DELETE_CATEGORIA = "DROP TABLE IF EXISTS " + TABLE_CATEGORIA + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_CATEGORIA + "';";

    public static final String SQL_CREATE_CATEGORIA_EVENTO = "CREATE TABLE " + TABLE_CATEGORIA_EVENTO + " (" +
            TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA + " INTEGER," +
            TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO + " INTEGER," +
            "PRIMARY KEY (" + TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA + "," + TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO + ")" +
            ");";

    public static final String SQL_DELETE_CATEGORIA_EVENTO = "DROP TABLE IF EXISTS " + TABLE_CATEGORIA_EVENTO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_CATEGORIA_EVENTO + "';";


    public static final String SQL_CREATE_INSTITUCION = "CREATE TABLE " + TABLE_INSTITUCION + " (" +
            TABLE_INSTITUCION_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_INSTITUCION_COLUMN_NOMBRE + " TEXT," +
            TABLE_INSTITUCION_COLUMN_LOGO + " BLOB" +
            ");";

    public static final String SQL_DELETE_INSTITUCION = "DROP TABLE IF EXISTS " + TABLE_INSTITUCION + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_INSTITUCION + "';";

    // DESCOMENTAR CUANDO SE INSERTE LA TABLA USUARIO
    public static final String SQL_CREATE_USUARIO_INSTITUCION = "CREATE TABLE " + TABLE_USUARIO_INSTITUCION + " (" +
            TABLE_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + " TEXT," +
            TABLE_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + " INTEGER," +
            "PRIMARY KEY (" + TABLE_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + "," +
            TABLE_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + "), " +
            //"FOREIGN KEY (" + TABLE_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + ") REFERENCES " +
            //TABLE_USUARIO + " (" + TABLE_USUARIO_CORREO_UCR + ")," +
            "FOREIGN KEY (" + TABLE_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + ") REFERENCES " +
            TABLE_INSTITUCION + " (" + TABLE_INSTITUCION_COLUMN_ID + ")" +
            ");";

    public static final String SQL_DELETE_USUARIO_INSTITUCION = "DROP TABLE IF EXISTS " + TABLE_USUARIO_INSTITUCION + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_USUARIO_INSTITUCION + "';";

    public static final String SQL_CREATE_IMAGEN_EVENTO = "CREATE TABLE " + TABLE_IMAGEN_EVENTO + " (" +
            TABLE_IMAGEN_EVENTO_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_IMAGEN_EVENTO_COLUMN_ID_EVENTO + " INTEGER," +
            TABLE_IMAGEN_EVENTO_COLUMN_IMAGEN + " BLOB," +
            "FOREIGN KEY (" + TABLE_IMAGEN_EVENTO_COLUMN_ID_EVENTO + ") REFERENCES " +
            TABLE_EVENTO + " (" + TABLE_EVENTO_COLUMN_ID + ")" +
            ");";

    public static final String SQL_DELETE_IMAGEN_EVENTO = "DROP TABLE IF EXISTS " + TABLE_IMAGEN_EVENTO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_IMAGEN_EVENTO + "';";
}
