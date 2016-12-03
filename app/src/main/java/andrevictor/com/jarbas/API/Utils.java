package andrevictor.com.jarbas.API;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static andrevictor.com.jarbas.Telas.TelaRota.lugares;
import static andrevictor.com.jarbas.Telas.TelaRota.listlatlong;


//Classe que pega o json e o objeto e atribui um ao outro (Não explicou nada, mas ta valendo)

public class Utils {

    public Direcoes getInformacao(String end){
            String json;
        Direcoes retorno;
        json = NetworkUtils.getJSONFromAPI(end);
        retorno = parseJson(json);

        return retorno;
    }

    private Direcoes parseJson(String json) {
        try {
            Direcoes direcoes = new Direcoes(); //Cria o objeto direcoes


            JSONArray jsonapi = new JSONArray(json); //Pega o Json
            JSONObject rotaCompletaJson = jsonapi.getJSONObject(0); //Pega o objeto 0

            //Promotor
            JSONObject rotaPromotor = rotaCompletaJson.getJSONObject("promotor");
            direcoes.setNomePromotor(rotaPromotor.getString("nome")); //Manda o nome da api para o objeto
            JSONObject rotaLocalizacao = rotaPromotor.getJSONObject("localizacao");
            direcoes.setLatitudePromotor(rotaLocalizacao.getDouble("latitude"));
            direcoes.setLongitudePromotor(rotaLocalizacao.getDouble("longitude"));
            direcoes.setNomePromotor(rotaPromotor.getString("nome"));


            //Mercado
            JSONArray rotaMercados = rotaCompletaJson.getJSONArray("mercados");
            JSONObject rotaMercado = rotaMercados.getJSONObject(0);
            JSONObject rotaMercadoLocalizacao = rotaMercado.getJSONObject("localizacao");
            direcoes.setLatitudeMercado(rotaMercadoLocalizacao.getDouble("latitude"));
            direcoes.setLongitudeMercado(rotaMercadoLocalizacao.getDouble("longitude"));

            //Pega informacoes rotas
            JSONArray rotaRotas =  rotaCompletaJson.getJSONArray("rotas"); //Pega rotas
            JSONObject rotaTeste = rotaRotas.getJSONObject(0);

            direcoes.setPrecoRota(""+rotaTeste.getDouble(new DecimalFormat("0.00").format(3.80)));//rotaTeste.getDouble("preco")));


            JSONArray instrucoesRotas = rotaTeste.getJSONArray("instrucoes"); //Instrucoes
            JSONArray polylinesRotas = rotaTeste.getJSONArray("polylines"); //Polylines

            ArrayList<String> arrIns = new ArrayList<String>();
            ArrayList<String> arrPol = new ArrayList<>();

            for(int i = 0; i < instrucoesRotas.length(); i++){
                arrIns.add(instrucoesRotas.getString(i).replaceAll("<[^>]*>", ""));
                lugares.add(instrucoesRotas.getString(i).replaceAll("<[^>]*>", ""));
            }

            for(int i = 0; i < polylinesRotas.length(); i++){
                arrPol.add(polylinesRotas.getString(i));

                for(LatLng p : decodePolyline(arrPol.get(i))) {
                    listlatlong.add(p);
                }
            }

            direcoes.setInstrucoesRota(arrIns);
            direcoes.setPolylinesRota(arrPol);

            return direcoes;
        }catch (JSONException e){
            e.printStackTrace();
            Log.i("ErroDirecoes", ""+e);
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