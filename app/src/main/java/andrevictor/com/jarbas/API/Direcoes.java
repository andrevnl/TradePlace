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
}
