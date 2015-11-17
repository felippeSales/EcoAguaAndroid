package ecoagua.controller;

import android.content.Intent;
import android.util.Log;
import ecoagua.ecoagua.LoginActivity;
import ecoagua.ecoagua.MainActivity;
import ecoagua.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import org.json.*;

/**
 * API eh classe que permite a conexao do aplicativo Android com o servidor
 * REST. Utilizando HTTP Client como meio de comunicacao.
 * 
 * @author Felipe Sales
 * 
 * @version %I%, %G%
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public class API {

	public static Usuario user;

	/**
	 * O endereco do servidor
	 */
	private static final String DOMAIN = "http://aguaeco-celiobarros.rhcloud.com";

	/**
	 * 
	 * Recebe o login e a senha do usuario, compara com as informacoes no
	 * servidor, se tudo der certo retorna um objeto Usuario contendo todas as
	 * informacoes relevantes a essa conta se nao retorna NULL.
	 * 
	 * @param usuario
	 *            o login do usuario
	 * @param senha
	 *            a sua respectiva senha
	 * @return
	 * 
	 * @return Usuario retorna um objeto com todas as informacoes do usuario.
	 * 
	 * @exception JSONException
	 *                Se houver algum erro na conexao com o servidor.
	 * @see JSONException
	 */
	public static boolean login(String usuario, String senha)
			throws JSONException {
		boolean login;

		String url, response;
		url = DOMAIN + "/login/" + usuario + "/" + senha;
		Log.d("url", url);

		response = GET(url);
		Log.d("JSON", response);
		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		login = obj.getBoolean("login");

		if (login) {
			if (obj.getString("tipo_usuario").equals("Morador")) {
				user = infoMorador(obj.getInt("id_morador"));
			} else {
				user = infoPredio(obj.getInt("id_predio"));
			}
		} else {
			user = null;
		}

		return login;
	}

	/**
	 * 
	 * Recebe o id do morador, compara com as informacoes no servidor, se tudo
	 * der certo retorna um objeto Morador contendo todas as informacoes se nao
	 * retorna NULL.
	 * 
	 * @param idMorador
	 *            O id no morador no nosso banco de dados.
	 * 
	 * @return Morador retorna um objeto com todas as informacoes do respectivo
	 *         morador.
	 * 
	 * @exception JSONException
	 *                Se houver algum erro na conexao com o servidor.
	 * 
	 * @see JSONException
	 * @see Morador
	 */
	public static Morador infoMorador(int idMorador) throws JSONException {

		Morador morador;
		String url, response;
		url = (DOMAIN + "/info_morador/" + idMorador);

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		String senha, nome, apartamento, telefone, email;
		int idPredio;
		Endereco endereco;
		Predio predio;

		telefone = "";
		email = "";

		senha = obj.getString("senha");
		apartamento = obj.getString("apartamento");
		nome = obj.getString("nome");
		idPredio = obj.getInt("id_predio");

		predio = infoPredio(idPredio);
		endereco = predio.getEndereco();

		morador = new Morador(nome, senha, telefone, email, endereco, predio,
				apartamento, idMorador);

		System.out.println(apartamento);

		return morador;
	}

	/**
	 * 
	 * Recebe o id do predio, compara com as informacoes no servidor, se tudo
	 * der certo retorna um objeto Predio contendo todas as informacoes se nao
	 * retorna NULL.
	 * 
	 * @param idPredio
	 *            O id no predio no nosso banco de dados.
	 * 
	 * @return Predio retorna um objeto com todas as informacoes do respectivo
	 *         predio.
	 * 
	 * @exception JSONException
	 *                Se houver algum erro na conexao com o servidor.
	 * 
	 * @see JSONException
	 * @see Predio
	 */
	public static Predio infoPredio(int idPredio) throws JSONException {
		Predio predio;
		String url, response;
		url = (DOMAIN + "/info_predio/" + idPredio);

		try {

			response = GET(url);

			JSONArray array = new JSONArray(response);
			JSONObject obj = array.getJSONObject(0);

			String senha, nome, bairro, cidade, rua, estado, telefone, email, numero;
			int cep;
			Endereco endereco;

			// login = obj.getString("login_predio");
			senha = obj.getString("senha");
			nome = obj.getString("nome");
			email = obj.getString("email");
			telefone = obj.getString("telefone");
			bairro = obj.getString("bairro");
			cidade = obj.getString("cidade");
			// estado = obj.getString("estado");
			estado = "PB";
			rua = obj.getString("rua");
			cep = obj.getInt("cep");
			numero = obj.getString("numero");

			// numero de login_predio é 'numero'
			// numero = 1;

			endereco = new Endereco(estado, cidade, bairro, rua, numero, cep);
			predio = new Predio(nome, senha, telefone, email, endereco,
					idPredio);

			System.out.println(estado);

		} finally {
		}

		return predio;
	}

	/**
	 * 
	 * Recebe todas as informacoes do morador e as cadastra no servidor, se der
	 * certo retorna true se nao retorna false.
	 * 
	 * @param idPredio
	 *            O id do predio no nosso banco de dados.
	 * @param nome
	 *            O nome do morador.
	 * @param senha
	 *            A senha do morador.
	 * @param apartamento
	 *            O apartamento do morador.
	 * 
	 * @return boolean Retorna true se o cadastro deu certo e false se nao.
	 * 
	 * @exception JSONException
	 *                Se houver algum erro na conexao com o servidor.
	 * 
	 * @see JSONException
	 */
	public static boolean cadastraMorador(int idPredio, String nome,
			String senha, String apartamento, String login)
			throws JSONException {

		boolean cadastro;

		String url, response;

		url = (DOMAIN + "/cadastra_morador/" + idPredio + "/" + nome + "/"
				+ senha + "/" + apartamento + "/" + login);

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		cadastro = obj.getBoolean("cadastro");

		return cadastro;
	}

	private static boolean checkId(int id, String tipo) {
		boolean idExiste = false;
		if (tipo.equals("Predio")) {
			try {
				user = infoPredio(id);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (tipo.equals("Morador")) {
			try {
				user = infoMorador(id);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (user != null) {
			idExiste = true;
		}

		return idExiste;

	}

	/**
	 * 
	 * Recebe todas as informacoes do predio e as cadastra no servidor, se der
	 * certo retorna true se nao retorna false.
	 * 
	 * @param nome
	 *            O nome do predio.
	 * @param senha
	 *            A senha do predio.
	 * @param telefone
	 *            O telefone do predio.
	 * @param email
	 *            O email do predio.
	 * @param estado
	 *            O estado em que o predio esta localizado.
	 * @param cidade
	 *            A cidade em que o predio esta localizado.
	 * @param bairro
	 *            O bairro em que o predio esta localizado.
	 * @param rua
	 *            A rua em que o predio esta localizado.
	 * @param numero
	 *            O numero do predio
	 * @param cep
	 *            O cep do predio
	 * @param login
	 *            O login do predio
	 * 
	 * @return boolean Retorna true se o cadastro deu certo e false se nao.
	 * 
	 * @exception JSONException
	 *                Se houver algum erro na conexao com o servidor.
	 * 
	 * @see JSONException
	 */
	public static boolean cadastraPredio(String nome, String senha,
			String telefone, String email, String estado, String cidade,
			String bairro, String rua, String numero, int cep, String login)
			throws JSONException {
		boolean cadastro = false;

		String url, response;

		url = (DOMAIN + "/cadastra_predio/" + nome + "/" + senha + "/"
				+ telefone + "/" + email + "/" + estado + "/" + cidade + "/"
				+ bairro + "/" + rua + "/" + numero + "/" + cep + "/" + login);

		response = GET(url);

		JSONArray array;

		array = new JSONArray(response);

		JSONObject obj = array.getJSONObject(0);

		cadastro = obj.getBoolean("cadastro");
		if (cadastro) {
			if (obj.getString("tipo_usuario") == "Morador") {
				user = infoMorador(obj.getInt("id_morador"));
			} else {
				user = infoPredio(obj.getInt("id_predio"));
			}
		} else {
			user = null;
		}

		return cadastro;
	}

	/**
	 * 
	 * Receo id do predio e retorna todos os moradoes registrados a aquele
	 * predio.
	 * 
	 * @param idPredio
	 *            O id do predio no nosso banco de dados.
	 * 
	 * @return ArrayList<Morador> Retorna a lista de moradores do predio.
	 * 
	 * @exception JSONException
	 *                Se houver algum erro na conexao com o servidor.
	 * 
	 * @see JSONException
	 * @see Morador
	 * @see ArrayList
	 */
	public static ArrayList<Morador> listaMoradores(int idPredio)
			throws JSONException {
		ArrayList<Morador> moradores = new ArrayList<Morador>();

		String url, response;

		url = (DOMAIN + "/lista_moradores/" + idPredio);
		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		JSONArray listaDeMoradores = obj.getJSONArray("lista_moradores");

		String senha, nome, apartamento, telefone, email;

		Endereco endereco;
		Predio predio;

		telefone = "";
		email = "";

		predio = infoPredio(idPredio);
		endereco = predio.getEndereco();

		for (int i = 0; i < listaDeMoradores.length(); i++) {
			senha = listaDeMoradores.getJSONObject(i).getString("senha");
			apartamento = listaDeMoradores.getJSONObject(i).getString(
					"apartamento");
			nome = listaDeMoradores.getJSONObject(i).getString("nome");

			moradores.add(new Morador(nome, senha, telefone, email, endereco,
					predio, apartamento, idPredio));

			System.out.println(nome);
		}

		return moradores;
	}

	/**
	 * 
	 * Recebe todas as informacoes do morador e as atualiza no servidor, se der
	 * certo retorna true se nao retorna false.
	 * 
	 * @param idPredio
	 *            O id do predio no nosso banco de dados.
	 * @param idMorador
	 *            O id do morador no nosso banco de dados.
	 * @param nome
	 *            O nome do morador.
	 * @param senha
	 *            A senha do morador.
	 * @param apartamento
	 *            O apartamento do morador.
	 * @param login
	 *            O login do morador.
	 * 
	 * @return boolean Retorna true se o cadastro deu certo e false se nao.
	 * 
	 * @exception JSONException
	 *                Se houver algum erro na conexao com o servidor.
	 * 
	 * @see JSONException
	 */
	public static boolean atualizaMorador(int idPredio, int idMorador,
			String nome, String senha, String apartamento, String login)
			throws JSONException {

		boolean cadastro;
		String url, response;

		url = (DOMAIN + "/atualiza_morador/" + idMorador + "/" + idPredio + "/"
				+ nome + "/" + senha + "/" + apartamento + "/" + login);

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		cadastro = obj.getBoolean("cadastro");
		System.out.println(cadastro);

		return cadastro;
	}

	public static ArrayList<Acude> Acudes() throws JSONException {
		ArrayList<Acude> Acudes = new ArrayList<Acude>();
		String url, response;

		url = DOMAIN + "/info_acudes";

		response = GET(url);
		// System.out.println(response);
		JSONArray array = new JSONArray(response);

		String nome, volume, data;
		int id;
		for (int i = 0; i < array.length(); i++) {
			nome = array.getJSONObject(i).getString("nome_acude");
			volume = array.getJSONObject(i).getString("volume");
			data = array.getJSONObject(i).getString("data");
			id = array.getJSONObject(i).getInt("id");

			Acudes.add(new Acude(nome, volume, data, id));

			// System.out.println(i);
		}

		return Acudes;

	}

	public static boolean addAcudes(String nome, String volume, String data)
			throws JSONException {
		String url, response;
		boolean result;

		url = DOMAIN + "/add_info_acudes/" + nome + "/" + volume + "/" + data;

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		result = obj.getBoolean("insert_info");
		System.out.println(result);

		return result;

	}

	public static boolean cadastraMedicao(int idPredio, String quantidade,
			String unidade, String data) throws JSONException {
		boolean result;
		String url, response;

		url = DOMAIN + "/cadastra_medicao/" + idPredio + "/" + quantidade + "/"
				+ unidade + "/" + data;

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		result = obj.getBoolean("cadastra_medicao");

		// System.out.println("medicao " + result);

		return result;
	}

	public static ArrayList<Medicao> getMedicoesPorPredio(int idPredio)
			throws JSONException {
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>();

		String url, response;

		url = DOMAIN + "/medicao/" + idPredio;

		response = GET(url);

		JSONArray array = new JSONArray(response);

		String unidade, quantidade, data;
		for (int i = 0; i < array.length(); i++) {

			// unidade = array.getJSONObject(i).getString("unidade");

			quantidade = "" + array.getJSONObject(i).getDouble("quantidade");

			data = array.getJSONObject(i).getString("data");

			medicoes.add(new Medicao(Float.parseFloat(quantidade), data,
					infoPredio(idPredio)));

			// System.out.println("Medicoes: " + i);
		}

		return medicoes;
	}

	public static ArrayList<Medicao> getMedicoesPorData(int idPredio,
			String data_consulta) throws JSONException {
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>();

		String url, response;

		url = DOMAIN + "/medicao/" + idPredio + "/" + data_consulta;

		response = GET(url);

		JSONArray array = new JSONArray(response);

		String unidade, quantidade, data;
		for (int i = 0; i < array.length(); i++) {

			// unidade = array.getJSONObject(i).getString("unidade");

			quantidade = "" + array.getJSONObject(i).getDouble("quantidade");

			data = array.getJSONObject(i).getString("data");

			medicoes.add(new Medicao(Float.parseFloat(quantidade), data,
					infoPredio(idPredio)));

			// System.out.println("Medicoes: " + i);
		}

		return medicoes;
	}

	public static boolean cadastraNotificacoes(int idPredio, String texto,
			String data) throws JSONException {
		boolean result;
		String url, response;

		url = DOMAIN + "/cadastra_notificacoes/" + idPredio + "/"
				+ encodeString(texto) + "/" + data;

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);

		result = obj.getBoolean("cadastra_notificacoes");

		// System.out.println("Cadastra notificacoes " + result);

		return result;
	}

	public static ArrayList<Notificacao> getNotificacoesPorPredio(int idPredio)
			throws JSONException {
		ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();
		String url, response;

		url = DOMAIN + "/notificacoes/" + idPredio;

		response = GET(url);

		JSONArray array = new JSONArray(response);

		String data, texto;
		Predio predio = infoPredio(idPredio);

		for (int i = 0; i < array.length(); i++) {
			data = array.getJSONObject(i).getString("data");
			texto = decodeString(array.getJSONObject(i).getString("texto"));

			notificacoes.add(new Notificacao(texto, data, predio));
			// System.out.println("Notificacoes" + i);
		}

		Log.d("get notificacoes", "saiu de notificacoes " + array.length());
		return notificacoes;
	}

	public static ArrayList<Notificacao> getNotificacoesPorData(int idPredio,
			String data_consulta) throws JSONException {
		ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();
		String url, response;

		url = DOMAIN + "/notificacoes/" + idPredio + "/" + data_consulta;

		response = GET(url);

		JSONArray array = new JSONArray(response);
		Predio predio = infoPredio(idPredio);
		String data, texto;
		for (int i = 0; i < array.length(); i++) {
			data = array.getJSONObject(i).getString("data");
			texto = array.getJSONObject(i).getString("texto");

			notificacoes.add(new Notificacao(texto, data, predio));
			// System.out.println("Notificacoes" + i);
		}

		return notificacoes;
	}

	public static ArrayList<Predio> listaPredios() throws JSONException {

		ArrayList<Predio> predios = new ArrayList<Predio>();

		String url, response;

		url = DOMAIN + "/lista_predios";

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj = array.getJSONObject(0);
		JSONArray listaPredios = obj.getJSONArray("lista_predios");

		String senha, nome, bairro, cidade, rua, numero, telefone, estado, email;
		int cep, id;

		for (int i = 0; i < listaPredios.length(); i++) {
			JSONObject predioObj = listaPredios.getJSONObject(i);

			senha = predioObj.getString("senha");
			nome = predioObj.getString("nome");
			bairro = predioObj.getString("bairro");
			cidade = predioObj.getString("cidade");
			rua = predioObj.getString("rua");
			numero = predioObj.getString("numero");
			telefone = predioObj.getString("telefone");
			estado = predioObj.getString("estado");
			email = predioObj.getString("email");
			cep = predioObj.getInt("cep");
			id = predioObj.getInt("id_predio");

			predios.add(new Predio(nome, senha, telefone, email, new Endereco(
					estado, cidade, bairro, rua, numero, cep), id));

		}

		return predios;

	}

	public static ArrayList<String> listaAcudes() throws JSONException {
		ArrayList<String> nomes = new ArrayList<String>();
		String url, response;

		url = DOMAIN + "/lista_acudes";

		response = GET(url);

		JSONArray array = new JSONArray(response);
		JSONObject obj;
		for (int i = 0; i < array.length(); i++) {
			obj = array.getJSONObject(i);
			nomes.add(obj.getString("nome_acude"));
		}

		return nomes;
	}

	public static ArrayList<Medicao> info_acude_ano(String nome, String ano)
			throws JSONException {
		ArrayList<Medicao> acudes = new ArrayList<Medicao>();
		String url, response;

		url = DOMAIN + "/info_acude_ano/" + encodeString(nome) + "/" + ano;

		response = GET(url);
		JSONArray array = new JSONArray(response);
		
		JSONObject obj;
		String volume, data;
		for (int i = 0; i < array.length(); i++) {
			obj = array.getJSONObject(i);

			volume = obj.getString("volume");
			data = obj.getString("data");

			// string to data
			Calendar cal = Calendar.getInstance();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				cal.setTime(sdf.parse(data));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			acudes.add(new Medicao(Float.parseFloat(volume), cal));

		}

		return acudes;
	}

	/**
	 * 
	 * Recebe uma url e gera uma requiciao HTTP GET, retorna o resultado dessa
	 * requisicao se ela for bem sucessida se naa retorna: Did not Work!
	 * 
	 * @param url
	 *            A url a qual a requisicao sera feita.
	 * 
	 * @return String A resposta obitida apos a requisicao
	 * 
	 */
	private static String GET(String url) {
		Log.d("GET", "DENTRO DO GET");

		String resultado = "nao funcionou";

		try {
			resultado = new Consulta().execute(url).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("GET resultado", resultado);

		return resultado;
	}

	private static String encodeString(String texto) {
		String result;

		result = texto.replace(" ", "%20");

		return result;
	}

	private static String decodeString(String texto) {
		String result;

		result = texto.replace("%20", " ");

		return result;
	}

	private static Calendar ultimoDomingo(Calendar dia) {
		Calendar domingo = (Calendar) dia.clone();

		while (true) {

			if (domingo.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				break;
			}

			domingo.roll(Calendar.DAY_OF_MONTH, false);
		}

		return domingo;
	}

	private static ArrayList<String> diasDaUltimaSemana(Calendar dia) {
		// Calendar dia = Calendar.getInstance();
		Calendar ultimoDomingo = ultimoDomingo(dia);

		ArrayList<String> dias = new ArrayList<String>();
		String data = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		while (true) {
			data = sdf.format(ultimoDomingo.getTime()).toString();
			System.out.println(data);

			dias.add(data);

			if (ultimoDomingo.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				break;
			}

			ultimoDomingo.roll(Calendar.DAY_OF_YEAR, false);

		}

		return dias;
	}

	private static float somaValoresMedicoes(ArrayList<Medicao> medicoes) {
		float result = 0;

		for (Medicao medicao : medicoes) {
			result += medicao.getValor();
		}

		return result;
	}

	public static float resultadoMedicoesSemanaPassada(int idPredio) {
		float result = 0;
		Calendar ultimoDomingo = ultimoDomingo(Calendar.getInstance());
		ArrayList<String> diasDaSemanaPassada = diasDaUltimaSemana(ultimoDomingo);

		for (String dia : diasDaSemanaPassada) {

			try {
				result += somaValoresMedicoes(getMedicoesPorData(idPredio, dia));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public static float resultadoMedicoesSemanaTrasada(int idPredio) {
		float result = 0;
		Calendar ultimoDomingo = ultimoDomingo(Calendar.getInstance());

		ultimoDomingo.roll(Calendar.DAY_OF_YEAR, false); // Volta 1 dia pra
															// pegar a semana
															// trasada
		Calendar domingoTrasado = ultimoDomingo(ultimoDomingo);

		ArrayList<String> diasDaSemanaPassada = diasDaUltimaSemana(domingoTrasado);

		for (String dia : diasDaSemanaPassada) {

			try {
				result += somaValoresMedicoes(getMedicoesPorData(idPredio, dia));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

}