package cobit19.ecci.ucr.ac.eventosucr.features.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {
    View view;

    // Boton de registro
    TextView tieneCuentaBtn;

    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registro, container, false);

        // Obtenemos de la vista el boton que indica que ya creo una cuenta
        tieneCuentaBtn = view.findViewById(R.id.registro_login);

        // Agregamos la accion que se quiere hacer cuando se presione el boton de ya tiene cuenta
        tieneCuentaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irALogin();
            }
        });


        // Retornamos la vista inflada
        return view;
    }

    public void registrar(){

    }

    public void irALogin(){
        showSelectedFragment(new LoginFragment());
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
                .replace(R.id.login_container_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
