package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import cobit19.ecci.ucr.ac.eventosucr.MenuActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;

public class BuscarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String BUSCAR = "buscar";
    public static final String QUERY = "query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        SearchView searchView = (SearchView) findViewById(R.id.buscar_barra);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(MenuActivity.ACTIVIDAD, BuscarActivity.BUSCAR);
        intent.putExtra(BuscarActivity.QUERY, query.toLowerCase());
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
