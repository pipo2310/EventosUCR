package cobit19.ecci.ucr.ac.eventosucr.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Categoria.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoriaDao categoriaDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {

                /*Categoria[] categorias = new Categoria[] {
                        new Categoria("gastronomia"),
                        new Categoria("teatro"),
                        new Categoria("danza"),
                        new Categoria("expo"),
                        new Categoria("musica"),
                        new Categoria("cine"),
                        new Categoria("literatura"),
                        new Categoria("taller"),
                        new Categoria("feria"),
                        new Categoria("conversatorio"),
                        new Categoria("convocatoria"),
                        new Categoria("otras")
                };


                // Populate the database in the background.
                // If you want to start with more words, just add them.
                CategoriaDao dao = INSTANCE.categoriaDao();
                dao.delete(categorias);
                dao.insert(categorias);*/
            });
        }
    };
}
