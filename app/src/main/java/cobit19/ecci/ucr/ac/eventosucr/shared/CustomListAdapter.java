package cobit19.ecci.ucr.ac.eventosucr.shared;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

public class CustomListAdapter extends ArrayAdapter<Evento> {
    private final Activity context;
    private final ArrayList<Evento> itemname;

    public CustomListAdapter(Activity context, ArrayList<Evento> itemname) {
        super(context, R.layout.lista_eventos_usuario, itemname);

        this.context = context;
        this.itemname = itemname;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_eventos_usuario, null, true);
        // Nombre del evento
        TextView nombre = (TextView) rowView.findViewById(R.id.name);
        nombre.setText(itemname.get(position).getNombre());

        // Nombre de la organizador que lo creo
        TextView institucion = (TextView) rowView.findViewById(R.id.institucion);
        institucion.setText(itemname.get(position).getOrganizador());

        // Imagen del evento
        ImageView imagenEvento=(ImageView)rowView.findViewById(R.id.image);
        // Agregamos a imagen por medio de un URL
        Glide.with(rowView)
                .load(itemname.get(position).getUrlImagen())
                // Vemos si podemos utilizar o no la imagen del cache
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imagenEvento);

        return rowView;
    }
}
