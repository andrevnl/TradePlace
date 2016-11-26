package andrevictor.com.jarbas.API;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreVictor on 08/10/16.
 * Classe que cria o objeto para guardar os dados que precisa do json
 */

public class Direcoes{

    private String nomeLocal;
    private ArrayList<String> directionsMap;
    private String preco;
    private String StartLatitude;
    private String StartLongitude;
    private String EndLatitude;
    private String EndLogintude;


    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public ArrayList<String> getDirectionsMap() {
        return directionsMap;
    }

    public void setDirectionsMap(ArrayList<String> directionsMap) {
        this.directionsMap = directionsMap;
    }

    public String getStartLatitude() {
        return StartLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        StartLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return StartLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        StartLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return EndLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        EndLatitude = endLatitude;
    }

    public String getEndLogintude() {
        return EndLogintude;
    }

    public void setEndLogintude(String endLogintude) {
        EndLogintude = endLogintude;
    }
}
