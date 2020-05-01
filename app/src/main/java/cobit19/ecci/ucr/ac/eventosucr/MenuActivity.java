package cobit19.ecci.ucr.ac.eventosucr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cobit19.ecci.ucr.ac.eventosucr.fragments.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView footerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        showSelectedFragment(new FavoritosFragment());

        footerMenu = (BottomNavigationView) findViewById(R.id.menu_footer);

        footerMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_buscar){
                    showSelectedFragment(new VistaEventoFragment());
                }
                else{
                    showSelectedFragment(new FavoritosFragment());
                }
                return true;
            }
        });
    }

    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
