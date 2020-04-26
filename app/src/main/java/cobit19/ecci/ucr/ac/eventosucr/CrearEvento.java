package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
    }
@Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.fecha);
        textView.setText(currentDateString);
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

    }else {
        tiempoFinal=false;
        horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
    }



    Toast.makeText(getApplicationContext(), hourOfDay + " " + minute, Toast.LENGTH_SHORT).show();

}



}
