package cobit19.ecci.ucr.ac.eventosucr.fragments;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static org.junit.Assert.*;

public class VistaEventoFragmentTest {

    @Test
    public void obtenerUrl() {
            double latOrig = 9.9576;
            double lonOrig = 84.0816;

            double latDest = 9.982108;
            double lonDest = -84.024573;


            assertEquals("URL erróneo", "https://maps.googleapis.com/maps/api/directions/json?origin=9.9576,84.0816&destination=9.982108,-84.024573&mode=driving&key=AIzaSyAqZYlg_jAJqO6q9-tZa6-ntIZc_dgqUb4",
                    new VistaEventoFragment().obtenerUrl(new com.google.android.gms.maps.model.LatLng(latOrig,lonOrig), new LatLng(latDest, lonDest)));
        }
    }
