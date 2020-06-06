package cobit19.ecci.ucr.ac.eventosucr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificacionProximoEvento extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "1" ;
    private final static String default_notification_channel_id = "default" ;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void  showNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id)
                .setSmallIcon(R.drawable.calendaricon)
                .setContentTitle("Eventos UCR")
                .setContentText("Tienes un evento mañana NOMBRE EVENTO, HORA EVENTO")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_DEFAULT ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "ProximoEvento" , importance) ;
            builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
    }

}
