package cobit19.ecci.ucr.ac.eventosucr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDates {

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
}
