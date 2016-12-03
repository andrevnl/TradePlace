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
    private Double latitudePromotor;
    private Double longitudePromotor;
    //Dados do Mercado
    private String NomeMercado;
    private Double latitudeMercado;
    private Double longitudeMercado;
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

    public void setEmpresaPromotor(String empresaPromotor) {
        EmpresaPromotor = empresaPromotor;
    }

    public Double getLatitudePromotor() {
        return latitudePromotor;
    }

    public void setLatitudePromotor(Double latitudePromotor) {
        this.latitudePromotor = latitudePromotor;
    }

    public Double getLongitudePromotor() {
        return longitudePromotor;
    }

    public void setLongitudePromotor(Double longitudePromotor) {
        this.longitudePromotor = longitudePromotor;
    }

    public String getNomeMercado() {
        return NomeMercado;
    }

    public void setNomeMercado(String nomeMercado) {
        NomeMercado = nomeMercado;
    }

    public Double getLatitudeMercado() {
        return latitudeMercado;
    }

    public void setLatitudeMercado(Double latitudeMercado) {
        this.latitudeMercado = latitudeMercado;
    }

    public Double getLongitudeMercado() {
        return longitudeMercado;
    }

    public void setLongitudeMercado(Double longitudeMercado) {
        this.longitudeMercado = longitudeMercado;
    }

    public String getPrecoRota() {
        return PrecoRota;
    }

    public void setPrecoRota(String precoRota) {
        PrecoRota = precoRota;
    }
}
