package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModificarEliminarEvento extends AppCompatActivity {
Evento evento;
Evento eventoElimina;
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar_evento);
        evento=new Evento();
        eventoElimina=new Evento();
        Bundle b = getIntent().getExtras();
        if (b != null)
        {
            // obtenemos el objeto persona
            evento = b.getParcelable(ListaEventosSuperUsuario.EXTRA_MESSAGE);
        }
        Button modificarEvento = (Button) findViewById(R.id.modificarEvento);
        modificarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEvento();
            }
        });
        Button eliminarEvento = (Button) findViewById(R.id.eliminarEvento);
        eliminarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEvento();
            }
        });

        prellenarCampos();

    }

    public void modificarEvento() {


    }
    public void showAlertDialogButtonClicked() {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desea eliminar el evento \n"+evento.getNombre()+" ?");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.modal_eliminar, null);
        builder.setView(customLayout);
        // add a button
        Button eliminar = customLayout.findViewById(R.id.button_elimina);
        Button cancelar = customLayout.findViewById(R.id.button_cancela);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEvent();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void eliminarEvent() {
        eventoElimina.eliminar(getApplicationContext(),evento.getId());
        Intent intent = new Intent(this, ListaEventosSuperUsuario.class);
        startActivity(intent);
    }

    // do something with the data coming from the AlertDialog
    private void sendDialogDataToActivity(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
    public void eliminarEvento() {
        showAlertDialogButtonClicked();


    }

    public void prellenarCampos() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        int dia=evento.getFecha().get(Calendar.DAY_OF_MONTH);
        String diaFinal=String.valueOf(dia);
        String diaSemana = dateFormat.format(evento.getFecha().getTime());

        SimpleDateFormat dateFormatMes = new SimpleDateFormat("MMMM");
        String mes = dateFormatMes.format(evento.getFecha().getTime());

        EditText nombre=(EditText)findViewById(R.id.nombreEvento);
        EditText institucion=(EditText)findViewById(R.id.nombreInstitucion2);
        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);
        EditText masinfo=(EditText)findViewById(R.id.masInformacion);
        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);

        TextView tiempoIni =(TextView)findViewById(R.id.tiempoInicio);
        TextView tiempoFin =(TextView)findViewById(R.id.tiempoFin);
        TextView fecha = (TextView) findViewById(R.id.fecha);
        fecha.setText(diaSemana+", \n"+diaFinal+" de "+mes);
        nombre.setHint(evento.getNombre());
        institucion.setHint(evento.getInstitucion());
        detalles.setText(evento.getDetalles());
        masinfo.setHint(evento.getMasInfo());
        ubicacion.setHint(evento.getUbicacion());
        tiempoIni.setText(evento.getHoraInicio());
        tiempoFin.setText(evento.getHoraFin());



    }
}
