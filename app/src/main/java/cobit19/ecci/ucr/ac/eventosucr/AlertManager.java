package cobit19.ecci.ucr.ac.eventosucr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cobit19.ecci.ucr.ac.eventosucr.features.favoritos.FavoritosFragment;
import cobit19.ecci.ucr.ac.eventosucr.fragments.VistaEventoFragment;

public class AlertManager extends BroadcastReceiver {

    private final String default_notification_channel_id = "default";
    private final String NOTIFICATION_CHANNEL_ID = "1";

    @Override
    public void onReceive(Context context, Intent intent) {

        //Obtener la referencia de Firebase
       /* FirebaseFirestore db = FirebaseFirestore.getInstance();

        //FALTA RECUPERAR EL USUARIO Y EVENTO
        db.collection("MeInteresa").document(Constantes.CORREO_UCR_USUARIO)
                .collection("eventosUsuario")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Se recuperan los eventos y se verifican las fechas
                    }
                });*/

        crearNotificacion(context, "Evento Próximo", "", "Alert");
    }

    public void crearNotificacion(Context context, String titulo, String mensaje, String msjAlerta){
        //PendingIntent notifIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        //Se crea la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id)
                .setSmallIcon(R.drawable.calendaricon)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setTicker(msjAlerta)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Irse a la actividad cuando se presione la notificacion
        Intent proximoEvento = new Intent(context, FavoritosFragment.class) ;
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(VistaEventoFragment.class);
        taskStackBuilder.addNextIntent(proximoEvento);

        PendingIntent eventoIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(eventoIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE) ;

        //Se crea un canal para la notificacion
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_DEFAULT ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "ProximoEvento" , importance) ;
            builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }

        mNotificationManager.notify(1, builder.build());

    }

}
