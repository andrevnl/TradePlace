package andrevictor.com.jarbas.API;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static andrevictor.com.jarbas.Telas.TelaRota.lugares;
import static andrevictor.com.jarbas.Telas.TelaRota.listlatlong;


//Classe que pega o json e o objeto e atribui um ao outro (Não explicou nada, mas ta valendo)

public class Utils {

    int contG = 0;
    int contP = 0;

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
            JSONObject obj = objArray.getJSONObject("fare"); //subclasse de "routes" / onde fica o preco da passagem / isso e um objeto e so to nele para pegar o valor
            JSONArray obj1 = objArray.getJSONArray("legs");//subclasse de "routes" /É um array
            JSONObject objLegs = obj1.getJSONObject(0); //subclasse de "routes" / Aqui estou na legs
            JSONArray objSteps = objLegs.getJSONArray("steps");//subclasse de "legs" / onde fica os passos /É um array

            //O que ta aqui em baixo e o que to pegando para usar no programa

            lugares.add(objLegs.getString("start_address")); //Endereco inicial

            String s = "0x00000000";

            System.out.println(s.substring(s.indexOf("x")+1));
            System.out.println(s.substring(s.indexOf("x")+1, s.length()));

            Log.i("Vamos", s.substring(s.indexOf("x")+1));
            Log.i("Vamos", json.substring(json.indexOf("end_address")+1));

            String polyline = "";

            while(contG < objSteps.length()){
                if(objSteps.getJSONObject(contG).getJSONArray("steps").isNull(contG)){
                    while (contP < objSteps.getJSONObject(contG).getJSONArray("steps").length()){
                        lugares.add(objSteps.getJSONObject(contG).getJSONArray("steps").getJSONObject(contP).getString("html_instructions"));

                        polyline = objSteps.getJSONObject(contG).getJSONArray("steps").getJSONObject(contP).getJSONObject("polyline").getString("points");
                        for(LatLng p : decodePolyline(polyline)) {
                            listlatlong.add(p);
                        }

                        contP++;
                    }
                }

                //HTML Instrutions dos grandes Steps
                lugares.add(objSteps.getJSONObject(contG).getString("html_instructions"));

                //Log.i("contG", ""+contG);
                //Log.i("contP", ""+contP);
                //Log.i("contSteps", ""+objSteps.getJSONObject(1));
                //Log.i("contStepsSteps", ""+objSteps.getJSONObject(0).getJSONArray("steps").isNull(0));

                //Points dos grandes Steps
                polyline = objSteps.getJSONObject(contG).getJSONObject("polyline").getString("points");
                for(LatLng p : decodePolyline(polyline)) {
                    listlatlong.add(p);
                }

                contG++;
                contP = 0;

                Log.i("contG", ""+contG);
                Log.i("contP", ""+contP);
                Log.i("contV", ""+ (objSteps.getJSONObject(contG).getJSONArray("steps") != null));

            }

            lugares.add(objLegs.getString("end_address")); //Endereco final

            direcoes.setPreco(obj.getString("text")); //Aqui pego o texto com o valor da passagem total
            direcoes.setStartLatitude(objSteps.getJSONObject(0).getJSONObject("start_location").getString("lat"));
            direcoes.setStartLatitude(objSteps.getJSONObject(0).getJSONObject("start_location").getString("lng"));
            direcoes.setStartLatitude(objSteps.getJSONObject(0).getJSONObject("end_location").getString("lat"));
            direcoes.setStartLatitude(objSteps.getJSONObject(0).getJSONObject("end_location").getString("lng"));

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