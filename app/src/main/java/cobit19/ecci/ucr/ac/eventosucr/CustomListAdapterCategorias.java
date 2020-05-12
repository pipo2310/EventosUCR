package cobit19.ecci.ucr.ac.eventosucr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListAdapterCategorias extends ArrayAdapter<Categoria> {
    private final Activity context;
    private final ArrayList<Categoria> itemname;
    public CustomListAdapterCategorias(Activity context, ArrayList<Categoria> itemname) {
        super(context, R.layout.lista_eventos_superusuario, itemname);
        this.context = context;
        this.itemname = itemname;

    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_categorias_buscar, null, true);
        ImageView categoriaIzquierda=(ImageView)rowView.findViewById(R.id.imagen);
        categoriaIzquierda.setBackgroundResource(R.drawable.ucr_evento_img);
        TextView nombreCatIzq=(TextView)rowView.findViewById(R.id.nombreCategoria);
        nombreCatIzq.setText(itemname.get(position).getNombre());
        //categoriaIzquierda.setImageResource(R.drawable.ucr_evento_img);//En el futuro se saca la imagen de la base con la posicion de la categoria
        ImageView categoriaDerecha=(ImageView)rowView.findViewById(R.id.imagen2);
        categoriaDerecha.setBackgroundResource(R.drawable.ucr_evento_img);
        TextView nombreCatDer=(TextView)rowView.findViewById(R.id.nombreCategoriaDer);
        nombreCatDer.setText(itemname.get(position).getNombre());
        //categoriaDerecha.setImageResource(R.drawable.ucr_evento_img);//En el futuro se saca la imagen de la base con la posicion de la categoria

        return rowView;
    }
}
