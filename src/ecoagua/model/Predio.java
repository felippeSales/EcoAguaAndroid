package ecoagua.model;

import java.util.ArrayList;
import java.util.List;

public class Predio extends Usuario implements Comparable<Predio> {
	// gastos com agua do mes atual em L
	private float gastosMesAtual;
	// lista de moradores
	private List<Morador> moradores;

	/**
	 * Construtor de predio
	 * 
	 * @param nome
	 * @param senha
	 * @param telefone
	 * @param email
	 * @param endereco
	 */
	public Predio(String nome, String senha, String telefone, String email,
			Endereco endereco, int id) {
		super(nome, senha, telefone, email, endereco, id);

	}

	/**
	 * Construtor com os dados do bd
	 * 
	 * @param nome
	 * @param senha
	 * @param telefone
	 * @param email
	 * @param endereco
	 * @param moradores
	 * @param gastoMesAtual
	 * @param colocacao
	 */
	public Predio(String nome, String senha, String telefone, String email,
			Endereco endereco, List<Morador> moradores,
			List<Notificacao> notificacaoes, List<Medicao> medicoes,
			float gastoMesAtual, int colocacao, int id) {
		super(nome, senha, telefone, email, endereco, id);
	}

	/**
	 * Get gastos
	 * 
	 * @return gastos mensais de agua em litros
	 */
	public float getGastosMesAtual() {
		return gastosMesAtual;
	}

	/**
	 * Set gastos
	 * 
	 * @param gastosMesAtual
	 */
	public void setGastosMesAtual(float gastosMesAtual) {
		this.gastosMesAtual = gastosMesAtual;
	}

	public List<Morador> getMoradores() {
		return moradores;
	}

	public void setMoradores(List<Morador> moradores) {
		if(moradores == null){
			throw new IllegalArgumentException("Moradores não podem ser null.");
		}
		this.moradores = moradores;
	}

	
	@Override
	public int compareTo(Predio another) {
		if (this.getGastosMesAtual() < another.getGastosMesAtual()) {
			return -1;
		} else if (this.getGastosMesAtual() > another.getGastosMesAtual()) {
			return 1;
		}
		return 0;
	}
}
