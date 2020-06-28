package cobit19.ecci.ucr.ac.eventosucr.features.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cobit19.ecci.ucr.ac.eventosucr.MainActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * CCB-44 Registro
 * Subtasks
 *  CCB-53
 *  CCB-54
 *  CCB-55
 *  Participantes:
 *      Fabian Alvarez
 *      Walter Bonilla
 *      Christian Rodriguez
 *
 */
public class RegistroFragment extends Fragment {
    View view;

    // Boton de registro
    Button registrar;
    TextView tieneCuentaBtn;
    EditText correo;
    EditText contrasenna;
    EditText confirmarContrasenna;
    private FirebaseAuth mAuth;

    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registro, container, false);

        mAuth = FirebaseAuth.getInstance();


        // Obtenemos de la vista el boton que indica que ya creo una cuenta
        tieneCuentaBtn = view.findViewById(R.id.registro_login);



        correo=view.findViewById(R.id.registro_nombre_usuario);
        contrasenna=view.findViewById(R.id.registro_contrasenna);
        confirmarContrasenna=view.findViewById(R.id.registro_conf_contrasenna);
        registrar=view.findViewById(R.id.registro_boton_is);

        // Agregamos la accion que se quiere hacer cuando se presione el boton de registrar
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

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

    public void registrarUsuario(){

        //Validaciones to do
        mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contrasenna.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesion exitoso
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Se envia al usuario a la vista principal
                            //cambiarDePantalla(MainActivity.class);
                        } else {
                            // Si el inicio de sesion falla, se le indica al usuario
                            Toast.makeText(getActivity(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }

                        // ...

                });

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
