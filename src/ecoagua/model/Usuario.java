package ecoagua.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import ecoagua.controller.API;

import com.ecoagua.R;


public class Usuario {
	private static final int TAMANHO_MAXIMO_NOME = 30;
	private static final int TAMANHO_MINIMO_SENHA = 2;
	private static final int TAMANHO_MAXIMO_SENHA = 20;
	private static final int TAMANHO_MAXIMO_EMAIL = 30;
	private static final int TAMANHO_MAXIMO_TELEFONE = 11;
	private static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
	private static final String TELEFONE_REGEX = "^\\([1-9]{2}\\) [2-9][0-9]{3,4}\\-[0-9]{4}$";
	private String apartamento;
	private String senha;
	private String telefone;
	private String email;
	private Endereco endereco;
	private int id; // id do bd

	// lista de notificacoes
	private List<Notificacao> notificacoes;
	// lista de medicoes
	private List<Medicao> medicoes;
	// colocacao no ranking
	private int colocacao;

	/**
	 * Construtor de usuario
	 * 
	 * @param nome
	 * @param senha
	 * @param telefone
	 * @param email
	 * @param endereco
	 */
	public Usuario(String nome, String senha, String telefone, String email,
			Endereco endereco, int id) {
		setNome(nome);
		setSenha(senha);
		setTelefone(telefone);
		setEmail(email);
		setEndereco(endereco);
		setId(id);
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		if (notificacoes == null) {
			throw new IllegalArgumentException(
					"Notificações não podem ser null.");
		}
		this.notificacoes = notificacoes;
	}

	public List<Medicao> getMedicoes() {
		return medicoes;
	}

	public void setMedicoes(List<Medicao> medicoes) {
		if (medicoes == null) {
			throw new IllegalArgumentException("Medições não podem ser null.");
		}
		this.medicoes = medicoes;
	}

	public void addMedicacao(Medicao medicao) {
		medicoes.add(medicao);
	}

	public Medicao getMedicaoPorData(Calendar data) {
		for (Medicao m : getMedicoes()) {
			if (CalendarUtils.formataDataAPI(m.getData()).equals(
					CalendarUtils.formataDataAPI(data))) {
				return m;
			}
		}

		return new Medicao((float) 0.0, data);
	}

	/**
	 * Get das medicoes por mes
	 * 
	 * @param data
	 * @return medicoes por mes
	 */
	public List<Medicao> getMedicoesMes(Calendar data) {
		List<Medicao> m = new ArrayList<Medicao>();

		Calendar temp = Calendar.getInstance();

		temp.setTime(data.getTime());

		temp.set(Calendar.DAY_OF_MONTH, 1);
		int myMonth = temp.get(Calendar.MONTH);

		// loop com todas as datas do mes
		while (myMonth == temp.get(Calendar.MONTH)) {
			m.add(getMedicaoPorData(temp));
			temp.add(Calendar.DAY_OF_MONTH, 1);
		}

		return m;
	}
	
	/**
	 * Get das medicoes por semana
	 * 
	 * @param data
	 * @return medicoes da semana
	 */
	public List<Medicao> getMedicoesSemana(Calendar data) {
		List<Medicao> m = new ArrayList<Medicao>();

		Calendar temp = Calendar.getInstance();

		temp.setTime(data.getTime());

		// gera datas da semana
		String[] days = new String[7];
		int delta = -temp.get(GregorianCalendar.DAY_OF_WEEK) + 1;
		temp.add(Calendar.DAY_OF_MONTH, delta);

		// loop com todas as datas da semana
		for (int i = 0; i < 7; i++) {
			m.add(getMedicaoPorData(temp));
			temp.add(Calendar.DAY_OF_MONTH, 1);
		}

		return m;
	}
	
	public void addNotificacao(Notificacao notificacao) {
		notificacoes.add(notificacao);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get nome do usuario
	 * 
	 * @return nome
	 */
	public String getNome() {
		return apartamento;
	}

	/**
	 * Set nome do usuario
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		if (nome != null) {
			if (nome.trim().isEmpty()) {
				throw new IllegalArgumentException("Nome é obrigatório.");
			} else if (nome.length() > TAMANHO_MAXIMO_NOME) {
				throw new IllegalArgumentException(
						"Tamanho do nome excede o limite de "
								+ TAMANHO_MAXIMO_NOME + " caracteres.");
			}
		} else {
			throw new IllegalArgumentException("Nome é obrigatório.");
		}
		this.apartamento = nome;
	}

	/**
	 * Get senha
	 * 
	 * @return senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Set senha
	 * 
	 * @param senha
	 */
	public void setSenha(String password) {
		/*
		 * if (password != null) { if (password.trim().isEmpty()) { throw new
		 * IllegalArgumentException("Senha é obrigatória."); } else if
		 * (password.length() < TAMANHO_MINIMO_SENHA) { throw new
		 * IllegalArgumentException("Deve deve ter pelo menos " +
		 * TAMANHO_MINIMO_SENHA + " caracteres."); } else if (password.length()
		 * > TAMANHO_MAXIMO_SENHA) { throw new
		 * IllegalArgumentException("Tamanho da senha excede o limite de " +
		 * TAMANHO_MAXIMO_SENHA + " caracteres."); } }
		 */

		this.senha = password;
	}

	/**
	 * Get telefone
	 * 
	 * @return telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * Set telefone
	 * 
	 * @param telefone
	 */
	public void setTelefone(String telefone) {
		/*
		 * if (telefone == null || telefone.trim().isEmpty()) { throw new
		 * IllegalArgumentException("Telefone é obrigatório."); } else if
		 * (telefone.length() > TAMANHO_MAXIMO_TELEFONE ||
		 * !telefone.matches(TELEFONE_REGEX)) { throw new
		 * IllegalArgumentException("Telefone inválido."); }
		 */
		this.telefone = telefone;
	}

	/**
	 * Get email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		/*
		 * if (email == null || email.trim().isEmpty()) { throw new
		 * IllegalArgumentException("E-mail é obrigatório."); } else if
		 * (email.length() > TAMANHO_MAXIMO_EMAIL ||
		 * !email.matches(EMAIL_REGEX)) { throw new
		 * IllegalArgumentException("E-mail inválido."); }
		 */
		this.email = email;
	}

	/**
	 * Get endereco
	 * 
	 * @return endereco
	 */
	public Endereco getEndereco() {

		return endereco;
	}

	/**
	 * Get colocacao
	 * 
	 * @return colocacao no ranking
	 */
	public int getColocacao() {
		return colocacao;
	}

	/**
	 * Set colocacao
	 * 
	 * @param colocacao
	 */
	public void setColocacao(int colocacao) {
		if (colocacao == 0) {
			throw new IllegalArgumentException("Colocação não pode ser 0.");
		}
		this.colocacao = colocacao;
	}

	/**
	 * Set endereco
	 * 
	 * @param endereco
	 */
	public void setEndereco(Endereco endereco) {
		/*
		 * if (endereco != null) { throw new
		 * IllegalArgumentException("Endereco é obrigatório."); }
		 */
		this.endereco = endereco;
	}

	/**
	 * Checa se o usuario e do tipo morador
	 * 
	 * @param usuario
	 * @return isMorador
	 */
	public static boolean isMorador(Usuario usuario) {
		boolean isMorador = false;

		if (usuario.getClass() == Morador.class) {

			isMorador = true;
		}
		return isMorador;

	}

	/**
	 * Checa se o usuario e do tipo predio
	 * 
	 * @param usuario
	 * @return isPredio
	 */
	public static boolean isPredio(Usuario usuario) {
		boolean isPredio = false;

		if (usuario.getClass() == Predio.class) {

			isPredio = true;
		}
		return isPredio;

	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		Usuario other = (Usuario) obj;
		return this.getId() == other.getId();
	}

}
