package ecoagua.model;

public class Endereco {
	private static final int TAMANHO_ESTADO = 2;
	private static final int TAMANHO_MAXIMO_CIDADE = 30;
	private static final int TAMANHO_MAXIMO_BAIRRO = 30;
	private static final int TAMANHO_MAXIMO_RUA = 100;
	private static final int TAMANHO_CEP = 8;

	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private int cep;
	private String numero;

	/**
	 * Construtor de endereco
	 * 
	 * @param estado
	 * @param cidade
	 * @param bairro
	 * @param rua
	 * @param nome12
	 * @param nome1
	 */
	public Endereco(String estado, String cidade, String bairro, String rua,
			String numero, int cep) {
		super();
		setEstado(estado);
		setCidade(cidade);
		setBairro(bairro);
		setRua(rua);
		setCep(cep);
		setNumero(numero);
	}

	/**
	 * Get estado
	 * 
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Set estado
	 * 
	 * @param estado
	 */
	public void setEstado(String estado) {
		// if (estado == null || estado.trim().isEmpty()) {
		// throw new IllegalArgumentException("Estado � obrigat�rio.");
		// } else if (estado.length() != TAMANHO_ESTADO) {
		// throw new
		// IllegalArgumentException("Estado inv�lido. Coloque apenas a sigla.");
		// }
		this.estado = estado;
	}

	/**
	 * Get cidade
	 * 
	 * @return cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * Set cidade
	 * 
	 * @param cidade
	 */
	public void setCidade(String cidade) {
		if (cidade == null || cidade.trim().isEmpty()) {
			throw new IllegalArgumentException("Cidade � obrigat�ria.");
		} else if (cidade.length() > TAMANHO_MAXIMO_CIDADE) {
			throw new IllegalArgumentException(
					"Tamanho da cidade excede o limite de "
							+ TAMANHO_MAXIMO_CIDADE + " caracteres.");
		}
		this.cidade = cidade;
	}

	/**
	 * Get bairro
	 * 
	 * @return bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * Set bairro
	 * 
	 * @param bairro
	 */
	public void setBairro(String bairro) {
		if (bairro == null || bairro.trim().isEmpty()) {
			throw new IllegalArgumentException("Bairro � obrigat�rio.");
		} else if (bairro.length() > TAMANHO_MAXIMO_BAIRRO) {
			throw new IllegalArgumentException(
					"Tamanho do bairro excede o limite de "
							+ TAMANHO_MAXIMO_BAIRRO + " caracteres.");
		}
		this.bairro = bairro;
	}

	/**
	 * Get rua
	 * 
	 * @return rua
	 */
	public String getRua() {
		return rua;
	}

	/**
	 * Set rua
	 * 
	 * @param rua
	 */
	public void setRua(String rua) {
		if (rua == null || rua.trim().isEmpty()) {
			throw new IllegalArgumentException("Rua � obrigat�ria.");
		} else if (rua.length() > TAMANHO_MAXIMO_RUA) {
			throw new IllegalArgumentException(
					"Tamanho da rua excede o limite de " + TAMANHO_MAXIMO_RUA
							+ " caracteres.");
		}
		this.rua = rua;
	}

	/**
	 * Get cep
	 * 
	 * @return cep
	 */
	public int getCep() {
		return cep;
	}

	/**
	 * Set cep
	 * 
	 * @param cep
	 */
	public void setCep(int cep) {
		// if (Integer.toString(cep).length() != TAMANHO_CEP) {
		// throw new IllegalArgumentException("Tamanho do CEP � diferente de "
		// + TAMANHO_CEP + " caracteres.");
		// }
		this.cep = cep;
	}

	/**
	 * Get numero
	 * 
	 * @return
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Set numero
	 * 
	 * @param cep2
	 */
	public void setNumero(String numero) {

		this.numero = numero;
	}

	@Override
	public String toString() {
		String endereco = "Rua " + this.getRua() + ", bairro "
				+ this.getBairro() + ", " + this.cidade + " - " + this.estado;
		return endereco;
	}
}
