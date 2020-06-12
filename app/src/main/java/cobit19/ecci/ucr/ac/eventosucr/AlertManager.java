package cobit19.ecci.ucr.ac.eventosucr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.features.buscar.BuscarActivity;
import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;
import io.grpc.okhttp.internal.Util;

public class AlertManager extends BroadcastReceiver {

    private static final String EVENTO = "evento";

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
                                    Date date = eventoUsuario.getTimestamp().toDate();
                                    //SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                                    //hola = sf.format(date);
                                    boolean esManana = UtilDates.esMañana(date);
                                    if(esManana){
                                        // Si alguno de los eventos es mañana, se envía la notificación
                                        crearNotificacion(context, "Evento Próximo", "El evento " +
                                                eventoUsuario.getNombre() + " se realizará mañana a las " +
                                                eventoUsuario.getHoraInicio(), true);
                                    }
                                }
                            }



                        }
                    }
                });



    }

    //El parametro "caso" sirve para saber si hay que irse a la vista de favoritos o a la vista de un evento
    public void crearNotificacion(Context context, String titulo, String mensaje, boolean caso){
        //PendingIntent notifIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        Intent notificationIntent = new Intent(context, MenuActivity.class);
        if(caso){
            notificationIntent.putExtra("From", "notifFragVista");
        }else{
            notificationIntent.putExtra("From", "notifFragFavoritos");
        }


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MenuActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);



        //Se crea la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.calendar_azul)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setColor(Color.parseColor("#005DA4"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setStyle(new NotificationCompat.BigTextStyle(builder));
                builder.setContentIntent(pendingIntent).build();
                builder.setAutoCancel(true);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(
                1, builder.build());



    }

}
