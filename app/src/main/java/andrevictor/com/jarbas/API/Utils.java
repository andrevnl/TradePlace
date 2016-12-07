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
    public static ArrayList<String> polilynesRota;
    public static ArrayList<String> locais; //Nomes dos supermercados
    public static ArrayList<LatLng> listlatlong; //Lista com latitude e longitude para desenhar o mapa
    public static ArrayList<Integer> controlaIntrucoesRota;
    public static ArrayList<Integer> controlaPolilynesRota;
    public static ArrayList<Double> precoRota;
    public static int sequenciaRota;
    public static double latitudePromotor;
    public static double longitudePromotor;
    public static String nomePromotor;
    public static String enderecoPromotor;

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
            polilynesRota = new ArrayList<>();
            listlatlong = new ArrayList<>();
            locais = new ArrayList<>();
            controlaIntrucoesRota = new ArrayList<>();
            controlaPolilynesRota = new ArrayList<>();
            precoRota = new ArrayList<>();
            sequenciaRota = 0;

            JSONArray jsonapi = new JSONArray(json); //Pega o Json
            JSONObject rotaCompletaJson = jsonapi.getJSONObject(0); //Pega o objeto 0

            //Promotor //Peguei tudo o que precisa do promotor
            JSONObject rotaPromotor = rotaCompletaJson.getJSONObject("promotor");
            nomePromotor = rotaPromotor.getString("nome"); //Pega o nome
            JSONObject rotaLocalizacao = rotaPromotor.getJSONObject("localizacao");
            latitudePromotor = rotaLocalizacao.getDouble("latitude"); //Pega latitude casa promotor
            longitudePromotor = rotaLocalizacao.getDouble("longitude"); //Pega longitude casa promotor
            JSONObject rotaEmpresaPromotor = rotaPromotor.getJSONObject("empresa");
            direcoes.setEmpresaPromotor(rotaEmpresaPromotor.getString("nome")); //Pega o nome da empresa
            enderecoPromotor = rotaPromotor.getString("endereco");


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
            //locais.add("Casa");


            //Pega informacoes rotas
            JSONArray rotaRotas =  rotaCompletaJson.getJSONArray("rotas"); //Pega rotas

            for(int i = 0; i < rotaRotas.length(); i++){
                JSONObject rotaTeste = rotaRotas.getJSONObject(i);
                precoRota.add(rotaTeste.getDouble("preco")); //Pega o preco
                sequenciaRota = rotaTeste.getInt("sequencia"); //Pega a sequencia

                JSONArray instrucoesRotas = rotaTeste.getJSONArray("instrucoes"); //Instrucoes
                JSONArray polylinesRotas = rotaTeste.getJSONArray("polylines"); //Polylines

                if(i > 0) {
                    controlaIntrucoesRota.add(instrucoesRotas.length() + controlaIntrucoesRota.get(i-1));
                }else {
                    controlaIntrucoesRota.add(instrucoesRotas.length());
                }
                for(int j = 0; j < instrucoesRotas.length(); j++){
                    lugares.add(instrucoesRotas.getString(j).replaceAll("<[^>]*>", ""));
                }

                if(i > 0) {
                    controlaPolilynesRota.add(polylinesRotas.length() + controlaPolilynesRota.get(i-1));
                }else{
                    controlaPolilynesRota.add(polylinesRotas.length());
                }
                for(int h = 0; h < polylinesRotas.length(); h++){
                    polilynesRota.add(polylinesRotas.getString(h));
                }

            }
            return direcoes;
        }catch (JSONException e){
            e.printStackTrace();
            Log.i("ErroDirecoes", ""+e);
            return null;
    }
    }
}