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
    public static final String TABLE_EVENTO_COLUMN_INSTITUCION = "institucion";
    public static final String TABLE_EVENTO_COLUMN_DETALLES = "detalles";
    public static final String TABLE_EVENTO_COLUMN_FECHA = "fecha";
    public static final String TABLE_EVENTO_COLUMN_HORAINICIO = "horaInicio";
    public static final String TABLE_EVENTO_COLUMN_HORAFIN = "horaFin";
    public static final String TABLE_EVENTO_COLUMN_UBICACION = "ubicacion";

    //Tabla Categoria
    public static final String TABLE_CATEGORIA = "Categoria";
    public static final String TABLE_CATEGORIA_COLUMN_ID = "id";
    public static final String TABLE_CATEGORIA_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_CATEGORIA_COLUMN_DETALLES = "detalles";

    //Table Relacion del Evento con una Categoria
    public static final String TABLE_CATEGORIA_EVENTO = "CategoriaEvento";
    public static final String TABLE_CATEGORIA_EVENTO_COLUMN_ID_CATEGORIA = "idCategoria";
    public static final String TABLE_CATEGORIA_EVENTO_COLUMN_ID_EVENTO = "idEvento";

    //Tabla Permisos
    public static final String TABLE_PERMISO = "Permiso";
    public static final String TABLE_PERMISO_COLUMN_ID = "id";
    public static final String TABLE_PERMISO_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_PERMISO_COLUMN_DETALLES = "detalles";

    //Tabla Permisos
    public static final String TABLE_ROL = "Rol";
    public static final String TABLE_ROL_COLUMN_ID = "id";
    public static final String TABLE_ROL_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_ROL_COLUMN_DETALLES = "detalles";

    //Tabla Ralacion del Permiso con un Rol
    public static final String TABLE_ROL_PERMISO = "RolPermiso";
    public static final String TABLE_ROL_PERMISO_COLUMN_ID_ROL = "idRol";
    public static final String TABLE_ROL_PERMISO_COLUMN_ID_PERMISO = "idPermiso";

    //Tabla Usuario
    public static final String TABLE_USUARIO = "RolPermiso";
    public static final String TABLE_USUARIO_COLUMN_CORREO_UCR = "correoUcr";
    public static final String TABLE_USUARIO_COLUMN_NOMBRE = "nombre";
    public static final String TABLE_USUARIO_COLUMN_CONTRASENIA = "contrasenia";
    public static final String TABLE_USUARIO_COLUMN_APELLIDO1 = "apellido1";
    public static final String TABLE_USUARIO_COLUMN_APELLIDO2 = "apellido2";

    //Table Relacion del Usuario con los eventos que le gustan
    public static final String TABLE_USUARIO_EVENTO = "UsuarioEvento";
    public static final String TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO = "correoUcr";
    public static final String TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO = "idEvento";

    // Tabla Relacion del Rol, Usuario e Institucion
    public static final String TABLE_ROL_USUARIO_INSTITUCION = "ROlUsuarioInstitucion";
    public static final String TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL = "idRol";
    public static final String TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO = "correoUcr";
    public static final String TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION = "idInstitucion";

    private DataBaseContract() {}

    public static final String SQL_CREATE_EVENTO = "CREATE TABLE " + TABLE_EVENTO + " (" +
            TABLE_EVENTO_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_EVENTO_COLUMN_NOMBRE +" TEXT," +
            TABLE_EVENTO_COLUMN_INSTITUCION +" TEXT," +
            TABLE_EVENTO_COLUMN_DETALLES + " TEXT," +
            TABLE_EVENTO_COLUMN_FECHA + " TEXT," +
            TABLE_EVENTO_COLUMN_HORAINICIO + " TEXT," +
            TABLE_EVENTO_COLUMN_HORAFIN + " TEXT," +
            TABLE_EVENTO_COLUMN_UBICACION + " TEXT" +
            ");";

    public static final String SQL_DELETE_EVENTO = "DROP TABLE IF EXISTS " + TABLE_EVENTO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_EVENTO + "';";

    public static final String SQL_CREATE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + " (" +
            TABLE_CATEGORIA_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_CATEGORIA_COLUMN_NOMBRE + " TEXT," +
            TABLE_CATEGORIA_COLUMN_DETALLES + " TEXT" +
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

    public static final String SQL_CREATE_PERMISO = "CREATE TABLE " + TABLE_PERMISO + " (" +
            TABLE_PERMISO_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_PERMISO_COLUMN_NOMBRE + " TEXT," +
            TABLE_PERMISO_COLUMN_DETALLES + " TEXT" +
            ");";

    public static final String SQL_DELETE_PERMISO = "DROP TABLE IF EXIST " + TABLE_PERMISO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_PERMISO + "';";

    public static final String SQL_CREATE_ROL = "CREATE TABLE " + TABLE_ROL + " (" +
            TABLE_ROL_COLUMN_ID + " INTEGER PRIMARY KEY," +
            TABLE_ROL_COLUMN_NOMBRE + " TEXT," +
            TABLE_ROL_COLUMN_DETALLES + " TEXT" +
            ");";

    public static final String SQL_DELETE_ROL = "DROP TABLE IF EXIST " + TABLE_ROL + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_ROL + "';";

    public static final String SQL_CREATE_ROL_PERMISO = "CREATE TABLE " + TABLE_ROL_PERMISO + " (" +
            TABLE_ROL_PERMISO_COLUMN_ID_ROL + " INTEGER," +
            TABLE_ROL_PERMISO_COLUMN_ID_PERMISO + " INTEGER," +
            "PRIMARY KEY (" + TABLE_ROL_PERMISO_COLUMN_ID_ROL + "," + TABLE_ROL_PERMISO_COLUMN_ID_PERMISO + ")," +
            "FOREIGN KEY (" + TABLE_ROL_PERMISO_COLUMN_ID_ROL + ") REFERENCES " +
            TABLE_ROL + "(" + TABLE_ROL_COLUMN_ID + ")," +
            "FOREIGN KEY (" + TABLE_ROL_PERMISO_COLUMN_ID_PERMISO + ") REFERENCES " +
            TABLE_PERMISO + "(" + TABLE_PERMISO_COLUMN_ID + ")," +
            ");";

    public static final String SQL_DELETE_ROL_PERMISO = "DROP TABLE IF EXISTS " + TABLE_ROL_PERMISO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_ROL_PERMISO + "';";

    public static final String SQL_CREATE_USUARIO = "CREATE TABLE " + TABLE_USUARIO + " (" +
            TABLE_USUARIO_COLUMN_CORREO_UCR + " TEXT PRIMARY KEY," +
            TABLE_USUARIO_COLUMN_CONTRASENIA + " TEXT," +
            TABLE_USUARIO_COLUMN_NOMBRE + " TEXT," +
            TABLE_USUARIO_COLUMN_APELLIDO1 + " TEXT," +
            TABLE_USUARIO_COLUMN_APELLIDO2 + " TEXT," +
            ");";

    public static final String SQL_DELETE_USUARIO = "DROP TABLE IF EXISTS " + TABLE_USUARIO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_USUARIO + "';";

    public static final String SQL_CREATE_USUARIO_EVENTO = "CREATE TABLE " + TABLE_USUARIO_EVENTO + " (" +
            TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO + " TEXT," +
            TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO + " INTEGER," +
            "PRIMARY KEY (" + TABLE_ROL_PERMISO_COLUMN_ID_ROL + "," + TABLE_ROL_PERMISO_COLUMN_ID_PERMISO + ")," +
            "FOREIGN KEY (" + TABLE_USUARIO_EVENTO_COLUMN_CORREO_UCR_USUARIO + ") REFERENCES " +
            TABLE_USUARIO + "(" + TABLE_USUARIO_COLUMN_CORREO_UCR + ")," +
            "FOREIGN KEY (" + TABLE_USUARIO_EVENTO_COLUMN_ID_EVENTO + ") REFERENCES " +
            TABLE_EVENTO + "(" + TABLE_EVENTO_COLUMN_ID + ")," +
            ");";

    public static final String SQL_DELETE_USUARIO_EVENTO = "DROP TABLE IF EXISTS " + TABLE_USUARIO_EVENTO + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_USUARIO_EVENTO + "';";

    public static final String SQL_CREATE_ROL_USUARIO_INSTITUCION = "CREATE TABLE " + TABLE_ROL_USUARIO_INSTITUCION + " (" +
            TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL + " INTEGER," +
            TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + " TEXT," +
            TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + " INTEGER," +
            "PRIMARY KEY (" + TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL + "," + TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + "," +
            TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + ")," +
            "FOREIGN KEY (" + TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_ROL + ") REFERENCES " +
            TABLE_ROL + "(" + TABLE_ROL_COLUMN_ID + ")," +
            "FOREIGN KEY (" + TABLE_ROL_USUARIO_INSTITUCION_COLUMN_CORREO_UCR_USUARIO + ") REFERENCES " +
            TABLE_USUARIO + "(" + TABLE_USUARIO_COLUMN_CORREO_UCR + ")," +
            "FOREIGN KEY (" + TABLE_ROL_USUARIO_INSTITUCION_COLUMN_ID_INSTITUCION + ") REFERENCES " +
            TABLE_INSTITUCION + "(" + TABLE_INSTITUCION_COLUMN_ID + ")," +
            ");";

    public static final String SQL_DELETE_ROL_USUARIO_INSTITUCION = "DROP TABLE IF EXISTS " + TABLE_ROL_USUARIO_INSTITUCION + ";" +
            "DELETE FROM sqlite_sequence WHERE name = '" + TABLE_ROL_USUARIO_INSTITUCION + "';";


}
