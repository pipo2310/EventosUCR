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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

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
    View v;
    ArrayList<String> comentarios=new ArrayList<String>();
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
        v=inflater.inflate(R.layout.fragment_comentarios, container, false);
        Button enviarComentario=(Button)v.findViewById(R.id.enviarComentario);

        enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentar();
            }
        });
        llenarLista();
        // Inflate the layout for this fragment
        return v;
    }

    private void comentar() {
        Date currentTime = Calendar.getInstance().getTime();
        Comentario comment=new Comentario();
        comment.setComentario("Comentario");
        comment.setHora(currentTime.toString());
        comment.setNombre("Papillo");
        comment.setCommentante(Constantes.CORREO_UCR_USUARIO);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference();
        myRef.child("comentariosEvento").child(evento.getNombre()).setValue(comment);


    }

    private void llenarLista() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                Comentario comentario = dataSnapshot.getValue(Comentario.class);
                comentarios.add(comentario.getHora());
                //ya aqui esta la el ultimo de los comentarios en teoria
                int a =0;
                a++;
                llenarListView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("comentariosEvento/"+evento.getNombre());
        ref.addValueEventListener(postListener);



    }

    private void llenarListView() {
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, comentarios);
        list = (ListView) v.findViewById(R.id.list);
        list.setAdapter(itemsAdapter);
    }

}
