package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Imagen;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;

public class ModificarEliminarEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{
Evento evento;
ArrayList<Imagen> imagenes;
Evento eventoElimina;
String id;
    boolean tiempoInicio;
    boolean tiempoFinal;
    Calendar fecha;
    String horaInicio;
    String horaFinalBase;
    int horaInicioManejoError;
    int minutoInicioManejoError;

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
            //imagenes=b.getParcelable(ListaEventosSuperUsuario.EXTRA_MESSAGEIMAGEN);

        }
        ImagenService imagenService=new ImagenService();
        imagenes=imagenService.leerImagenEvento(getApplicationContext(),evento.getId());
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
        EventoService eventoService = new EventoService();
        EditText nombre=(EditText)findViewById(R.id.nombreEvento);
        EditText institucion=(EditText)findViewById(R.id.nombreInstitucion2);
        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);

        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);
        if(nombre.length()!=0){
            evento.setNombre(nombre.getText().toString());

        }
        if(institucion.length()!=0){
            evento.setIdInstitucion(institucion.getText().toString());

        }
        if(detalles.length()!=0){
            evento.setDetalles(detalles.getText().toString());

        }

        if(ubicacion.length()!=0){
            evento.setUbicacion(ubicacion.getText().toString());

        }

        long ret=eventoService.actualizar(getApplicationContext(), evento);
        Intent intent = new Intent(this, ListaEventosSuperUsuario.class);
        startActivity(intent);
        finish();

    }
    public void showAlertDialogButtonClicked() {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Eliminar Evento");

        //builder.setTitle("¿Desea eliminar el evento \n"+evento.getNombre()+" ?\n Esta opción es irreversible");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.modal_eliminar_2, null);
        builder.setView(customLayout);
        // add a button
        Button eliminar = customLayout.findViewById(R.id.button_elimina);
        Button cancelar = customLayout.findViewById(R.id.button_cancela);
        TextView textoModal = customLayout.findViewById(R.id.textoEliminar);
        textoModal.setText("¿Desea eliminar el evento \n"+evento.getNombre()+" ?\n Esta opción es irreversible");
        TextView titulo = customLayout.findViewById(R.id.title);
        titulo.setText("Eliminar Evento");



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
        EventoService eventoService = new EventoService();
        eventoService.eliminar(getApplicationContext(),evento.getId());
        ImagenService imagenService=new ImagenService();
        imagenService.eliminarImagenesEventos(getApplicationContext(),evento.getId());
        Intent intent = new Intent(this, ListaEventosSuperUsuario.class);
        startActivity(intent);
        finish();
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

        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);

        TextView tiempoIni =(TextView)findViewById(R.id.tiempoInicio);
        TextView tiempoFin =(TextView)findViewById(R.id.tiempoFin);
        TextView fecha = (TextView) findViewById(R.id.fecha);
        ImageView imagenEvento=(ImageView)findViewById(R.id.imagenEventoModificar);
        if (imagenes.size()!=0){
            imagenEvento.setImageBitmap(imagenes.get(0).getImagen());
        }

        fecha.setText(diaSemana+", \n"+diaFinal+" de "+mes);
        nombre.setHint(evento.getNombre());
        institucion.setHint(evento.getIdInstitucion());
        detalles.setText(evento.getDetalles());

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
        Date fechaSeleccionada=c.getTime();

        if(System.currentTimeMillis()>fechaSeleccionada.getTime()){//Tiempo seleccionado es menor a tiempo actual(selecciono ayer por ejemplo)
            TextView fechaSelect = (TextView) findViewById(R.id.fecha);
            fechaSelect.setError("Fecha no valida");
        }else{
            TextView fechaSelect = (TextView) findViewById(R.id.fecha);
            fechaSelect.setError(null);
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
            horaInicioManejoError=hourOfDay;
            minutoInicioManejoError=minute;

        }else {
            tiempoFinal=false;
            if(hourOfDay>horaInicioManejoError){
                horaFin.setError(null);
                horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
                evento.setHoraFin(df.format(hourOfDay)+" : "+df.format(minute));
                //horaFinalBase=df.format(hourOfDay)+" : "+df.format(minute);
            }else if((hourOfDay==horaInicioManejoError)&&(minute>minutoInicioManejoError)){
                horaFin.setError(null);
                horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
                evento.setHoraFin(df.format(hourOfDay)+" : "+df.format(minute));
            }else{
                horaFin.setError("Tiempo final no valido");
            }

        }



        Toast.makeText(getApplicationContext(), hourOfDay + " " + minute, Toast.LENGTH_SHORT).show();

    }
}
