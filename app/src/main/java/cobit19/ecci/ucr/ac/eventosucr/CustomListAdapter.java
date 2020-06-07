package cobit19.ecci.ucr.ac.eventosucr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Imagen;

public class CustomListAdapter extends ArrayAdapter<Evento> {
    private final Activity context;
    private final ArrayList<Evento> itemname;
    private final ArrayList<ImageView> imagenes;

        public CustomListAdapter(Activity context, ArrayList<Evento> itemname, ArrayList<ImageView> imagenes) {
        super(context, R.layout.lista_eventos_superusuario, itemname);
        this.context = context;
        this.itemname = itemname;
        this.imagenes=imagenes;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_eventos_superusuario, null, true);
        TextView nombre = (TextView) rowView.findViewById(R.id.name);
        nombre.setText(itemname.get(position).getNombre());
        TextView institucion = (TextView) rowView.findViewById(R.id.institucion);
        institucion.setText(itemname.get(position).getInstitucion(context).getNombre());
        ImageView imagenEvento=(ImageView)rowView.findViewById(R.id.image);
        //Picasso.with(getContext()).load(itemname.get(position).getImagen()).into(imagenEvento);
        imagenEvento.setImageDrawable(imagenes.get(position).getDrawable());

        return rowView;
    }
}
