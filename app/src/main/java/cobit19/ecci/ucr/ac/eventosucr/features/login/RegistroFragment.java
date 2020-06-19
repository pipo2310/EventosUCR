package cobit19.ecci.ucr.ac.eventosucr.features.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {
    View view;

    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registro, container, false);



        // Retornamos la vista inflada
        return view;
    }

    public void registrar(){

    }

    public void cambiarDePantalla(Class<?> activity) {
        Intent a =new Intent(getActivity(), activity);
        startActivity(a);
        getActivity().finish();
    }

    /**
     * Metodo que se usa para indicar cual es el fragment que se va a ver
     */
    private void showSelectedFragment(Fragment fragment){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
