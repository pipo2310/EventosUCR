package cobit19.ecci.ucr.ac.eventosucr.features.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import cobit19.ecci.ucr.ac.eventosucr.MainActivity;
import cobit19.ecci.ucr.ac.eventosucr.MenuActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private View view;
    private EditText contrasenna;
    private EditText usuario;
    private ImageView showPass;
    // Variable para acceder a Firebase Authentication
    private FirebaseAuth mAuth;
    // Boton de iniciar sesion
    Button iniciarSesionBtn;
    // Boton de registro
    TextView registrarseBtn;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // Inicializamos Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Obtenemos recursos de la vista
        usuario = view.findViewById(R.id.login_nombre_usuario);
        contrasenna = view.findViewById(R.id.login_contrasenna);
        showPass = view.findViewById(R.id.show_pass_btn);
        iniciarSesionBtn = view.findViewById(R.id.login_boton_is);
        registrarseBtn = view.findViewById(R.id.login_registro);

        // Agregamos la accion que se quiere hacer cuando se presione el boton de iniciar sesion
        iniciarSesionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        // Agregamos la accion que se quiere hacer cuando se presione el boton de mostrar y ocultar contraseña
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePass();
            }
        });

        // Agregamos la accion que se quiere hacer cuando se presione el boton de crear un cuenta
        registrarseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irARegitrar();
            }
        });

        return view;
    }

    public void irARegitrar(){
        showSelectedFragment(new RegistroFragment());
    }

    public void iniciarSesion(){
        iniciarSesionBtn.setEnabled(false);
        if(usuario.length() > 0 && contrasenna.length() > 0) {
            mAuth.signInWithEmailAndPassword(usuario.getText().toString(), contrasenna.getText().toString())
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Inicio de sesion exitoso
                                FirebaseUser user = mAuth.getCurrentUser();
                                // Se verifica si el usuario tiene el email verificado
                                if(user.isEmailVerified()){
                                    // Se envia al usuario a la vista principal
                                    cambiarDePantalla(MainActivity.class);
                                }else{
                                    // Se envia a la vista de Verificar email
                                    showSelectedFragment(new VerificarCorreoFragment());
                                }
                            } else {
                                // Si el inicio de sesion falla, se le indica al usuario
                                Toast.makeText(getActivity(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            // Se le indica al usuario que debe agregar algo en los campos
            Toast.makeText(getActivity(), "Debe escribir un usario y una contraseña", Toast.LENGTH_SHORT).show();
        }
        iniciarSesionBtn.setEnabled(true);
    }

    /**
     * Metodo para mostrar u ocultar la contraseña cada vez que se selecciona el boton correspondiente
     */
    public void showHidePass(){
        if(contrasenna.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            showPass.setImageResource(R.drawable.show_password);

            //Mostrar contraseña
            contrasenna.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
            showPass.setImageResource(R.drawable.hide_password);

            //Ocultar contraseña
            contrasenna.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }

    public void cambiarDePantalla(Class<?> activity) {
        iniciarSesionBtn.setEnabled(true);
        Intent a =new Intent(getActivity(), activity);
        startActivity(a);
        getActivity().finish();
    }

    /**
     * Metodo que se usa para indicar cual es el feagment que se va a ver
     */
    private void showSelectedFragment(Fragment fragment){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_container_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
