package cobit19.ecci.ucr.ac.eventosucr;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CrearEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener{
boolean tiempoInicio;
boolean tiempoFinal;
Calendar fecha;
String horaInicio;
String horaFinalBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        TextView tiempoIni =(TextView)findViewById(R.id.tiempoInicio);
        TextView tiempoFin =(TextView)findViewById(R.id.tiempoFin);
        TextView textView = (TextView) findViewById(R.id.fecha);

        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat simpleDate =  new SimpleDateFormat("hh : mm");

        String strDt = simpleDate.format(currentTime);
        tiempoIni.setText(strDt);
        tiempoFin.setText(strDt);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        SimpleDateFormat sdfday = new SimpleDateFormat("dd");
        SimpleDateFormat sdfmonth = new SimpleDateFormat("MMMM");
        //SimpleDateFormat sdfyear = new SimpleDateFormat("YYYY");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        String monthOfTheWeek = sdfmonth.format(d);
        //String yearOfTheWeek = sdfyear.format(d);
        String numOfTheWeek = sdfday.format(d);
        textView.setText(dayOfTheWeek+", \n"+numOfTheWeek +" de "+monthOfTheWeek);

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

        Button crearEvento = (Button) findViewById(R.id.guardarEvento);
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               guardarEvento();
            }
        });
    }

    public void guardarEvento() {
        boolean insertar=true;
        EditText nombre=(EditText)findViewById(R.id.nombreEvento);
        EditText institucion=(EditText)findViewById(R.id.nombreInstitucion2);
        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);

        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);
        if(nombre.length()==0){
            nombre.setError("Nombre no valido");
            insertar=false;

        }
        if(institucion.length()==0){
            institucion.setError("Institucion no valido");
            insertar=false;

        }
        if(detalles.length()==0){
            detalles.setError("Detalles no valido");
            insertar=false;

        }
        if(ubicacion.length()==0){
            ubicacion.setError("Detalles no valido");
            insertar=false;

        }
        if(insertar==true){
            Evento evento = new Evento(nombre.getText().toString(),institucion.getText().toString(),detalles.getText().toString(),fecha,horaInicio,horaFinalBase,ubicacion.getText().toString());
            // inserta el estudiante, se le pasa como parametro el contexto de la app
            long newRowId = evento.insertar(getApplicationContext());
            Toast.makeText(getApplicationContext(),
                    " Id: " + evento.getId() +
                    " Ubicacion "+evento.getUbicacion()+
                    " Nombre Evento: " + evento.getNombre()+ " Nombre Institucion: " +
                    evento.getInstitucion() +
                    " Detalles" + evento.getDetalles()+ " Hora Inicio: "+evento.getHoraInicio()+" Hora fin "+evento.getHoraFin() ,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ListaEventosSuperUsuario.class);

            finish();
            // Deseo recibir una respuesta: startActivityForResult()
            startActivity(intent);
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        fecha=c;

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
        horaInicio=df.format(hourOfDay)+" : "+df.format(minute);

    }else {
        tiempoFinal=false;
        horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
        horaFinalBase=df.format(hourOfDay)+" : "+df.format(minute);
    }



    Toast.makeText(getApplicationContext(), hourOfDay + " " + minute, Toast.LENGTH_SHORT).show();

}



}
