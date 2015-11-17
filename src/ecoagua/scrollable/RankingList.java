package ecoagua.scrollable;

import java.util.List;

import com.ecoagua.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import ecoagua.model.Predio;

public class RankingList extends AdapterListView<Predio> {
	
	public RankingList(Context context, List<Predio> itens, ListView lv) {
		super(context, itens, lv);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (view == null) {
            //infla o layout para podermos pegar as views
            view = getmInflater().inflate(R.layout.activity_list_ranking, null);

            //cria um item de suporte para não precisarmos sempre
            //inflar as mesmas informacoes
            itemHolder = new ItemSuporte();
            itemHolder.tvGastos = ((TextView) view.findViewById(R.id.tv_list_gastos));
            itemHolder.tvNome = ((TextView) view.findViewById(R.id.tv_list_nome));
            itemHolder.tvPosicao = ((TextView) view.findViewById(R.id.tv_list_posicao));

            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista
        //e define os valores nos itens.
        Predio item = getItens().get(position);
        itemHolder.tvGastos.setText(Float.toString(item.getGastosMesAtual()));
        itemHolder.tvNome.setText(item.getNome());
        itemHolder.tvPosicao.setText(Integer.toString(item.getColocacao()));

        //retorna a view com as informações
        return view;
	}
	
	/**
     * Classe de suporte para os itens do layout.
     */
    private class ItemSuporte {

    	TextView tvPosicao;
    	TextView tvNome;
        TextView tvGastos;
    }
	
}
