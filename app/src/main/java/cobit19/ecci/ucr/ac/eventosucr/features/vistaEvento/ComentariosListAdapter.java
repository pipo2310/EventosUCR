package cobit19.ecci.ucr.ac.eventosucr.features.vistaEvento;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Comentario;

public class ComentariosListAdapter extends ArrayAdapter<Comentario> {
    private final Activity context;
    private final ArrayList<Comentario> comentarios;

    public ComentariosListAdapter(Activity context, ArrayList<Comentario> comentarios) {
        super(context, R.layout.comentario_evento, comentarios);
        this.context = context;
        this.comentarios = comentarios;

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

        return rowView;
    }
}
