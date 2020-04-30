package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class VistaEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_evento);

        Calendar fecha = Calendar.getInstance();
        Evento evento = new Evento("1", "Semana U", "Feucr", "Evento masivo en el pretil", "", fecha, "11:00", "03:00", "Pretil, UCR");
        long newrowId = evento.insertar(getApplicationContext());
        leerEvento();


    }

    //Inserta los datos de la base de datos en la vista de un evento
    void leerEvento() {
        Evento evento = new Evento();
        //Llama al método leer que está en la clase Evento
        evento.leer(getApplicationContext(), "1");

        TextView nombreDeEvento = (TextView)findViewById(R.id.nombreDeEvento);
        nombreDeEvento.setText(evento.getNombre());
        TextView creadorEvento = (TextView)findViewById(R.id.creadorEvento);
        creadorEvento.setText(evento.getInstitucion());
        TextView fecha = (TextView) findViewById(R.id.fecha);
        fecha.setText(DateFormat.getDateInstance(DateFormat.FULL).format(evento.getFecha().getTime()));
        TextView hora = (TextView)findViewById(R.id.hora);
        hora.setText(evento.getHoraInicio() + " - "+ evento.getHoraFin());
        TextView ubicacion = (TextView)findViewById(R.id.ubicacion);
        ubicacion.setText(evento.getUbicacion());
        TextView detalles = (TextView)findViewById(R.id.descripcionDetalles);
        detalles.setText(evento.getDetalles());



    }

}



