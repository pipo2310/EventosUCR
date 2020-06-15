package cobit19.ecci.ucr.ac.eventosucr;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilDates {

    public static Date parsearaDate(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
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
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        return sdf.format(fecha);
    }

    public static String obtenerFechaParaExplorarEventoCarta(Calendar fecha) {
        String fechaParaExplorarEvenoCarta;
        SimpleDateFormat month_date = new SimpleDateFormat("LLL");
        fechaParaExplorarEvenoCarta = month_date.format(fecha.getTime());
        fechaParaExplorarEvenoCarta += "\n" + fecha.get(Calendar.DAY_OF_MONTH);
        return fechaParaExplorarEvenoCarta;
    }

    public static boolean esMañana(Date d){
        return DateUtils.isToday(d.getTime()-DateUtils.DAY_IN_MILLIS);
    }
}
