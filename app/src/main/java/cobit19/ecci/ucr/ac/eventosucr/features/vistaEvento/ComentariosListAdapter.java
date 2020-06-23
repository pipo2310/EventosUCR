package cobit19.ecci.ucr.ac.eventosucr.features.vistaEvento;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Comentario;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

public class ComentariosListAdapter extends ArrayAdapter<Comentario> {
    private final Activity context;
    private final ArrayList<Comentario> comentarios;
    private final Evento evento;

    public ComentariosListAdapter(Activity context, ArrayList<Comentario> comentarios, Evento evento) {
        super(context, R.layout.comentario_evento, comentarios);
        this.context = context;
        this.comentarios = comentarios;
        this.evento=evento;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.comentario_evento, null, true);

        // Nombre del usuario que realiza el comentario
        TextView nombre = rowView.findViewById(R.id.comentario_usuario);
        nombre.setText(comentarios.get(position).getNombre());

        // Texto del comentario
        TextView texto = rowView.findViewById(R.id.comentario_texto);
        texto.setText(comentarios.get(position).getComentario());

        // Fecha y hora en la que se realiza el comentario
        TextView fecha = rowView.findViewById(R.id.comentario_fecha);
        fecha.setText(comentarios.get(position).getHora());

        TextView numComentarios = rowView.findViewById(R.id.numerocomentarios);
        numComentarios.setText("Likes "+comentarios.get(position).getLikes());

        ImageButton like=rowView.findViewById(R.id.likeicon);


        //like.setTag("like");

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Evaluar con el tag del boton si ya esta likeado o no
                if(like.getTag()=="liked"){
                    dislike(comentarios.get(position),position,parent);

                }
                else
                {
                    like(comentarios.get(position),position,parent);

                }

            }
        });


        return rowView;
    }


    private void dislike(Comentario comentario,int position,ViewGroup listView){

        ListView yourListView= (ListView) listView;
        //notifyDataSetChanged();
        View v = yourListView.getChildAt(position -
                yourListView.getFirstVisiblePosition());

        if(v == null)
            return;

        ImageButton like = v.findViewById(R.id.likeicon);
        like.setTag("like");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrOfStr = user.getEmail().split("@");
        String userName = arrOfStr[0];
        userName=userName.replace('.',' ');
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("likes").child(evento.getNombre()).child(comentario.getHora()).child(userName).removeValue();



    }



    private void like(Comentario comentario,int position,ViewGroup listView) {

        ListView yourListView= (ListView) listView;
        //notifyDataSetChanged();
        View v = yourListView.getChildAt(position -
                yourListView.getFirstVisiblePosition());

        if(v == null)
            return;

        ImageButton like = v.findViewById(R.id.likeicon);
        like.setTag("liked");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrOfStr = user.getEmail().split("@");
        String userName = arrOfStr[0];
        userName=userName.replace('.',' ');
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("likes").child(evento.getNombre()).child(comentario.getHora()).child(userName).push().setValue(true);
        //eventLiked(comentario,position,listView);
    }

    private void eventLiked(Comentario comentario,int position,ViewGroup listView){
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
                //ComentariosListAdapter.this.notifyDataSetChanged();


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

    private void actualizarArrayList(int position) {

    }


}

