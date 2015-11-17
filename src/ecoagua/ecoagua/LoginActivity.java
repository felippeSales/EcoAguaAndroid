package ecoagua.ecoagua;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;

import com.ecoagua.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ecoagua.controller.API;
import ecoagua.ecoagua.grafico.SemanaActivity;
import ecoagua.model.Acude;
import ecoagua.model.Medicao;
import ecoagua.model.Morador;
import ecoagua.model.Predio;
import ecoagua.model.Ranking;
import ecoagua.model.Usuario;

public class LoginActivity extends Activity{
	private TextView etCadastrar;
	private Button btnEntrar;
	private EditText etLogin;
	private EditText etSenha;
	private View loading;
	public static List<Medicao> medicoesAcude;
	
	private void logar() {
		btnEntrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loading.setVisibility(View.VISIBLE);
				String login = etLogin.getText().toString();
				String senha = etSenha.getText().toString();
				
				try {
					if(API.login(login, senha)){
						
						setMedicoes();
						setRanking();
						setColocacao();
						setDadosAcude();
						
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);
					}else{
						String msg = "Usuário não existe.";
						String title = "Login";
						Dialogo.showDialogo(title, msg, LoginActivity.this);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			private void setDadosAcude() {
				try {
					medicoesAcude = API.info_acude_ano("Epitacio Pessoa", "2015");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			/**
			 * Set a colocacao da lista de predios baseado no ranking
			 */
			private void setColocacao() {
				for(int i=0; i<Ranking.getPredios().size(); i++){
					if(Ranking.getPredios().get(i).getId() == API.user.getId()){
						API.user.setColocacao(i+1);
					}

					Ranking.getPredios().get(i).setColocacao(i+1);
					
				}
				
			}

			/**
			 * Set the ranking list of predios
			 */
			private void setRanking(){
				List<Predio> predios = null;
				try {
					predios = API.listaPredios();
					Ranking.rankeia(predios);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			/**
			 * Assim que confirmado o login, seta as medicoes dependendo do tipo de usuario
			 * @throws JSONException 
			 */
			private void setMedicoes() {
				try {
					API.user.setMedicoes(API.getMedicoesPorPredio(API.user.getId()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	}

	private void cadastrar() {
		etCadastrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this, CadastroPredioActivity.class);
				startActivity(intent);
			}
		});
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		etCadastrar = (TextView) findViewById(R.id.login_text_cadastrarse);
		btnEntrar = (Button) findViewById(R.id.btn_entrar);
		etLogin = (EditText) findViewById(R.id.et_login);
		etSenha = (EditText) findViewById(R.id.et_senha);
		loading = (View) findViewById(R.id.loginLoading);
		
		loading.setVisibility(View.INVISIBLE);
		
		cadastrar();

		logar();
	}
}
