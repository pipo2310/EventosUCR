package cobit19.ecci.ucr.ac.eventosucr.features.buscar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarFragment extends Fragment {

    public BuscarFragment() {}
    public BuscarFragment(String query) {
        System.out.println(query);
    }

    public static BuscarFragment newInstance(String param1, String param2) {
        return new BuscarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        // Aqui es donde vamos a meter los fragmentos a la vista
        Button button =  view.findViewById(R.id.buscar_boton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuscarActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
