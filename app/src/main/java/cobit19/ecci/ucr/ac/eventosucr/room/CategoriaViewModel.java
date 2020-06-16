package cobit19.ecci.ucr.ac.eventosucr.room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoriaViewModel extends AndroidViewModel {

    private CategoriaRepository categoriaRepository;

    private LiveData<List<Categoria>> allCategorias;

    public CategoriaViewModel(Application application) {
        super(application);
        categoriaRepository = new CategoriaRepository(application);
        allCategorias = categoriaRepository.getAllCategorias();
    }

    public LiveData<List<Categoria>> getAllCategorias() { return allCategorias; }

    public void insert(Categoria... categorias) { categoriaRepository.insert(categorias); }

    public void delete(Categoria... categorias) {
        categoriaRepository.delelte(categorias);
    }
}
