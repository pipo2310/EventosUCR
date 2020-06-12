package cobit19.ecci.ucr.ac.eventosucr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGridAdapterCategorias extends ArrayAdapter<Categoria> {
    private final Activity context;
    private final ArrayList<Categoria> itemname;
    public CustomGridAdapterCategorias(Activity context, ArrayList<Categoria> itemname) {
        super(context, R.layout.lista_categorias_buscar, itemname);
        this.context = context;
        this.itemname = itemname;

    }

    public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_categorias_buscar, null, true);
        ImageView categoriaImagen=(ImageView)rowView.findViewById(R.id.imagenCatego);
        categoriaImagen.setBackgroundResource(R.drawable.ucr_evento_img);
        TextView nombreCatego=(TextView)rowView.findViewById(R.id.nombreCatego);
        nombreCatego.setText(itemname.get(position).getNombre());

        return rowView;
    }
}
