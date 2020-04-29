package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaEventosSuperUsuario extends AppCompatActivity {
ArrayList<Evento>eventos;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_super_usuario);
        leerEventos();
    }

    public void leerEventos() {
        Evento evento=new Evento();
        eventos=evento.leerEventos(getApplicationContext());

        CustomListAdapter adapter = new CustomListAdapter(this, eventos);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

    }
}
