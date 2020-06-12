package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import cobit19.ecci.ucr.ac.eventosucr.features.administracionEventosUsuario.CrearEvento;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent a =new Intent(this, MenuActivity.class);
        startActivity(a);
        //finish();
    }


    public void cambiarDePantalla() {
        Intent a =new Intent(this, CrearEvento.class);
        startActivity(a);
    }

    public void irMenu(){
        Intent a =new Intent(this, MenuActivity.class);
        startActivity(a);
    }
}
