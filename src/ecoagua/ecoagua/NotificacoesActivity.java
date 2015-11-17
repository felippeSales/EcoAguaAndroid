package ecoagua.ecoagua;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;

import com.ecoagua.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import ecoagua.controller.API;
import ecoagua.model.Notificacao;
import ecoagua.model.Predio;
import ecoagua.scrollable.NotificacoesList;
import ecoagua.scrollable.RankingList;

public class NotificacoesActivity extends Activity {
	private ListView list;
	private NotificacoesList notificacoesList;
	private List<Notificacao> itens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificacoes);
		
		Notificacao semanaPassada, semanaTrasada;

		
		list = (ListView) findViewById(R.id.lvExpNotificacao);
	
		try {
			itens = API.getNotificacoesPorPredio(API.user.getId());
			
			Predio p = API.infoPredio(1);
			
			float consumoSemanaPassada = API.resultadoMedicoesSemanaPassada(1); 
			float consumoSemanaTrasada = API.resultadoMedicoesSemanaTrasada(1);
			float resultado = 0;
			String texto = "";
			
			if(consumoSemanaPassada > consumoSemanaTrasada){
				resultado =  consumoSemanaPassada / consumoSemanaTrasada;
				resultado = (1 - resultado) * 100;
				
				texto = "Voce aumentou o consumo em: ";
			}else{
				resultado =  consumoSemanaPassada / consumoSemanaTrasada;
				resultado = (1 - resultado) * 100;
				
				texto = "Voce diminuiu o consumo em: ";
			}
			
			DecimalFormat df = new DecimalFormat("##.##");
			df.setRoundingMode(RoundingMode.DOWN);
			texto = texto + df.format(resultado);
			semanaPassada = new Notificacao(p, texto + "%");

//			semanaTrasada = new Notificacao(p, "GASTO DA SEMANA trasada: " + API.resultadoMedicoesSemanaPassada(1));

			
			itens.add(semanaPassada);
	//		itens.add(semanaTrasada);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		// necessario fazer o sorte antes
		Collections.sort(itens);
		notificacoesList = new NotificacoesList(this, itens, list);
		list.setAdapter(notificacoesList);
	}
/*
	private void criaItensTest() {
		// dados pra teste
		String texto1 = "notificacao teste 1";
		String texto2 = "notificacao teste 2";
		

		Notificacao n1 = new Notificacao(predio1, texto1);
		Notificacao n2 = new Notificacao(predio2, texto2);
		
		itens.add(n1);
		itens.add(n2);

	}
	*/
}
