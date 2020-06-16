package cobit19.ecci.ucr.ac.eventosucr.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoriaDao {
    @Query("SELECT * FROM categoria")
    LiveData<List<Categoria>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Categoria... categorias);

    @Delete
    void delete(Categoria... categorias);
}
