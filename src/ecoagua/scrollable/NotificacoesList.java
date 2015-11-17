package ecoagua.scrollable;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import ecoagua.model.CalendarUtils;
import ecoagua.model.Notificacao;

import com.ecoagua.R;

public class NotificacoesList extends AdapterListView<Notificacao>{

	public NotificacoesList(Context context, List<Notificacao> itens,
			ListView lv) {
		super(context, itens, lv);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (view == null) {
            //infla o layout para podermos pegar as views
            view = getmInflater().inflate(R.layout.activity_list_notificacoes, null);

            //cria um item de suporte para não precisarmos sempre
            //inflar as mesmas informacoes
            itemHolder = new ItemSuporte();
            itemHolder.tvData = ((TextView) view.findViewById(R.id.tv_notificacoes_data));
            itemHolder.tvTexto = ((TextView) view.findViewById(R.id.tv_notificacoes_texto));
            itemHolder.tvEnderecado = ((TextView) view.findViewById(R.id.tv_notificacoes_nome));

            //define os itens na view;
            view.setTag(itemHolder);
        } else {
            //se a view já existe pega os itens.
            itemHolder = (ItemSuporte) view.getTag();
        }

        //pega os dados da lista
        //e define os valores nos itens.
        Notificacao item = getItens().get(position);
        itemHolder.tvEnderecado.setText(item.getPredio().getNome());
        itemHolder.tvTexto.setText(item.getTexto());
        itemHolder.tvData.setText(CalendarUtils.getDataFormada(item.getData()));

        //retorna a view com as informações
        return view;
	}
	
	/**
     * Classe de suporte para os itens do layout.
     */
    private class ItemSuporte {

    	TextView tvEnderecado;
    	TextView tvTexto;
        TextView tvData;
    }

}
