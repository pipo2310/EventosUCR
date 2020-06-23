package cobit19.ecci.ucr.ac.eventosucr.features.notificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import cobit19.ecci.ucr.ac.eventosucr.MenuActivity;
import cobit19.ecci.ucr.ac.eventosucr.R;
import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;
import cobit19.ecci.ucr.ac.eventosucr.shared.UtilDates;


public class AlertManager extends BroadcastReceiver {

    public static final String EVENTO = "evento";
    String GROUP_KEY_EVENTO_PROXIMO = "eventoProximo";

    @Override
    public void onReceive(final Context context, Intent intent) {



        //Obtener la referencia de Firebase
       FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrOfStr = user.getEmail().split("@");
        String userName = arrOfStr[0];

        //FALTA RECUPERAR EL USUARIO Y EVENTO
        db.collection("meInteresaUsuarioEvento").document(userName)
                .collection("eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //int i = 0;
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
                                                eventoUsuario.getHoraInicio(), true, eventoUsuario);
                                    }
                                    //i++;
                                }
                            }



                        }
                    }
                });



    }

    //El parametro "caso" sirve para saber si hay que irse a la vista de favoritos o a la vista de un evento
    public void crearNotificacion(Context context, String titulo, String mensaje, boolean caso, Evento evento){

        //id único para cada notificación
        //Se envía en notificationManagerCompat.notify y hace que una notificación no le "caiga encima" a otra
        //CCB-46 D:Katherine N:Javier
        int idNotif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);



        Intent notificationIntent = new Intent(context, MenuActivity.class);
        if(caso){
            notificationIntent.putExtra("From", "notifFragVista");
            notificationIntent.putExtra(EVENTO, evento);
        }else{
            notificationIntent.putExtra("From", "notifFragFavoritos");
        }


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MenuActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.calendar_azul)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setGroup(GROUP_KEY_EVENTO_PROXIMO)
                .setColor(Color.parseColor("#005DA4"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setStyle(new NotificationCompat.BigTextStyle(builder));
        builder.setContentIntent(pendingIntent).build();
        builder.setAutoCancel(true);

        notificationManager.notify(idNotif, builder.build());

        SystemClock.sleep(2000);

        if(notificationManager.getActiveNotifications().length > 1){
            int idNotifGroup = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            //Se crea la notificacion de grupo
            NotificationCompat.Builder groupBuilder = new NotificationCompat.Builder(context, "1")
                    .setSmallIcon(R.drawable.calendar_azul)
                    .setContentTitle("Eventos UCR")
                    .setGroup(GROUP_KEY_EVENTO_PROXIMO)
                    .setGroupSummary(true)
                    .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                    .setColor(Color.parseColor("#005DA4"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            groupBuilder.setStyle(new NotificationCompat.BigTextStyle(builder));
            groupBuilder.setContentIntent(pendingIntent).build();
            groupBuilder.setAutoCancel(true);

            notificationManager.notify(idNotifGroup, groupBuilder.build());

        }


    }

}
