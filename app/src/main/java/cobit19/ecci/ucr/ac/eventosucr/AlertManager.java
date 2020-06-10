package cobit19.ecci.ucr.ac.eventosucr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.BuscarActivity;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;

public class AlertManager extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {



        //Obtener la referencia de Firebase
       FirebaseFirestore db = FirebaseFirestore.getInstance();

        //FALTA RECUPERAR EL USUARIO Y EVENTO
        db.collection("meInteresa").document("Katherine")
                .collection("eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Evento eventoUsuario = new Evento();
                        String hola = "";
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                eventoUsuario = document.toObject(Evento.class);
                                if(eventoUsuario != null){
                                    
                                }
                            }


                            crearNotificacion(context, "Evento Próximo", hola);
                        }
                    }
                });



    }

    public void crearNotificacion(Context context, String titulo, String mensaje){
        //PendingIntent notifIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        Intent notificationIntent = new Intent(context, BuscarActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(BuscarActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);

        //Se crea la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.calendaricon)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                builder.setContentIntent(pendingIntent).build();


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(
                1, builder.build());



    }

}
