package cobit19.ecci.ucr.ac.eventosucr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.services.EventoService;
import cobit19.ecci.ucr.ac.eventosucr.shared.ListaEventosFragment;
import cobit19.ecci.ucr.ac.eventosucr.core.services.ImagenService;

public class ListaEventosSuperUsuario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ListaEventosFragment.OnEventoSeleccionadoInteractionListener {
    ArrayList<Evento>eventos;
    ArrayList<Evento>eventosPruebaFireBase=new ArrayList<Evento>();
    ListView list;
    public final static String EXTRA_MESSAGE="evento";
    public final static String EXTRA_MESSAGEIMAGEN="imagenes";
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_super_usuario);

        // nav bar de arriba
        Toolbar toolbar = findViewById(R.id.toolbar_LE);
        setSupportActionBar(toolbar);

        // Para el menu lateral
        drawer = findViewById(R.id.drawer_layout_LE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view_LE);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvento();
            }
        });
        leerEventos();
    }


    public void addEvento() {

        Intent intent = new Intent(this, CrearEvento.class);


        // Deseo recibir una respuesta: startActivityForResult()
        startActivity(intent);
        finish();
    }


    public void leerEventos() {
        EventoService eventoService=new EventoService();
        final ImagenService imagenService=new ImagenService();
        final Bitmap imagenNula= BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.ucr_evento_img);
        final ImageView imagenNulaImageView=new ImageView(this);
        final long ONE_MEGABYTE = 1024 * 1024;
        final int[] i = {0};
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();
        imagenNulaImageView.setImageBitmap(imagenNula);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarioEventosCreado")
                .document(Constantes.CORREO_UCR_USUARIO)
                .collection("eventosUsuario")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            eventosPruebaFireBase=new ArrayList<Evento>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                eventosPruebaFireBase.add(document.toObject(Evento.class));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            final ArrayList<ImageView> imagenesdeEventos=new ArrayList<ImageView>();
                            if(eventosPruebaFireBase.size()>0){
                                for(Evento evento : eventosPruebaFireBase){
                                StorageReference mountainImagesRef = storageRef.child("events/"+evento.getNombre()+".png");
                                mountainImagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        ImageView imagenExistente = new ImageView(ListaEventosSuperUsuario.this);
                                        // Set the Bitmap data to the ImageView
                                        imagenExistente.setImageBitmap(bmp);
                                        imagenesdeEventos.add(imagenExistente);
                                        if(i[0]++ == eventosPruebaFireBase.size() - 1){
                                            ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventosPruebaFireBase,imagenesdeEventos);
                                            getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                    .commit();
                                        }


                                        // Data for "images/island.jpg" is returns, use this as needed
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        //Imagen imagen=new Imagen(evento.getId(),imagenNula);

                                        imagenesdeEventos.add(imagenNulaImageView);
                                        if(i[0]++ == eventosPruebaFireBase.size() - 1){
                                            ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventosPruebaFireBase,imagenesdeEventos);
                                            getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                    .commit();
                                        }

                                    }
                                });

                            }}

                            //ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventosPruebaFireBase,imagenesdeEventos);
                            //getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                                 //   .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                   // .commit();

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        /*
        eventos = eventoService.leerLista(getApplicationContext());
        final ArrayList<ImageView> imagenesdeEventos=new ArrayList<ImageView>();
        final long ONE_MEGABYTE = 1024 * 1024;

        final int[] i = {0};

        StorageReference listRef = storage.getReference().child("events/");


        for (final Evento evento : eventos){
            //imagenesdeEventos.add(imagenNulaImageView);



            try{
                StorageReference mountainImagesRef = storageRef.child("events/"+evento.getNombre()+".png");
                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        evento.setImagen(uri.toString());
                        // Got the download URL for 'users/me/profile.png'
                        // Pass it to Picasso to download, show in ImageView and caching

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        evento.setImagen("https://firebasestorage.googleapis.com/v0/b/eventosucr-35c97.appspot.com/o/events%2Fucr_evento_img.jpg?alt=media&token=a54035a7-f0ef-4083-9e72-530884dafbb4");
                    }
                });

                evento.setImagen(mountainImagesRef.getDownloadUrl().getResult().toString());

*/
/*
                //Leer imagen de evento
                mountainImagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ImageView imagenExistente = new ImageView(ListaEventosSuperUsuario.this);
                        // Set the Bitmap data to the ImageView
                        imagenExistente.setImageBitmap(bmp);
                        imagenesdeEventos.add(imagenExistente);
                        if(i[0]++ == eventos.size() - 1){
                            ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventos,imagenesdeEventos);
                            getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        }

                        // Data for "images/island.jpg" is returns, use this as needed
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //Imagen imagen=new Imagen(evento.getId(),imagenNula);

                        imagenesdeEventos.add(imagenNulaImageView);
                        if(i[0]++ == eventos.size() - 1){
                            ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventos,imagenesdeEventos);
                            getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        }
                    }
                });
                */
                //riversRef.getByte(localFile)
/*
            }catch (Exception e){
                evento.setImagen("gs://eventosucr-35c97.appspot.com/events/ucr_evento_img.jpg");
                //Evento no tiene imagen
                //imagenesdeEventos.add(imagenNulaImageView);
            }
*/


             /*

            if(imagenService.leerImagenEvento(getApplicationContext(),evento.getId()).size()==0){
                //Imagen imagen=new Imagen(evento.getId(),imagenNula);
                imagenesdeEventos.add(imagenNulaImageView);
            }else{

                ImageView imagenExistente=new ImageView(this);
                imagenExistente.setImageBitmap(imagenService.leerImagenEvento(getApplicationContext(),evento.getId()).get(0).getImagen());
                imagenesdeEventos.add(imagenExistente);
            }


*/

        }

        //ListaEventosFragment listaEventosFragment=new ListaEventosFragment(eventos,imagenesdeEventos);
        //getSupportFragmentManager().beginTransaction().replace(R.id.listaEventosFragmentVista, listaEventosFragment)
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                //.commit();








    /**
     * Metodo para el menu lateral aqui se pone donde se va la aplicacion cada vez que se toca un item
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.nav_home){
            Intent a =new Intent(this, MenuActivity.class);
            startActivity(a);
            // finalizamos la aplicacion para que NO quede en segundo plano
            finish();
        }
        else{
            drawer.closeDrawer(Gravity.LEFT, true);
        }
        return true;
    }

    @Override
    public void onEventoSelecciondo(Evento evento) {

    }
}
