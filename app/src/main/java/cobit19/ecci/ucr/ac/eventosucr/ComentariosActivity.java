package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Comentario;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;

public class ComentariosActivity extends AppCompatActivity {
    private Evento evento;
    private ArrayList<Comentario> comentarios=new ArrayList<Comentario>();
    private ListView list;

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
    }

    private void comentar() {
        // Texto del comentario que se quiere enviar
        TextView comentarioNuevo = findViewById(R.id.comentarios_comentario_nuevo);
        String textoComentario = comentarioNuevo.getText().toString();
        textoComentario.replaceAll("\\s+","");
        if(textoComentario == "") {
            // Se obtiene la fecha actual
            Date currentTime = Calendar.getInstance().getTime();
            // Se crea un objeto comentario
            Comentario comment = new Comentario();
            // al objeto comentario se le agrega el texto que ingreso el usuario
            comment.setComentario(comentarioNuevo.getText().toString());
            // se agrega la fecha y hora actual del usuario
            comment.setHora(currentTime.toString());
            // se agrega el nombre del usuario que comenta
            // TODO: Falta obtener el nombre del usuario
            comment.setNombre("Walter Bonilla");
            // se agrega el identificador del usuario
            comment.setComentante(Constantes.CORREO_UCR_USUARIO);

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
                llenarListView();
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

    private void llenarListView() {
        ComentariosListAdapter adapter = new ComentariosListAdapter(this, comentarios);
        list = findViewById(R.id.lista_comentarios);
        list.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
