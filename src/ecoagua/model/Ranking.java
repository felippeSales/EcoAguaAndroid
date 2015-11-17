package ecoagua.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;

import ecoagua.controller.API;


public class Ranking {
	private static List<Predio> predios;
	
	public Ranking(){
		
	}
	
	public static void setPredios(List<Predio> predios) {
		Ranking.predios = predios;
	}
	
	public static List<Predio> getPredios() {
		return predios;
	}
	
	/**
	 * Rankeia os predios por gasto mensal de agua
	 * @param p
	 * @return
	 * @throws JSONException
	 */
	public static void rankeia(List<Predio> p) throws JSONException{
		List<Predio> predios = new ArrayList<Predio>();
		
		for(Predio predio: p){
			predio.setGastosMesAtual(calculaGastoMensal(predio));
			predios.add(predio);
		}
		
		//rankeia por gasto
		Collections.sort(predios);
		
		setPredios(predios);
		
	}

	/**
	 * Calcula o consumo mensal de agua
	 * @param p
	 * @return
	 * @throws JSONException 
	 */
	private static Float calculaGastoMensal(Predio p) throws JSONException {
		p.setMedicoes(API.getMedicoesPorPredio(p.getId()));
		
		
		List<Medicao> medicoes = p.getMedicoesMes(Calendar.getInstance());
		float count = (float) 0.0;
		
		for(Medicao m : medicoes){
			count += m.getValor();
		}
		
		return count;
		
	}
}
