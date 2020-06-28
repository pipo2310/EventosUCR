package cobit19.ecci.ucr.ac.eventosucr.features.administracionEventosUsuario;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

import cobit19.ecci.ucr.ac.eventosucr.R;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(requireActivity(), R.style.DialogTheme, (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}
