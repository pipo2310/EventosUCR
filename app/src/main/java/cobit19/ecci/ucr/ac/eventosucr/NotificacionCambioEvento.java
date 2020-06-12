package cobit19.ecci.ucr.ac.eventosucr;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

public class NotificacionCambioEvento extends IntentService {

    public static final String NOTIFICATION_CHANNEL_ID = "1" ;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificacionCambioEvento() {
        super("NotificacionCambioEvento");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        android.os.Debug.waitForDebugger();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("meInteresa").document("Katherine")
                .collection("eventos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            return;
                        }
                        for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                            Evento evento;
                            AlertManager alertManager = new AlertManager();
                            switch(dc.getType()){
                                case ADDED:
                                    break;
                                case MODIFIED:
                                    evento = dc.getDocument().toObject(Evento.class);
                                    alertManager.crearNotificacion(getApplicationContext(),
                                            "Evento Modificado","El evento " +
                                                    evento.getNombre() + " ha sido cancelado");
                                    break;
                                case REMOVED:
                                    evento = dc.getDocument().toObject(Evento.class);
                                    alertManager.crearNotificacion(getApplicationContext(),
                                            "Evento Cancelado","El evento " +
                                            evento.getNombre() + " ha sido cancelado");
                                    break;
                            }
                        }
                    }
                });
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO for communication return IBinder implementation
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
