package cobit19.ecci.ucr.ac.eventosucr.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoriaRepository {

    private CategoriaDao categoriaDao;
    private LiveData<List<Categoria>> allCategorias;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    CategoriaRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        categoriaDao = db.categoriaDao();
        allCategorias = categoriaDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Categoria>> getAllCategorias() {
        return allCategorias;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Categoria... categorias) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            categoriaDao.insert(categorias);
        });
    }
}
