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

public class CadastroPredioActivity extends Activity {
	private Button btnCadastrar;
	private EditText etNome;
	private EditText etSenha1;
	private EditText etTelefone;
	private EditText etEmail;
	private EditText etEstado;
	private EditText etCidade;
	private EditText etBairro;
	private EditText etRua;
	private EditText etNumero;
	private EditText etCep;

	private void cadastrarPredio() {
		btnCadastrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String nome = etNome.getText().toString();
				String senha = etSenha1.getText().toString();
				String telefone = etTelefone.getText().toString();
				String email = etEmail.getText().toString();
				String estado = etEstado.getText().toString();
				String cidade = etCidade.getText().toString();
				String bairro = etBairro.getText().toString();
				String rua = etRua.getText().toString();
				String numero = etNumero.getText().toString();
				int cep = Integer.parseInt(etCep.getText().toString());

				Endereco e = new Endereco(estado, cidade, bairro, rua, numero,
						cep);
				
				try {
					API.cadastraPredio(nome, senha, telefone, email, estado, cidade, bairro, rua, numero, cep, nome);
					Intent intent = new Intent(CadastroPredioActivity.this, MainActivity.class);
					startActivity(intent);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_predio);

		btnCadastrar = (Button) findViewById(R.id.btn_cadastrar_predio);

		etNome = (EditText) findViewById(R.id.tf_predio_nome);
		etSenha1 = (EditText) findViewById(R.id.tf_predio_senha1);
		etTelefone = (EditText) findViewById(R.id.tf_predio_telefone);
		etEmail = (EditText) findViewById(R.id.tf_predio_email);
		etEstado = (EditText) findViewById(R.id.tf_predio_estado);
		etCidade = (EditText) findViewById(R.id.tf_predio_cidade);
		etBairro = (EditText) findViewById(R.id.tf_predio_bairro);
		etRua = (EditText) findViewById(R.id.tf_predio_rua);
		etNumero = (EditText) findViewById(R.id.tf_predio_numero);
		etCep = (EditText) findViewById(R.id.tf_predio_cep);

		cadastrarPredio();
	}
}
