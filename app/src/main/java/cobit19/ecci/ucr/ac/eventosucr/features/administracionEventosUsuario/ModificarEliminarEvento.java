package cobit19.ecci.ucr.ac.eventosucr.features.administracionEventosUsuario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cobit19.ecci.ucr.ac.eventosucr.DatePickerFragment;
import cobit19.ecci.ucr.ac.eventosucr.MapActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.TimePickerFragment;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.room.CategoriaViewModel;
import cobit19.ecci.ucr.ac.eventosucr.shared.Constantes;

public class ModificarEliminarEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, OnMapReadyCallback,
        GoogleMap.OnMapClickListener, AdapterView.OnItemSelectedListener {

    Evento evento;
    Evento eventoElimina;
    boolean tiempoInicio;
    boolean tiempoFinal;
    int horaInicioManejoError;
    int minutoInicioManejoError;
    GoogleMap eventoMap;
    private static final int SELECT_PICTURE=100;
    public static  final String TAG= "SelectImageeActivity";

    // ROOM
    // Crear la variable del model view de categoria
    private CategoriaViewModel categoriaViewModel;

    // Necesario para ver si hay que cambiar la imagen
    private boolean imagenCambiada;

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
            // Obtenemos el evento
            evento = b.getParcelable(ListaEventosUsuario.EXTRA_MESSAGE);

        }

        final ImageView imagenEvento=(ImageView)findViewById(R.id.imagenEventoModificar);
        // Agregamos a imagen por medio de un URL
        Glide.with(this)
                .load(evento.getUrlImagen())
                // Vemos si podemos utilizar o no la imagen del cache
                .signature(new ObjectKey(evento.getImagenUltimaModificacion()))
                .into(imagenEvento);
        imagenCambiada = false;

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

        ImageButton insertarImagen = (ImageButton) findViewById(R.id.agregarImagen);

        insertarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarImagenAImageView();
            }
        });

        // Le pedimos al proovedor de view models que nos de el de categorias
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
            String latitudString = sharedPreferences.getString("latitud","");
            String longitudString = sharedPreferences.getString("longitud","");
            evento.setLatitud(Double.parseDouble(latitudString));
            evento.setLongitud(Double.parseDouble(longitudString));
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(evento.getLatitud(), evento.getLongitud(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.ubicacionMap);
        mapFragment.getMapAsync(this);
    }

    private void agregarImagenAImageView() {
        openImageChooser();
    }

    public void modificarEvento() {
        // Por ahora no se puede editar el nombre
        // EditText nombre=(EditText)findViewById(R.id.nombreEvento);

        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);

        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);

        if(detalles.length()!=0){
            evento.setDetalles(detalles.getText().toString());
        }

        if(ubicacion.length()!=0){
            evento.setUbicacion(ubicacion.getText().toString());
        }

        String eventoId = evento.getNombre().replaceAll(" ", "");
        String usuarioId = Constantes.CORREO_UCR_USUARIO.replaceAll("@(.)*", "");


        if (imagenCambiada) {
            // STORAGE
            // Crear la referencia al storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            // Crear una refeerencia al lugar donde se va a guardar la imagen
            StorageReference mountainImagesRef = storageRef.child("eventos/"+eventoId+".png");

            ImageView imagenEvento=(ImageView)findViewById(R.id.imagenEventoModificar);
            // Setear la forma en la que se va a guardar la imagen
            imagenEvento.setDrawingCacheEnabled(true);
            imagenEvento.buildDrawingCache();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imagenEvento.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setContentType("image/png")
                        .build();

                // Agregar la imagen al storage
                UploadTask uploadTask = mountainImagesRef.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Date date = new Date();
                        evento.setImagenUltimaModificacion(date.toString());

                        // La razon de hacerlo asi es porque la imagen toma un rato en actualizarce por lo tanto
                        // esto le da tiempo de no traerse la imagen anterior

                        // FIRESTORE
                        // Modificamos el evento
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        // Lo midificamos en las categorias a las que pertenece
                        for (String categoria: evento.getCategorias()) {
                            db.collection("categoriaEventos")
                                    .document(categoria)
                                    .collection("eventos")
                                    .document(eventoId)
                                    .set(evento);
                        }

                        //Agrega a la coleccion de eventos el evento identificado por su nombre
                        db.collection("eventos").document(eventoId).set(evento);

                        //Se recupera el usuario actual de la aplicacion mediante firebaase me imagino la verdad nose
                        db.collection("usuarioEventosCreado")
                                .document(usuarioId)
                                .collection("eventos")
                                .document(eventoId).set(evento);

                        // Modificamos el evento para los usuarios interesedos
                        for (String usuarioInteresado: evento.getUsuariosInteresados()) {
                            db.collection("meInteresaUsuarioEvento")
                                    .document(usuarioInteresado)
                                    .collection("eventos")
                                    .document(eventoId)
                                    .set(evento);
                        }

                        Intent intent = new Intent(getApplicationContext(), ListaEventosUsuario.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        } else {
            // FIRESTORE
            // Modificamos el evento
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Lo midificamos en las categorias a las que pertenece
            for (String categoria: evento.getCategorias()) {
                db.collection("categoriaEventos")
                        .document(categoria)
                        .collection("eventos")
                        .document(eventoId)
                        .set(evento);
            }

            //Agrega a la coleccion de eventos el evento identificado por su nombre
            db.collection("eventos").document(eventoId).set(evento);

            //Se recupera el usuario actual de la aplicacion mediante firebaase me imagino la verdad nose
            db.collection("usuarioEventosCreado")
                    .document(usuarioId)
                    .collection("eventos")
                    .document(eventoId).set(evento);

            // Modificamos el evento para los usuarios interesedos
            for (String usuarioInteresado: evento.getUsuariosInteresados()) {
                db.collection("meInteresaUsuarioEvento")
                        .document(usuarioInteresado)
                        .collection("eventos")
                        .document(eventoId)
                        .set(evento);
            }

            Intent intent = new Intent(this, ListaEventosUsuario.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        eventoMap = googleMap;
        eventoMap.setOnMapClickListener(this);
        configurarMapa(googleMap);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Intent i =new Intent(this, MapActivity.class);
        startActivity(i);

    }

    public void configurarMapa(GoogleMap googleMap){
        eventoMap = googleMap;

        //Se agregan las coordenadas del mapa
        LatLng ubicacionEvento = new LatLng(evento.getLatitud(), evento.getLongitud());

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
                dialog.cancel();
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
        // STORAGE
        // Crear la referencia al storage
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        String eventoId = evento.getNombre().replaceAll(" ", "");
        StorageReference imagenRef = storageRef.child("eventos/" + eventoId + ".png");
        // Eliminamos la imagen
        imagenRef.delete();

        // FIRESTORE
        // Crear la referencia a firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Lo eliminamos de la tabla de eventos
        db.collection("eventos")
                .document(eventoId)
                .delete();

        // Lo eliminamos de la tabla de eventos del usuario
        String usuarioId = Constantes.CORREO_UCR_USUARIO.replaceAll("@(.)*", "");
        db.collection("usuarioEventosCreado")
                .document(usuarioId)
                .collection("eventos")
                .document(eventoId)
                .delete();

        // Lo eliminamos de la tabla de eventos por categoria
        for (String categoria: evento.getCategorias()) {
            db.collection("categoriaEventos")
                    .document(categoria)
                    .collection("eventos")
                    .document(eventoId)
                    .delete();
        }

        // Lo eliminamos de favoritos
        for (String usuarioInteresado: evento.getUsuariosInteresados()) {
            db.collection("meInteresaUsuarioEvento")
                    .document(usuarioInteresado)
                    .collection("eventos")
                    .document(eventoId)
                    .delete();
        }

        Intent intent = new Intent(this, ListaEventosUsuario.class);
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

        //EditText institucion=(EditText)findViewById(R.id.nombreInstitucion2);
        EditText detalles=(EditText)findViewById(R.id.agregueDescripcion2);

        EditText ubicacion=(EditText)findViewById(R.id.agregarDireccion);

        TextView tiempoIni =(TextView)findViewById(R.id.tiempoInicio);
        TextView tiempoFin =(TextView)findViewById(R.id.tiempoFin);
        TextView fecha = (TextView) findViewById(R.id.fecha);

        fecha.setText(diaSemana+", \n"+diaFinal+" de "+mes);
        nombre.setHint(evento.getNombre());
        hintIntituciones();

        detalles.setText(evento.getDetalles());

        ubicacion.setHint(evento.getUbicacion());
        tiempoIni.setText(evento.getHoraInicio());
        tiempoFin.setText(evento.getHoraFin());



    }

    private void hintIntituciones() {

    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

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
                        openAppSettings(ModificarEliminarEvento.this);
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
        ImageView imagenEvento=(ImageView)findViewById(R.id.imagenEventoModificar);//Seteamos la imagen al image view

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
                            findViewById(R.id.imagenEventoModificar).post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ImageView) findViewById(R.id.imagenEventoModificar)).setImageURI(selectedImageUri);

                                    imagenCambiada = true;
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
