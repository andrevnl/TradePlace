package andrevictor.com.jarbas.ClassesDiversas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import andrevictor.com.jarbas.R;

/**
 * Created by SENAI on 12/11/2016.
 * Classe que permite colocar a imageview e o textview na mesma linha do listview
 */

public class AdapterListViewRota extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<ItemListView> itens;

    public AdapterListViewRota(Context context, ArrayList<ItemListView> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount()
    {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public ItemListView getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posição.
        ItemListView item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_listrota, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.text)).setText(item.getTexto());
        ((TextView) view.findViewById(R.id.text)).setTextColor(Color.parseColor(item.getCorLinha()));
        view.setBackgroundColor(Color.parseColor(item.getColor()));
        ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());



        return view;
    }
}