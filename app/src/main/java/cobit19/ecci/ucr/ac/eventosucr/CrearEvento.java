package cobit19.ecci.ucr.ac.eventosucr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mukesh.tinydb.TinyDB;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.core.models.CategoriaEvento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Imagen;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Institucion;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaEventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.CategoriaService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;
import cobit19.ecci.ucr.ac.eventosucr.core.services.InstitucionService;

public class CrearEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener , OnMapReadyCallback, GoogleMap.OnMapClickListener {
    boolean tiempoInicio;
    boolean tiempoFinal;
    Calendar fecha;
    String horaInicio;
    String horaFinalBase;
    double longitud;
    double latitud;
    String institucion;
    int horaInicioManejoError;
    int minutoInicioManejoError;
    GoogleMap eventoMap;
    private static final int SELECT_PICTURE=100;
    public static  final String TAG= "SelectImageActivity";
    // Lista de nombres de instituciones
    ArrayList<String> nombresInstituciones = new ArrayList<String>();
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

        SimpleDateFormat simpleDate =  new SimpleDateFormat("kk : mm");

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
        llenarInstituciones();

        textView.setText(dayOfTheWeek+", \n"+numOfTheWeek +" de "+monthOfTheWeek);
        fecha=Calendar.getInstance();
        /*
        ImageButton insertarUbicacion = (ImageButton) findViewById(R.id.ubicacionImagen);

        insertarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUbicacion();
            }
        });

         */
        ImageButton insertarImagen = (ImageButton) findViewById(R.id.agregarImagen);

        insertarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarImagenAImageView();
            }
        });
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

    private void llenarInstituciones() {
        InstitucionService institucionService=new InstitucionService();
        ArrayList<Institucion> instituciones=institucionService.leerLista(getApplicationContext());
        nombresInstituciones.add("Seleccione una institucion");
        for(int i = 0; i<instituciones.size(); i++){
            nombresInstituciones.add(instituciones.get(i).getNombre());
        }
        Spinner listaInstituciones = (Spinner) findViewById(R.id.dropdownInstitucion);
        //create array adapter and provide arrary list to it
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, nombresInstituciones);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaInstituciones.setAdapter(arrayAdapter);
        listaInstituciones.setOnItemSelectedListener(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        eventoMap = googleMap;
        eventoMap.setOnMapClickListener(this);
        configurarMapa(googleMap);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Intent i =new Intent(this,MapActivity.class);
        startActivity(i);

    }


    @Override
    protected void onResume() {
        super.onResume();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.ubicacionMap);
        mapFragment.getMapAsync(this);
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
            String latitudString = sharedPreferences.getString("latitud","");
            String longitudString = sharedPreferences.getString("longitud","");
            latitud=Double.parseDouble(latitudString);
            longitud=Double.parseDouble(longitudString);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitud, longitud, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            EditText ubicacionEscrita=(EditText)findViewById(R.id.agregarDireccion);
            ubicacionEscrita.setText(address);
            Log.i(TAG, "Direccion : " + addresses.get(0));



        }catch (Exception e){

        }
    }



    private void agregarImagenAImageView() {
        openImageChooser();

        //ImageView imagenEvento=(ImageView)findViewById(R.id.imagenEvento);//Seteamos la imagen al image view

    }

    // Selecciona un item del spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Mostrar al usuario la categoria seleccionada
        Spinner spinner = (Spinner) parent;
        if(spinner.getId()==R.id.dropdownInstitucion){//Spinner instituciones
            institucion=Integer.toString(pos);
        }else{
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
        

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // No sirve
    }
    //Permite realizar la configuración del mapa. Añade las coordenadas y el marcador  del evento correspondiente
    //Configura la cámara y el zoom
    public void configurarMapa(GoogleMap googleMap){
        eventoMap = googleMap;

        //Se agregan las coordenadas del mapa
        LatLng ubicacionEvento = new LatLng(latitud, longitud);

        //Se agrega el marcador en el mapa
        eventoMap.addMarker(new MarkerOptions().position(ubicacionEvento).title("Ubicacion"));

        // Muevo el mapa hacia la posición del marcador
        eventoMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionEvento));

        //Opciones de zoom
        UiSettings mapSettings;
        mapSettings = eventoMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true); //controles zoom
        mapSettings.setZoomGesturesEnabled(true); //gestos zoom
        mapSettings.setCompassEnabled(true); //brújula
        mapSettings.setMyLocationButtonEnabled(true);
        mapSettings.setRotateGesturesEnabled(true); //rotación


        //Mover la cámara
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ubicacionEvento)
                .zoom(20)
                .bearing(70)
                .tilt(25)
                .build();
        eventoMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
        //EditText institucion=(EditText)findViewById(R.id.nombreInstitucion2);
        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);
        TextView tiempIni=(TextView)findViewById(R.id.tiempoInicio);
        TextView tiempFin=(TextView)findViewById(R.id.tiempoFin);


        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);
        if(nombre.length()==0){
            nombre.setError("Nombre no valido");
            insertar=false;

        }


        if(institucion.length()==0){
            //institucion.setError("Institucion no valido");
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
        if(fecha==null){
            fecha=Calendar.getInstance();
        }
        SimpleDateFormat myDateFormat = new SimpleDateFormat("MM.dd.yyyy");
        Calendar fechaHoy=Calendar.getInstance();
        String fechaHoyEscrita=myDateFormat.format(fechaHoy.getTime());
        if(horaInicio==null){
            if(myDateFormat.format(fecha.getTime()).equals(fechaHoyEscrita)){
                tiempIni.setError("Hora inicio no valida");
                insertar=false;
            }else{
                SimpleDateFormat simpleDate =  new SimpleDateFormat("kk : mm");

                String strDt = simpleDate.format(fecha.getTime());
                horaInicio=strDt;
            }
        }

        if(horaFinalBase==null){
            tiempFin.setError("Hora final no valida");
            insertar=false;

        }

        if(insertar==true){
            ImageView imagenEvento=(ImageView)findViewById(R.id.imagenEvento);//Seteamos la imagen al image view
            Evento evento = new Evento(nombre.getText().toString(),institucion,detalles.getText().toString(),fecha,horaInicio,horaFinalBase,ubicacion.getText().toString(), latitud,longitud);
            // inserta el estudiante, se le pasa como parametro el contexto de la app
            long newRowId = eventoService.insertar(getApplicationContext(), evento);
            String eventoID = Long.toString(newRowId);
            for(int i=0; i<categoriasSeleccionadas.size(); i++){
                categoriaEventoService.insertar(getApplicationContext(), new CategoriaEvento(categorias.get(categoriasSeleccionadas.get(i)).getId(), eventoID));
            }
            BitmapDrawable drawable = (BitmapDrawable) imagenEvento.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ImagenService imagenService=new ImagenService();
            Imagen imagenAInsertar=new Imagen(eventoID,bitmap);

            imagenService.insertar(getApplicationContext(),imagenAInsertar);
            Toast.makeText(getApplicationContext(),
                    " Id: " + evento.getId() +
                    " Ubicacion "+evento.getUbicacion()+
                    " Nombre Evento: " + evento.getNombre()+ " Nombre Institucion: " +
                    evento.getInstitucion(getApplicationContext()).getNombre() +
                    " Detalles" + evento.getDetalles()+ " Hora Inicio: "+evento.getHoraInicio()+" Hora fin "+evento.getHoraFin() ,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ListaEventosSuperUsuario.class);


            // Deseo recibir una respuesta: startActivityForResult()
            startActivity(intent);
            finish();
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
        try {
            horaIni.setError(null);
        }
        catch(Exception e) {

        }
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

    private void handlePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_PICTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_PICTURE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //  Show your own message here
                        } else {
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings(CrearEvento.this);
                    }
                });
        alertDialog.show();
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        ImageView imagenEvento=(ImageView)findViewById(R.id.imagenEvento);//Seteamos la imagen al image view

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (resultCode == RESULT_OK) {
                    if (requestCode == SELECT_PICTURE) {
                        // Get the url from data
                        final Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            String path = getPathFromURI(selectedImageUri);
                            Log.i(TAG, "Image Path : " + path);
                            // Set the image in ImageView
                            findViewById(R.id.imagenEvento).post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ImageView) findViewById(R.id.imagenEvento)).setImageURI(selectedImageUri);
                                }
                            });

                        }
                    }
                }
            }
        }).start();

    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }





}
