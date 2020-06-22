package cobit19.ecci.ucr.ac.eventosucr.features.vistaEvento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Comentario;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

public class ComentariosActivity extends AppCompatActivity {
    private Evento evento;
    private ArrayList<Comentario> comentarios=new ArrayList<Comentario>();
    private ListView list;
    private int numeroComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);


        // Obtenemos el evento
        Bundle b = getIntent().getExtras();
        if (b != null) {
            // obtenemos el objeto tableTop
            evento = b.getParcelable(VistaEventoFragment.EXTRA_MESSAGE_VE);
        }else{
            onBackPressed();
        }
        //eventLiked();

        // Ponemos el nombre del evento
        TextView nombreEvento = findViewById(R.id.comentarios_nombre_evento);
        nombreEvento.setText(evento.getNombre());
        numeroComentarios=0;
        // Llenamos la lista con los comentarios de los usuarios
        llenarLista();

        // boton para enviar un comentario
        TextView enviarComentario = findViewById(R.id.comentarios_enviar_comentario);

        enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentar();
            }
        });

        // boton para likear un comentario
        /*
        View commentLayout = LayoutInflater.from(this).inflate(R.layout.comentario_evento, null);
        ImageButton likeComentario = (ImageButton)commentLayout.findViewById(R.id.likeicon);

        likeComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like();
            }
        });

         */
    }

    private void like() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrOfStr = user.getEmail().split("@");
        String userName = arrOfStr[0];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("likes").child(evento.getNombre()).push().setValue(user.getEmail());

    }

    private void comentar() {
        // Texto del comentario que se quiere enviar
        TextView comentarioNuevo = findViewById(R.id.comentarios_comentario_nuevo);
        String textoComentario = comentarioNuevo.getText().toString();
        if(textoComentario != "") {
            // Se obtiene la fecha actual
            Date currentTime = Calendar.getInstance().getTime();
            // Se crea un objeto comentario
            Comentario comment = new Comentario();
            // al objeto comentario se le agrega el texto que ingreso el usuario
            comment.setComentario(comentarioNuevo.getText().toString());
            // se agrega la fecha y hora actual del usuario
            comment.setHora(currentTime.toString());

            // Obtenemos el usuario logueado de Firebase
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // se agrega el nombre del usuario que comenta
            String[] arrOfStr = user.getEmail().split("@");
            String userName = arrOfStr[0];
            comment.setNombre(userName);

            // se agrega el identificador del usuario
            comment.setComentante(user.getEmail());

            // se limpia la entrada del usuario
            comentarioNuevo.setText("");

            // Obtenemos la base de datos de Firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            // insertamos el comentario en la base de datos
            DatabaseReference myRef = database.getReference();
            myRef.child("comentariosEvento").child(evento.getNombre()).push().setValue(comment);
        }else{
            Toast.makeText(this, "Debe escribir algo en el comentario", Toast.LENGTH_SHORT).show();
        }
    }



    private void llenarLista() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("comentariosEvento/"+evento.getNombre());
        ref.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Comentario newPost = dataSnapshot.getValue(Comentario.class);

                comentarios.add(newPost);
                llenarListView(newPost);

                //eventLiked();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void llenarListView(Comentario ultimoComentario) {
        ComentariosListAdapter adapter = new ComentariosListAdapter(this, comentarios,evento);
        list = findViewById(R.id.lista_comentarios);
        list.setAdapter(adapter);

        eventLiked(ultimoComentario,comentarios.size()-1,list);


    }

    private void eventLiked(Comentario comentario, int position, ViewGroup listView){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("likes/"+evento.getNombre()+"/"+comentario.getHora());
        ref.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //Se actualiza interfaz aumentandole la cantidad de likes
                String comentante = dataSnapshot.getValue(String.class);

                comentario.setLikes(comentario.getLikes()+1);
                comentarios.set(position,comentario);
                ListView yourListView= (ListView) listView;
                //notifyDataSetChanged();

                View v = yourListView.getChildAt(position -
                        yourListView.getFirstVisiblePosition());

                if(v == null)
                    return;

                TextView numComentarios = v.findViewById(R.id.numerocomentarios);
                numComentarios.setText("Likes "+comentarios.get(position).getLikes());
                //ComentariosActivity.this.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
