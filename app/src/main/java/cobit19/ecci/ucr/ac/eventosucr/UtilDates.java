package cobit19.ecci.ucr.ac.eventosucr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilDates {

    // Usuario por defecto de la app
    public static final String CORREO_UCR_USUARIO = "walter.bonillagutierrez@ucr.ac.cr";

    public static Date parsearaDate(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date;

        try {
            date = sdf.parse(fecha);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }

        return date;
    }

    public static String parsearaString(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        return sdf.format(fecha);
    }

    public static String obtenerFechaParaExplorarEventoCarta(Calendar fecha) {
        String fechaParaExplorarEvenoCarta;
        SimpleDateFormat month_date = new SimpleDateFormat("LLL");
        fechaParaExplorarEvenoCarta = month_date.format(fecha.getTime());
        fechaParaExplorarEvenoCarta += "\n" + fecha.get(Calendar.DAY_OF_MONTH);
        return fechaParaExplorarEvenoCarta;
    }
}
