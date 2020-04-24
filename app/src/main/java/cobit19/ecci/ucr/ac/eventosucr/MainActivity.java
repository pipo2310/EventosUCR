package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EJEMPLO INGRESAR EVENTO
        /*// Ejemplo de uso para verificar que funciona
        Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaInicio.setTime(new Date());
        fechaFin.setTime(new Date());

        Evento evento = new Evento();
        evento.setNombre("Evento 1");
        evento.setDetalles("Detalles del evento 1");
        evento.setFechaInicio(fechaInicio);
        evento.setFechaFin(fechaFin);

        long rowidEvento = evento.insertar(getApplicationContext());

        // Verifica que se ingreso en la base de datos
        if (rowidEvento != -1) {
            // Pedimos de la base de datos el evento que se guardo anteriormente
            Evento nuevoEvento = new Evento();
            nuevoEvento.leer(getApplicationContext(),"1");

            // Mostramos el Evento en la aplicacion
            Toast.makeText(getApplicationContext(), nuevoEvento.toString(), Toast.LENGTH_LONG).show();
        }*/

        // EJEMPLO INGRESAR CATEGORIA
        /*Categoria categoria = new Categoria();
        categoria.setNombre("Categoria 1");
        categoria.setDetalles("Detalles de la categoria 1");

        long rowidCategoria = categoria.insertar(getApplicationContext());

        if (rowidCategoria != -1) {
            Categoria nuevaCategoria = new Categoria();
            nuevaCategoria.leer(getApplicationContext(),"1");

            Toast.makeText(getApplicationContext(), nuevaCategoria.toString(), Toast.LENGTH_LONG).show();
        }*/
    }
}
