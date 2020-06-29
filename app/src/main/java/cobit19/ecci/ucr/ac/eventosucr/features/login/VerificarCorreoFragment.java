package cobit19.ecci.ucr.ac.eventosucr.features.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cobit19.ecci.ucr.ac.eventosucr.MainActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificarCorreoFragment extends Fragment {
    View view;
    // Recursos de la vista
    private Button enviarCorreo;
    private TextView loginBtn;
    private TextView msj_correo;
    private TextView irBtn;
    private EditText correo;
    // Autenticacion de Firebase
    private FirebaseUser user;
    // Validacion de correo
    private final String emailPattern = "^[a-z]+(\\.[a-z]+)*@(ecci.)*ucr.ac.cr$";
    private String emailAntiguo;
    private Pattern pattern;
    // Validacion de inicio de sesion
    private boolean correoValido;

    public VerificarCorreoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_verificar_correo, container, false);

        // Usuario que quiere registrarse
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Se envia el correo de verificacion
        user.sendEmailVerification();

        // Patron de validacion
        pattern = Pattern.compile(emailPattern);

        // Obtenemos recursos de la vista
        loginBtn = view.findViewById(R.id.verificar_correo_login);
        correo = view.findViewById(R.id.verificar_correo_nombre_usuario);
        enviarCorreo = view.findViewById(R.id.verificar_correo_boton);
        msj_correo = view.findViewById(R.id.verificar_correo_msj_correo);
        irBtn = view.findViewById(R.id.verificar_correo_boton_2);

        // Guardamos el email con el que se registro en primer lugar
        emailAntiguo = user.getEmail();
        correo.setText(emailAntiguo);
        correoValido = true;

        // Agregamos la accion que se quiere hacer cuando se presione el boton de reenviar correo
        enviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reenviarCorreo();
            }
        });

        // Agregamos la accion que se quiere hacer cuando se presione el boton de ir a Login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irALogin();
            }
        });

        irBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irALogin();
            }
        });

        // Agregamos la accion que se desea ver cuando se escriba el correo
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { validarCorreo(s); }

            @Override
            public void afterTextChanged(Editable s) { validarCambio(); }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void reenviarCorreo(){
        if (!emailAntiguo.equals(correo.getText().toString())) {
            user.updateEmail(correo.getText().toString());
        }
        user.sendEmailVerification();
    }

    public void validarCorreo(CharSequence str){
        Matcher matcher = pattern.matcher(str);
        if(matcher.matches()){
            msj_correo.setText("");
            correoValido = true;
        }else{
            msj_correo.setText("Debe ingresar un email valido de la ucr");
            correoValido = false;
            enviarCorreo.setEnabled(false);
            enviarCorreo.setBackgroundResource(R.drawable.boton_login_disabled);
        }
    }

    public void validarCambio(){
        if(correoValido){
            enviarCorreo.setEnabled(true);
            enviarCorreo.setBackgroundResource(R.drawable.boton_login_celeste);
        }else{
            enviarCorreo.setEnabled(false);
            enviarCorreo.setBackgroundResource(R.drawable.boton_login_disabled);
        }
    }

    public void irALogin(){
        FirebaseAuth.getInstance().signOut();
        showSelectedFragment(new LoginFragment());
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
