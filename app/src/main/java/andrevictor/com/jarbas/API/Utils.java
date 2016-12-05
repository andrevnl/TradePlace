package andrevictor.com.jarbas.API;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


//Classe que pega o json e o objeto e atribui um ao outro

public class Utils {

    public static ArrayList<Double> LatitudeMercado;
    public static ArrayList<Double> LongitudeMercado;
    public static ArrayList<String> EnderecoMercado;
    public static ArrayList<String> lugares; //Instrucoes das rotas para chegar no mercado
    public static ArrayList<String> locais; //Nomes dos supermercados
    public static ArrayList<LatLng> listlatlong; //Lista com latitude e longitude para desenhar o mapa

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

            LatitudeMercado = new ArrayList<>();
            LongitudeMercado = new ArrayList<>();
            EnderecoMercado = new ArrayList<>();
            lugares = new ArrayList<>();
            listlatlong = new ArrayList<>();
            locais = new ArrayList<>();


            JSONArray jsonapi = new JSONArray(json); //Pega o Json
            JSONObject rotaCompletaJson = jsonapi.getJSONObject(0); //Pega o objeto 0

            //Promotor //Peguei tudo o que precisa do promotor
            JSONObject rotaPromotor = rotaCompletaJson.getJSONObject("promotor");
            direcoes.setNomePromotor(rotaPromotor.getString("nome")); //Pega o nome
            JSONObject rotaLocalizacao = rotaPromotor.getJSONObject("localizacao");
            direcoes.setLatitudePromotor(rotaLocalizacao.getDouble("latitude")); //Pega latitude casa promotor
            direcoes.setLongitudePromotor(rotaLocalizacao.getDouble("longitude")); //Pega longitude casa promotor
            JSONObject rotaEmpresaPromotor = rotaPromotor.getJSONObject("empresa");
            direcoes.setEmpresaPromotor(rotaEmpresaPromotor.getString("nome"));


            //Mercado //Peguei tudo de todos os mercados para usar no programa na primeira vez que roda
            JSONArray rotaMercados = rotaCompletaJson.getJSONArray("mercados");

            for(int i = 0; i < rotaMercados.length(); i++){
                JSONObject rotaMercado = rotaMercados.getJSONObject(i);
                JSONObject rotaMercadoLocalizacao = rotaMercado.getJSONObject("localizacao");
                LatitudeMercado.add(rotaMercadoLocalizacao.getDouble("latitude")); //Latitude mercado
                LongitudeMercado.add(rotaMercadoLocalizacao.getDouble("longitude")); //Longitude mercado
                locais.add(rotaMercado.getString("nome")); //Nome Mercado
                EnderecoMercado.add(rotaMercado.getString("endereco")); //Endereco Mercado
            }


            //Pega informacoes rotas
            JSONArray rotaRotas =  rotaCompletaJson.getJSONArray("rotas"); //Pega rotas
            JSONObject rotaTeste = rotaRotas.getJSONObject(0);


            direcoes.setPrecoRota(new DecimalFormat("0.00").format(3.80)); //Arrumar essa parte

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