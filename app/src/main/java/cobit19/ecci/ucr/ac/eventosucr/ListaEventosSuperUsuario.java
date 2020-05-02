package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ListaEventosSuperUsuario extends AppCompatActivity {
ArrayList<Evento>eventos;
    ListView list;
    public final static String EXTRA_MESSAGE="evento";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_super_usuario);
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvento();
            }
        });
        leerEventos();
    }

    public void addEvento() {
        Intent intent = new Intent(this, CrearEvento.class);


        // Deseo recibir una respuesta: startActivityForResult()
        startActivity(intent);
        //finish();
    }


    public void leerEventos() {
        Evento evento=new Evento();
        eventos=evento.leerEventos(getApplicationContext());

        CustomListAdapter adapter = new CustomListAdapter(this, eventos);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                Evento eventoSeleccionado = eventos.get(position);
                //Irse a otra pantalla con los extras desde esta para no hacer otra llamada a la base en la siguiente actividad
                cambiarDePantalla(eventoSeleccionado);


            }
        });

    }

    public void cambiarDePantalla(Evento evento) {
        Intent intent = new Intent(this, ModificarEliminarEvento.class);
        intent.putExtra(EXTRA_MESSAGE, evento);

        // Deseo recibir una respuesta: startActivityForResult()
        startActivityForResult(intent, 0);
        //finish();
    }
}
