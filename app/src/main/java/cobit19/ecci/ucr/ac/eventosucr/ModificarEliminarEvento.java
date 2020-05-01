package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModificarEliminarEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{
Evento evento;
Evento eventoElimina;
String id;
    boolean tiempoInicio;
    boolean tiempoFinal;
    Calendar fecha;
    String horaInicio;
    String horaFinalBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar_evento);
        TextView tiempoIni =(TextView)findViewById(R.id.tiempoInicio);
        TextView tiempoFin =(TextView)findViewById(R.id.tiempoFin);
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
        ImageButton button = (ImageButton) findViewById(R.id.calendario);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        ImageButton button1 = (ImageButton) findViewById(R.id.tiempoIniimg);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiempoInicio=true;

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        ImageButton button2 = (ImageButton) findViewById(R.id.tiempoFinimg);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiempoFinal=true;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

    }

    public void modificarEvento() {
        EditText nombre=(EditText)findViewById(R.id.nombreEvento);
        EditText institucion=(EditText)findViewById(R.id.nombreInstitucion2);
        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);
        EditText masinfo=(EditText)findViewById(R.id.masInformacion);
        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);
        if(nombre.length()!=0){
            evento.setNombre(nombre.getText().toString());

        }
        if(institucion.length()!=0){
            evento.setInstitucion(institucion.getText().toString());

        }
        if(detalles.length()!=0){
            evento.setDetalles(detalles.getText().toString());

        }
        if(masinfo.length()!=0){
            evento.setMasInfo(masinfo.getText().toString());

        }
        if(ubicacion.length()!=0){
            evento.setUbicacion(ubicacion.getText().toString());

        }

        long ret=evento.actualizar(getApplicationContext());
        Intent intent = new Intent(this, ListaEventosSuperUsuario.class);
        startActivity(intent);


    }
    public void showAlertDialogButtonClicked() {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Evento");

        //builder.setTitle("¿Desea eliminar el evento \n"+evento.getNombre()+" ?\n Esta opción es irreversible");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.modal_eliminar, null);
        builder.setView(customLayout);
        // add a button
        Button eliminar = customLayout.findViewById(R.id.button_elimina);
        Button cancelar = customLayout.findViewById(R.id.button_cancela);
        TextView textoModal = customLayout.findViewById(R.id.textoEliminar);
        textoModal.setText("¿Desea eliminar el evento \n"+evento.getNombre()+" ?\n Esta opción es irreversible");



        // create and show the alert dialog
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Funcionalidades de los botones
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEvent();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        evento.setFecha(c);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());//Fecha completa
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        int dia=c.get(Calendar.DAY_OF_MONTH);
        String diaFinal=String.valueOf(dia);
        String diaSemana = dateFormat.format(c.getTime());

        SimpleDateFormat dateFormatMes = new SimpleDateFormat("MMMM");
        String mes = dateFormatMes.format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.fecha);
        textView.setText(diaSemana+", \n"+diaFinal+" de "+mes);
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView horaIni = (TextView) findViewById(R.id.tiempoInicio);
        TextView horaFin = (TextView) findViewById(R.id.tiempoFin);
        DecimalFormat df = new DecimalFormat("00");

        if(tiempoInicio){
            tiempoInicio=false;
            horaIni.setText(df.format(hourOfDay)+" : "+df.format(minute));
            horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
            evento.setHoraInicio(df.format(hourOfDay)+" : "+df.format(minute));

        }else {
            tiempoFinal=false;
            horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
            evento.setHoraFin(df.format(hourOfDay)+" : "+df.format(minute));
            //horaFinalBase=df.format(hourOfDay)+" : "+df.format(minute);
        }



        Toast.makeText(getApplicationContext(), hourOfDay + " " + minute, Toast.LENGTH_SHORT).show();

    }
}
