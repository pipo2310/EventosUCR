package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.room.Categoria;
import cobit19.ecci.ucr.ac.eventosucr.shared.Util;

public class CustomGridAdapterCategorias extends ArrayAdapter<Categoria> {
    private final Activity context;
    private final List<Categoria> itemname;
    public CustomGridAdapterCategorias(Activity context, List<Categoria> itemname) {
        super(context, R.layout.lista_categorias_buscar, itemname);
        this.context = context;
        this.itemname = itemname;

    }

    public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_categorias_buscar, null, true);

        ImageView categoriaImagen=(ImageView)rowView.findViewById(R.id.imagenCatego);
        categoriaImagen.setBackgroundResource(Util.idCategoria(itemname.get(position).getCategoria()));

        TextView nombreCatego=(TextView)rowView.findViewById(R.id.nombreCatego);
        // Hacer que la primera letra sea en mayuscula
        String categoriasMostrar = itemname.get(position).getCategoria();
        categoriasMostrar = categoriasMostrar.substring(0, 1).toUpperCase() + categoriasMostrar.substring(1);
        nombreCatego.setText(categoriasMostrar);

        return rowView;
    }
}
