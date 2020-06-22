package cobit19.ecci.ucr.ac.eventosucr.features.notificaciones;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cobit19.ecci.ucr.ac.eventosucr.core.models.Evento;

public class NotificacionCambioEvento extends IntentService {

    public static final String NOTIFICATION_CHANNEL_ID = "1" ;
    private static final String TAG = "NotificacionCambioEvent";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *0
     *
     */
    public NotificacionCambioEvento() {
        super("NotificacionCambioEvento");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //android.os.Debug.waitForDebugger();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrOfStr = user.getEmail().split("@");
        String userName = arrOfStr[0];
        db.collection("meInteresaUsuarioEvento").document(userName)
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
                                                    evento.getNombre() + " ha sido modificado", true, evento);
                                    break;
                                case REMOVED:
                                    evento = dc.getDocument().toObject(Evento.class);
                                    db.collection("eventos")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        // Creamos la lista de eventos de firebase
                                                        boolean eventoCancelado = true;
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            final Evento eventoRecuperado = document.toObject(Evento.class);
                                                            if(evento.getNombre().equals(eventoRecuperado.getNombre())){
                                                                eventoCancelado = false;
                                                            }
                                                        }
                                                        if(eventoCancelado){
                                                            alertManager.crearNotificacion(getApplicationContext(),
                                                                    "Evento Cancelado","El evento " +
                                                                            evento.getNombre() + " ha sido cancelado", false, evento);
                                                        }
                                                    }
                                                }
                                            });
                                    break;
                            }
                        }
                    }
                });
    }
}
