package cobit19.ecci.ucr.ac.eventosucr.features.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // Obtenemos recursos de la vista
        usuario = view.findViewById(R.id.login_nombre_usuario);
        contrasenna = view.findViewById(R.id.login_contrasenna);
        showPass = view.findViewById(R.id.show_pass_btn);
        final Button iniciarSesionBtn = view.findViewById(R.id.login_boton_is);

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

        return view;
    }

    public void iniciarSesion(){
        // TODO: falta validar en BD
        // Se envia al usuario a la vista principal
        Intent a =new Intent(getActivity(), MenuActivity.class);
        startActivity(a);
        getActivity().finish();
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
}
