package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarDePantalla();
            }
        });

        // Ejemplo de uso para verificar que funciona
        Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaInicio.setTime(new Date());
        fechaFin.setTime(new Date());

        Evento evento = new Evento();
        evento.setNombre("Evento 1");
        evento.setDetalles("Detalles del evento 1");
        evento.setFechaInicio(fechaInicio);
        evento.setFechaFin(fechaFin);

        long rowid = evento.insertar(getApplicationContext());

        // Verifica que se ingreso en la base de datos
        if (rowid != -1) {
            // Pedimos de la base de datos el evento que se guardo anteriormente
            Evento nuevoEvento = new Evento();
            nuevoEvento.leer(getApplicationContext(),"1");

            // Mostramos el Evento en la aplicacion
            Toast.makeText(getApplicationContext(), nuevoEvento.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void cambiarDePantalla() {
        Intent a =new Intent(this,CrearEvento.class);
        startActivity(a);
    }
}
