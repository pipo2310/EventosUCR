package cobit19.ecci.ucr.ac.eventosucr;

/**
 * Created by Fabian on 22/4/2020.
 * Ultima modificacion by Fabian on 22/4/2020.
 * Esta clase define el esquema de la base de datos de la app
 * Esta clase debe usarse en toda la app
 */

public final class DataBaseContract {

    private DataBaseContract() {}

    public static final String SQL_CREATE_EVENTO = "CREATE TABLE Persona (" +
            "id INTEGER PRIMARY KEY," +
            "nombre TEXT," +
            "detalles TEXT," +
            "fechaInicio TEXT," +
            "fechaFin TEXT)";

    public static final String SQL_DELETE_EVENTO = "DROP TABLE IF EXISTS Persona;" +
            "DELETE FROM sqlite_sequence WHERE name = 'Persona';";
}
