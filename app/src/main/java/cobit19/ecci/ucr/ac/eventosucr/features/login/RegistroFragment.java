package cobit19.ecci.ucr.ac.eventosucr.features.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    // Recursos de la vista
    private Button registrar;
    private TextView tieneCuentaBtn;
    private TextView msj_correo;
    private TextView msj_contrasenna;
    private EditText correo;
    private EditText contrasenna;
    private EditText confirmarContrasenna;
    private ImageView showPass;
    private ImageView showPassConf;
    // Autenticacion de Firebase
    private FirebaseAuth mAuth;
    // Validacion de correo
    private final String emailPattern = "^[a-z]+(\\.[a-z]+)*@(ecci.)*ucr.ac.cr$";
    private Pattern pattern;
    // Validacion de inicio de sesion
    private boolean correoValido;
    private boolean passValida;
    private String correoUser;

    public RegistroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registro, container, false);

        // Instancia de firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // Patron de validacion
        pattern = Pattern.compile(emailPattern);

        // Obtenemos recursos de la vista
        tieneCuentaBtn = view.findViewById(R.id.registro_login);
        correo = view.findViewById(R.id.registro_nombre_usuario);
        contrasenna = view.findViewById(R.id.registro_contrasenna);
        confirmarContrasenna = view.findViewById(R.id.registro_conf_contrasenna);
        registrar = view.findViewById(R.id.registro_boton_is);
        msj_correo = view.findViewById(R.id.registro_msj_correo);
        msj_contrasenna = view.findViewById(R.id.registro_msj_contrasenna);
        showPass = view.findViewById(R.id.show_pass_btn_contrasenna);
        showPassConf = view.findViewById(R.id.show_pass_btn_conf_contrasenna);

        // Registro desabilitado
        registrar.setEnabled(false);
        correoValido = false;
        passValida = false;

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

        // Agregamos la accion que se quiere hacer cuando se presione el boton de mostrar y ocultar contraseña
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePass();
            }
        });

        // Agregamos la accion que se quiere hacer cuando se presione el boton de mostrar y ocultar contraseña
        showPassConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePassConf();
            }
        });

        // Agregamos la accion que se desea ver cuando se escriba el correo
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validarCorreo(s);
            }

            @Override
            public void afterTextChanged(Editable s) { validarRegistro(); }
        });

        TextWatcher valContrasenna = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (contrasenna.getText().length() > 5) {
                    validarContrasenna();
                }else{
                    msj_contrasenna.setText("La contraseña debe tener minimo 6 caracteres");
                    passValida = false;
                    registrar.setEnabled(false);
                    registrar.setBackgroundResource(R.drawable.boton_login_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {validarRegistro();}
        };

        // Agregamos la accion que se desea ver cuando se la confirmacion de la contraseña
        contrasenna.addTextChangedListener(valContrasenna);

        // Agregamos la accion que se desea ver cuando se la confirmacion de la contraseña
        confirmarContrasenna.addTextChangedListener(valContrasenna);

        // Retornamos la vista inflada
        return view;
    }

    public void validarCorreo(CharSequence s){
        String str = s.toString();
        str = str.toLowerCase();
        Matcher matcher = pattern.matcher(str);
        if(matcher.matches()){
            msj_correo.setText("");
            correoValido = true;
            correoUser = str;
        }else{
            msj_correo.setText("Debe ingresar un email válido de la ucr");
            correoValido = false;
            registrar.setEnabled(false);
            registrar.setBackgroundResource(R.drawable.boton_login_disabled);
        }
    }

    public void validarContrasenna(){
        if(confirmarContrasenna.getText().toString().equals(contrasenna.getText().toString())){
            msj_contrasenna.setText("");
            passValida = true;
        }else{
            msj_contrasenna.setText("Las contraseñas no son iguales");
            passValida = false;
            registrar.setEnabled(false);
            registrar.setBackgroundResource(R.drawable.boton_login_disabled);
        }
    }

    public void validarRegistro(){
        if(correoValido && passValida){
            registrar.setEnabled(true);
            registrar.setBackgroundResource(R.drawable.boton_login_celeste);
        }
    }

    public void registrarUsuario(){
        registrar.setEnabled(false);
        mAuth.createUserWithEmailAndPassword(correoUser, contrasenna.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Se envia al usuario a la vista de verificar email
                            irAVerificacion();
                        } else {
                            // Si el registro falla, se le indica al usuario
                            try{
                                FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                if(e.getErrorCode() == "ERROR_EMAIL_ALREADY_IN_USE"){
                                    Toast.makeText(getActivity(), "El correo ya está asociado a una cuenta", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                Toast.makeText(getActivity(), "No se pudo realizar el registro, intente de nuevo mas tarde", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        registrar.setEnabled(true);
    }

    public void irAVerificacion(){
        registrar.setEnabled(true);
        showSelectedFragment(new VerificarCorreoFragment());
    }

    public void irALogin(){
        showSelectedFragment(new LoginFragment());
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

    /**
     * Metodo para mostrar u ocultar la contraseña cada vez que se selecciona el boton correspondiente
     */
    public void showHidePassConf(){
        if(confirmarContrasenna.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            showPassConf.setImageResource(R.drawable.show_password);

            //Mostrar contraseña
            confirmarContrasenna.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
            showPassConf.setImageResource(R.drawable.hide_password);

            //Ocultar contraseña
            confirmarContrasenna.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
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
