package cobit19.ecci.ucr.ac.eventosucr;

import com.google.firebase.Timestamp;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.*;

public class UtilDatesTest {

    @Test
    public void esMañana() {
        Timestamp timestamp = Timestamp.now();
        Date tomorrow = timestamp.toDate();
        assertEquals("La fecha no es mañana", false , new UtilDates().esMañana(tomorrow));
    }
}