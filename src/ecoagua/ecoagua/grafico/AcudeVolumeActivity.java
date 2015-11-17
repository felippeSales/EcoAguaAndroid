package ecoagua.ecoagua.grafico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import ecoagua.model.Medicao;

import com.ecoagua.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class AcudeVolumeActivity extends Activity {
	private Calendar dia;
	private GraphView graph;
	private LineGraphSeries<DataPoint> series;
	private List<Medicao> medicoes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acude_volume);

		dia = Calendar.getInstance();
		medicoes = new ArrayList<Medicao>();

		graph = (GraphView) findViewById(R.id.graph_acude);

		criaGrafico();
	}

	private void geraListaMedicoes() {
		/*Calendar temp = Calendar.getInstance();
		temp.set(Calendar.YEAR, 2011);
		*/
		Float medidas[] = { 13.7f, 39.7f, 24.65f, 14.93f, 7.68f }; //dados de porcentagens pegos do tableau
		String datas[] = {"31-12-2011", "31-12-2012", "31-12-2013", "31-12-2014", "31-12-2015"}; 
		
		for (int i = 0; i < medidas.length; i++) {
			Calendar cal = Calendar.getInstance();
			try { 
				String data = datas[i]; 
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  
				cal.setTime(sdf.parse(data)); 
				} catch (ParseException e) { 
				e.printStackTrace(); 
				} 
			medicoes.add(new Medicao(medidas[i], cal));
			//temp.add(Calendar.YEAR, 1);

		}
	}

	private void criaGrafico() {
		Serie serie = new Serie();

		String titulo = "Volume (%)";
		int cor = Color.BLUE;
		boolean drawDataPoints = true;
		int raio = 8;
		geraListaMedicoes();
		List<Medicao> m = medicoes;
		int numX = m.size();
		Context context = AcudeVolumeActivity.this;
		boolean showTextOnPointClick = true;

		String legendaT = "Volume: ";
		String unidade = "%";
		
		series = serie.criaSerie(titulo, cor, drawDataPoints, raio, dia,
				m, numX, context, showTextOnPointClick, legendaT, unidade);

		Grafico grafico = new Grafico();

		boolean legenda = true;
		boolean scroll = true;

		boolean comLabels = false;
		int numLabels = 7;
		boolean soIntEmX = false;
		String tituloX = "Ano";

		grafico.criaGrafico(graph, series, legenda, comLabels, numLabels,
				soIntEmX, scroll, tituloX, titulo);
	}
}
