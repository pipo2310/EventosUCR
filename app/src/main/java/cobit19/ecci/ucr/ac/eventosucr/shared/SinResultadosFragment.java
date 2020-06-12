package cobit19.ecci.ucr.ac.eventosucr.shared;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cobit19.ecci.ucr.ac.eventosucr.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SinResultadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SinResultadosFragment extends Fragment {

    public SinResultadosFragment() {
        // Required empty public constructor
    }


    public static SinResultadosFragment newInstance(String param1, String param2) {
        SinResultadosFragment fragment = new SinResultadosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sin_resultados, container, false);
    }
}
