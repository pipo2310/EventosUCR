package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.core.models.CategoriaEvento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;

public class CrearEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    boolean tiempoInicio;
    boolean tiempoFinal;
    Calendar fecha;
    String horaInicio;
    String horaFinalBase;
    int horaInicioManejoError;
    int minutoInicioManejoError;
    // Lista de categorias de la base de datos
    ArrayList<Categoria> categorias;
    // Lista de nombres de categorias
    ArrayList<String> nombresCategorias = new ArrayList<String>();
    // Lista de categorias seleccionadas
    ArrayList<Integer> categoriasSeleccionadas = new ArrayList<Integer>();
    // Servicio de Categorias
    CategoriaService categoriaService = new CategoriaService();
    // Servicio de CategoriaEvento
    CategoriaEventoService categoriaEventoService = new CategoriaEventoService();
    // Servicio del Evento
    EventoService eventoService = new EventoService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        // Leemos las categorias de la base de datos
        categorias = categoriaService.leerLista(getApplicationContext());

        // Llenamos el spinner con los nombres de las categorias
        llenarCategorias();

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
        fecha=Calendar.getInstance();

        //fecha.setTime(d);
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

    // Selecciona un item del spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Mostrar al usuario la categoria seleccionada
        if(pos != 0){
            LinearLayout layout = (LinearLayout) findViewById(R.id.agregar_categorias_a_crear_evento);
            TextView textView = new TextView(this);
            textView.setText(nombresCategorias.get(pos));
            layout.addView(textView);
            categoriasSeleccionadas.add(pos-1);
        }else{
            // Decirle al usuario que debe seleccionar una categoria
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // No sirve
    }

    // Llena el spinner con la lista de categorias
    public void llenarCategorias(){
        nombresCategorias.add("Seleccione una categoría");
        for(int i = 0; i<categorias.size(); i++){
            nombresCategorias.add(categorias.get(i).getNombre());
        }
        Spinner listaCategorias = (Spinner) findViewById(R.id.dropdown_cat_1);
        //create array adapter and provide arrary list to it
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, nombresCategorias);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaCategorias.setAdapter(arrayAdapter);
        listaCategorias.setOnItemSelectedListener(this);
    }

    // Guarda el evento en base de Datos
    public void guardarEvento() {
        boolean insertar=true;
        EditText nombre=(EditText)findViewById(R.id.nombreEvento);
        EditText institucion=(EditText)findViewById(R.id.nombreInstitucion2);
        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);
        TextView tiempIni=(TextView)findViewById(R.id.tiempoInicio);
        TextView tiempFin=(TextView)findViewById(R.id.tiempoFin);

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
        if(horaInicio==null){
            tiempIni.setError("Hora inicio no valida");
            insertar=false;

        }
        if(horaFinalBase==null){
            tiempFin.setError("Hora final no valida");
            insertar=false;

        }

        if(insertar==true){
            Evento evento = new Evento(nombre.getText().toString(),institucion.getText().toString(),detalles.getText().toString(),fecha,horaInicio,horaFinalBase,ubicacion.getText().toString());
            // inserta el estudiante, se le pasa como parametro el contexto de la app
            long newRowId = eventoService.insertar(getApplicationContext(), evento);
            String eventoID = Long.toString(newRowId);
            for(int i=0; i<categoriasSeleccionadas.size(); i++){
                categoriaEventoService.insertar(getApplicationContext(), new CategoriaEvento(categorias.get(categoriasSeleccionadas.get(i)).getId(), eventoID));
            }
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
        Date fechaSeleccionada=c.getTime();

        if(System.currentTimeMillis()>fechaSeleccionada.getTime()){//Tiempo seleccionado es menor a tiempo actual(selecciono ayer por ejemplo)
            TextView fechaSelect = (TextView) findViewById(R.id.fecha);
            fechaSelect.setError("Fecha no valida");
        }else{
            TextView fechaSelect = (TextView) findViewById(R.id.fecha);
            fechaSelect.setError(null);

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
        horaInicioManejoError=hourOfDay;
        minutoInicioManejoError=minute;

    }else {
        tiempoFinal=false;
        if(hourOfDay>horaInicioManejoError){
            horaFin.setError(null);
            horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
            horaFinalBase=df.format(hourOfDay)+" : "+df.format(minute);
        }else if((hourOfDay==horaInicioManejoError)&&(minute>minutoInicioManejoError)){
            horaFin.setError(null);
            horaFin.setText(df.format(hourOfDay)+" : "+df.format(minute));
            horaFinalBase=df.format(hourOfDay)+" : "+df.format(minute);
        }else{
            horaFin.setError("Hora final no valida");
        }

    }



    Toast.makeText(getApplicationContext(), hourOfDay + " " + minute, Toast.LENGTH_SHORT).show();

}



}
