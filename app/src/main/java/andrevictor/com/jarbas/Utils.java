package andrevictor.com.jarbas;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import andrevictor.com.jarbas.API.Direcoes;
import andrevictor.com.jarbas.API.NetworkUtils;

import static andrevictor.com.jarbas.MapsActivity.lugares;
import static andrevictor.com.jarbas.MapsActivity.listlatlong;


public class Utils {

    int cont = 0;

    public Direcoes getInformacao(String end){
            String json;
        Direcoes retorno;
        json = NetworkUtils.getJSONFromAPI(end);
        retorno = parseJson(json);

        return retorno;
    }

    private Direcoes parseJson(String json){
        try {
            Direcoes direcoes = new Direcoes(); //Cria o objeto direcoes



            JSONObject result = new JSONObject(json); //Pega o JSON
            JSONArray routes = result.getJSONArray("routes"); //Pega a maior classe para depois pegar o que tem dentro dela

            JSONObject objArray = routes.getJSONObject(0); //Seta o primeiro objeto (So tem um se eu entendi direito)

            JSONObject obj = objArray.getJSONObject("fare"); //subclasse de "routes" / onde fica o preco da passagem
            direcoes.setPreco(obj.getString("text")); //Aqui pego o valor da passagem total

            JSONArray objLegs = objArray.getJSONArray("legs");//subclasse de "routes" /É um array

            JSONObject objLe = objLegs.getJSONObject(0);
            JSONArray objSteps = objLe.getJSONArray("steps");//subclasse de "legs" / onde fica os passos /É um array


            for (cont = 0; cont < objSteps.length(); cont++){
                JSONObject objHtml = objSteps.getJSONObject(cont);
                lugares.add(objHtml.getString("html_instructions"));

                String polyline = objSteps.getJSONObject(cont).getJSONObject("polyline").getString("points");

                Log.i("polyline",polyline);

                for(LatLng p : decodePolyline(polyline)) {
                    listlatlong.add(p);
                }
            }

            Log.i("obj",""+ obj);
            Log.i("pol", ""+listlatlong);


            return direcoes;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }

       }

    // DECODE POLYLINE
    private List<LatLng> decodePolyline(String encoded) {

        List<LatLng> listPoints = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            Log.i("Script", "POL: LAT: "+p.latitude+" | LNG: "+p.longitude);
            listPoints.add(p);
        }
        return listPoints;
    }

}