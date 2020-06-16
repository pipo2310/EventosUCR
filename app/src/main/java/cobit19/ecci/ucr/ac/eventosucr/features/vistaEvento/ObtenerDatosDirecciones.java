package cobit19.ecci.ucr.ac.eventosucr.features.vistaEvento;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ObtenerDatosDirecciones extends AsyncTask<Object, String, String> {
    GoogleMap mapa;
    String url;
    LatLng origen;
    LatLng destino;
    HttpURLConnection httpURLConnection = null;
    String datos = "";
    InputStream inputStream = null;
    public Context context;

    public ObtenerDatosDirecciones(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... parametros) {
        mapa = (GoogleMap)parametros[0];
        url = (String)parametros[1];
        origen = (LatLng)parametros[2];
        destino = (LatLng)parametros[3];

        try{
            //Para hacer la conexion http y obtener las rutas
            URL miUrl = new URL(url);
            httpURLConnection = (HttpURLConnection)miUrl.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            datos = sb.toString();
            bufferedReader.close();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return datos;
    }


    @Override
    protected void onPostExecute(String s){

        //Agarra el resultado obtenido del URL
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0)
                    .getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

            int count = jsonArray.length();
            String[] polylineArray = new String[count];

            JSONObject jsonObject1;

            for(int i=0; i<count; i++){
                jsonObject1 = jsonArray.getJSONObject(i);
                String polyline= jsonObject1.getJSONObject("polyline").getString("points");
                polylineArray[i] = polyline;
            }

            int count2 = polylineArray.length;

            for(int i=0; i<count2; i++){
                PolylineOptions opcionesLinea = new PolylineOptions();
                opcionesLinea.color(Color.GRAY);
                opcionesLinea.width(10);
                opcionesLinea.addAll(PolyUtil.decode(polylineArray[i]));

                mapa.addPolyline(opcionesLinea);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
