package andrevictor.com.jarbas.API;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreVictor on 08/10/16.
 * Classe que cria o objeto para guardar os dados que precisa do json
 */

public class Direcoes{

    //Dados do Promotor
    private String nomePromotor;
    private String LoginPromotor;
    private String SenhaPromotor;
    private String EmpresaPromotor;
    private String LatitudePromotor;
    private String LongitudePromotor;
    //Dados do Mercado
    private String NomeMercado;
    private String LatitudeMercado;
    private String LongitudeMercado;
    //Dados da Rota
    private String PrecoRota;
    private String SequenciaRota;
    private ArrayList<String> InstrucoesRota;
    private ArrayList<String> PolylinesRota ;

    public String getNomePromotor() {
        return nomePromotor;
    }

    public void setNomePromotor(String nomePromotor) {
        this.nomePromotor = nomePromotor;
    }

    public String getLoginPromotor() {
        return LoginPromotor;
    }

    public void setLoginPromotor(String loginPromotor) {
        LoginPromotor = loginPromotor;
    }

    public String getSenhaPromotor() {
        return SenhaPromotor;
    }

    public void setSenhaPromotor(String senhaPromotor) {
        SenhaPromotor = senhaPromotor;
    }

    public String getEmpresaPromotor() {
        return EmpresaPromotor;
    }

    public void setEmpresaPromotor(String empresaPromotor) {
        EmpresaPromotor = empresaPromotor;
    }

    public String getLatitudePromotor() {
        return LatitudePromotor;
    }

    public void setLatitudePromotor(String latitudePromotor) {
        LatitudePromotor = latitudePromotor;
    }

    public String getLongitudePromotor() {
        return LongitudePromotor;
    }

    public void setLongitudePromotor(String longitudePromotor) {
        LongitudePromotor = longitudePromotor;
    }

    public String getNomeMercado() {
        return NomeMercado;
    }

    public void setNomeMercado(String nomeMercado) {
        NomeMercado = nomeMercado;
    }

    public String getLatitudeMercado() {
        return LatitudeMercado;
    }

    public void setLatitudeMercado(String latitudeMercado) {
        LatitudeMercado = latitudeMercado;
    }

    public String getLongitudeMercado() {
        return LongitudeMercado;
    }

    public void setLongitudeMercado(String longitudeMercado) {
        LongitudeMercado = longitudeMercado;
    }

    public String getPrecoRota() {
        return PrecoRota;
    }

    public void setPrecoRota(String precoRota) {
        PrecoRota = precoRota;
    }

    public String getSequenciaRota() {
        return SequenciaRota;
    }

    public void setSequenciaRota(String sequenciaRota) {
        SequenciaRota = sequenciaRota;
    }

    public ArrayList<String> getInstrucoesRota() {
        return InstrucoesRota;
    }

    public void setInstrucoesRota(ArrayList<String> instrucoesRota) {
        InstrucoesRota = instrucoesRota;
    }

    public ArrayList<String> getPolylinesRota() {
        return PolylinesRota;
    }

    public void setPolylinesRota(ArrayList<String> polylinesRota) {
        PolylinesRota = polylinesRota;
    }
}
