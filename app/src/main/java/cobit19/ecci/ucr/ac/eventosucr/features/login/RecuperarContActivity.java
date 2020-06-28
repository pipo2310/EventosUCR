package cobit19.ecci.ucr.ac.eventosucr.features.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

import cobit19.ecci.ucr.ac.eventosucr.R;

public class RecuperarContActivity extends AppCompatActivity {

    EditText correoUsuario;
    Button recuperarBoton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_cont);
        correoUsuario = (EditText) findViewById(R.id.correoUsuario);
        recuperarBoton = (Button) findViewById(R.id.recuperarBoton);
        mAuth = FirebaseAuth.getInstance();
        recuperarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCorreo();

            }
        });

    }

    private void enviarCorreo(){
        recuperarBoton.setEnabled(false);
        ArrayList<String> correosPermitidos = new ArrayList<String>(
                Arrays.asList("ucr.ac.cr","gmail.com","hotmail.com","ecci.ucr.ac.cr"));
        String correo = correoUsuario.getText().toString();

        String [] dominio = correo.split("@");


        if(correoUsuario.equals("") || !correosPermitidos.contains(dominio[1])){
            Toast.makeText(this,"Correo inválido",Toast.LENGTH_SHORT).show();
        }else{
            mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RecuperarContActivity.this, "El correo se envió existosamente. Revise su correo", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RecuperarContActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RecuperarContActivity.this, "Error al enviar el correo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        recuperarBoton.setEnabled(true);
    }
}
