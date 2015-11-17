package ecoagua.ecoagua;

import org.json.JSONException;

import com.ecoagua.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import ecoagua.controller.API;
import ecoagua.model.Endereco;

public class CadastroMoradorActivity extends Activity {
	private Button btnCadastrar;
	private EditText etNome;
	private EditText etSenha1;
	private EditText etPredio;
	private EditText etApartamento;
	
	private void cadastrarMorador() {
		btnCadastrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String nome = etNome.getText().toString();
				String senha = etSenha1.getText().toString();
				String predio = etPredio.getText().toString();
				String apartamento = etApartamento.getText().toString();
				
				try {
					if(API.cadastraMorador(API.user.getId(), nome, senha, apartamento, nome)){
						Intent intent = new Intent(CadastroMoradorActivity.this, LoginActivity.class);
						startActivity(intent);
					}else{
						//morador já existe
						String msg = "Este morador já está cadastrado.";
						String title = "Cadastrar morador";
						
						Dialogo.showDialogo(title, msg, CadastroMoradorActivity.this);
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_morador);

		btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_morador);
		etNome = (EditText) findViewById(R.id.tf_morador_nome);
		etSenha1 = (EditText) findViewById(R.id.tf_morador_senha1);
		etPredio = (EditText) findViewById(R.id.tf_morador_predio);
		etApartamento = (EditText) findViewById(R.id.tf_morador_ap);
		
		cadastrarMorador();
	}

	

}
