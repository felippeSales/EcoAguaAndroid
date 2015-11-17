package ecoagua.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notificacao implements Comparable<Notificacao>{
	private Predio predio;
	private String texto;
	private Calendar data;
	
	/**
	 * Construtor de notificacao
	 * @param predio
	 * @param texto
	 */
	public Notificacao(Predio predio, String texto) {
		setTexto(texto);
		setPredio(predio);
		data = Calendar.getInstance();
	}
	
	public Notificacao(String texto, String data, Predio predio) {
		setTexto(texto);
		
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			cal.setTime(sdf.parse(data));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setData(cal);
		setPredio(predio);
	}

	public Predio getPredio() {
		return predio;
	}

	public void setPredio(Predio predio) {
		if (predio == null ) {
			throw new IllegalArgumentException("Predio é obrigatório.");
		
	}
		this.predio = predio;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
				throw new IllegalArgumentException("Texto é obrigatório.");
			
		}
		this.texto = texto;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	@Override
	public int compareTo(Notificacao another) {
		return this.getData().compareTo(another.getData());
	}
	
}