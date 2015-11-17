package ecoagua.ecoagua;

import com.ecoagua.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ecoagua.ecoagua.grafico.AcudeVolumeActivity;
import ecoagua.ecoagua.grafico.AcudeVolumeAtualActivity;
import ecoagua.ecoagua.grafico.MesActivity;
import ecoagua.ecoagua.grafico.SemanaActivity;
public class EstatisticasActivity extends Activity {
	private Button btnMensal;
	private Button btnSemanal;
	private Button btnVolume;
	private Button btnVolumeAtual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estatisticas);
		btnMensal = (Button) findViewById(R.id.btn_grafico_mensal);
		btnSemanal = (Button) findViewById(R.id.btn_grafico_semanal);
		btnVolume = (Button) findViewById(R.id.btn_grafico_acude_volume);
		btnVolumeAtual = (Button) findViewById(R.id.btn_grafico_acude_volume_atual);
		
		verGraficoConsumoMensal();
		verGraficoConsumoSemanal();
		verGraficoAcudeVolume();
		verGraficoAcudeVolumeAtual();
	}

	private void verGraficoAcudeVolume() {
		btnVolume.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EstatisticasActivity.this, AcudeVolumeActivity.class);
				startActivity(intent);
				
			}
		});
		
	}
	
	private void verGraficoAcudeVolumeAtual() {
		btnVolumeAtual.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EstatisticasActivity.this, AcudeVolumeAtualActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	private void verGraficoConsumoSemanal() {
		btnSemanal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EstatisticasActivity.this, SemanaActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	private void verGraficoConsumoMensal() {
		btnMensal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EstatisticasActivity.this, MesActivity.class);
				startActivity(intent);
				
			}
		});
		
	}


}
