package cobit19.ecci.ucr.ac.eventosucr;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import cobit19.ecci.ucr.ac.eventosucr.features.login.LoginActivity;
import cobit19.ecci.ucr.ac.eventosucr.features.notificaciones.AlertManager;
import cobit19.ecci.ucr.ac.eventosucr.features.notificaciones.NotificacionCambioEvento;

public class MainActivity extends AppCompatActivity {

    private final String NOTIFICATION_CHANNEL_ID = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos el firebase auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Verificar si hay un usuario inscrito en la app
        if(mAuth.getCurrentUser() == null) {
            // Si no hay un usuario se envia a la pantalla de login
            cambiarDePantalla(LoginActivity.class);
        }else{
            //crea el canal de notificaciones
            crearCanalNotificacion();
            asignarAlarma();
            lanzarServicio();
            // Si ya hay un usuario se envia a la vista de explorar
            cambiarDePantalla(MenuActivity.class);
        }
    }

    public void cambiarDePantalla(Class<?> activity) {
        Intent a =new Intent(this, activity);
        startActivity(a);
        finish();
    }

    //Notificaciones
    public void crearCanalNotificacion(){
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_DEFAULT ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "ProximoEvento" , importance) ;
            notificationChannel.setDescription("Canal de notificaciones de eventos");

            NotificationManager mNotificationManager = getSystemService(NotificationManager.class) ;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void asignarAlarma() {
        Calendar alertTime = Calendar.getInstance();

        alertTime.set(Calendar.HOUR_OF_DAY, 14);
        alertTime.set(Calendar.MINUTE, 48);
        alertTime.set(Calendar.SECOND, 0);

        Intent alertIntent = new Intent(this, AlertManager.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alertIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void lanzarServicio(){
        Intent i= new Intent(this, NotificacionCambioEvento.class);
        startService(i);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
