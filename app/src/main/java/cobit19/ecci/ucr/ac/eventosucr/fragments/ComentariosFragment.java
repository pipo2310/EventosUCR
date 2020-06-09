package cobit19.ecci.ucr.ac.eventosucr.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import cobit19.ecci.ucr.ac.eventosucr.ComentariosListAdapter;
import cobit19.ecci.ucr.ac.eventosucr.Constantes;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.UtilDates;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Comentario;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosFragment extends Fragment {
    Evento evento;
    View view;
    ArrayList<Comentario> comentarios=new ArrayList<Comentario>();
    ListView list;
    public ComentariosFragment() {
        // Required empty public constructor
    }

    public ComentariosFragment(Evento evento) {
        this.evento = evento;

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_comentarios, container, false);

        // Llenamos la lista con los comentarios de los usuarios
        llenarLista();

        // boton para enviar un comentario
        TextView enviarComentario = view.findViewById(R.id.comentarios_enviar_comentario);

        enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentar();
            }
        });

        // Retornamos la vista
        return view;
    }

    private void comentar() {
        // Texto del comentario que se quiere enviar
        TextView comentarioNuevo = view.findViewById(R.id.comentarios_comentario_nuevo);
        // Se obtiene la fecha actual
        Date currentTime = Calendar.getInstance().getTime();
        // Se crea un objeto comentario
        Comentario comment=new Comentario();
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
    }

    private void llenarLista() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("comentariosEvento/"+evento.getNombre());
        ref.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Comentario newPost = dataSnapshot.getValue(Comentario.class);

                comentarios.add(newPost);
                //ya aqui esta la el ultimo de los comentarios en teoria
                llenarListView();
                //System.out.println("Author: " + newPost.getNombre());
                //System.out.println("Title: " + newPost.getHora());
                //System.out.println("Previous Post ID: " + prevChildKey);
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
        ComentariosListAdapter adapter = new ComentariosListAdapter(getActivity(), comentarios);
        list = view.findViewById(R.id.lista_comentarios);
        list.setAdapter(adapter);
    }

}
