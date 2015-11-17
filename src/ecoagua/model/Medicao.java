package ecoagua.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.net.ParseException;

public class Medicao {
	private float valor;
	private final String unidadeMedida = "L";
	private Calendar data;
	private Predio predio;

	public Medicao(float valor, Predio predio) {
		setValor(valor);
		setData(Calendar.getInstance());
	}
	
	public Medicao(float valor, String data, Predio predio) {
		setValor(valor);
		
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
				cal.setTime(sdf.parse(data));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setData(cal);
	}

	public Medicao(Float valor) {
		setValor(valor);
	
	}

	public Medicao(float valor, Calendar data) {
		setValor(valor);
		setData(data);
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Predio getPredio() {
		return predio;
	}

	public void setPredio(Predio predio) {
		if (predio == null) {
			throw new IllegalArgumentException("Predio é obrigatório.");

		}
		this.predio = predio;
	}
}
