package andrevictor.com.jarbas.ClassesDiversas;

/**
 * Created by SENAI on 12/11/2016.
 * Classe que cria o objeto para o AdapterListView
 */

public class ItemListView {
    private String texto;
    private int iconeRid;

    public ItemListView()
    {
    }

    public ItemListView(String texto, int iconeRid)
    {
        this.texto = texto;
        this.iconeRid = iconeRid;
    }

    public int getIconeRid()
    {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid)
    {
        this.iconeRid = iconeRid;
    }

    public String getTexto()
    {
        return texto;
    }

    public void setTexto(String texto)
    {
        this.texto = texto;
    }
}
