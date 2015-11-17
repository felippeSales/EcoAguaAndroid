package ecoagua.ecoagua.grafico;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.widget.Toast;
import ecoagua.model.CalendarUtils;
import ecoagua.model.Medicao;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class Serie {

	protected Serie() {

	}

	/**
	 * Cria a serie a ser plotada no grafico
	 * @param serie
	 * @param titulo
	 * @param cor
	 * @param drawDataPoints
	 * @param raio
	 * @param thickness
	 * @param data
	 * @param medicoes
	 * @param numX
	 * @param context
	 * @param textoPointClick
	 * @param showTextOnPointClick
	 * @return
	 */
	public LineGraphSeries<DataPoint> criaSerie( String titulo, int cor,
			boolean drawDataPoints, int raio, Calendar data,
			List<Medicao> medicoes, int numX, Context context, boolean showTextOnPointClick, String legenda, String unidade) {

		// parametros
		DataPoint[] pontos = criaDataPoints(numX, data, medicoes);
		LineGraphSeries<DataPoint> serie = setSeries(pontos);
		setPropriedadesSerie(serie, titulo, cor);
		drawDataPoints(serie, drawDataPoints, raio);
		showTextoOnClick(serie, context, medicoes, showTextOnPointClick, legenda, unidade);
		return serie;
	}

	public DataPoint[] criaDataPoints(int qtdDias, Calendar data,
			List<Medicao> medicoes) {
		int start = 1;
		DataPoint[] pontos = new DataPoint[qtdDias];

		// mudar o valor de 1 para os dados de medicao
		while ((start < qtdDias + 1) && (start < medicoes.size() + 1)) {
			// substituir o 1 pelo dado da lista de medicoes
			pontos[start - 1] = new DataPoint(start, medicoes.get(start - 1)
					.getValor());
			start++;
		}

		return pontos;
	}

	public LineGraphSeries<DataPoint> setSeries(DataPoint[] points) {
		return new LineGraphSeries<DataPoint>(points);
	}

	public void setPropriedadesSerie(LineGraphSeries<DataPoint> serie,
			String titulo, int cor) {
		// legenda
		serie.setTitle(titulo);
		// styling series
		serie.setColor(cor);
	}

	public void drawDataPoints(LineGraphSeries<DataPoint> serie,
			boolean drawDataPoint, int raio) {
		if (drawDataPoint) {

			serie.setDrawDataPoints(true);
			serie.setDataPointsRadius(raio);
			serie.setThickness(raio);
		}
	}

	public void showTextoOnClick(LineGraphSeries<DataPoint> serie,
			final Context context, final List<Medicao> medicoes,
			boolean show, final String legenda, final String unidade) {
		if (show) {
			serie.setOnDataPointTapListener(new OnDataPointTapListener() {
				@Override
				public void onTap(Series series, DataPointInterface dataPoint) {
					
					
					 Calendar temp = medicoes.get((int)dataPoint.getX()-1).getData(); 
					 
					 String volume= legenda + String.format("%.2f", dataPoint.getY()) + unidade;
					 Toast.makeText( context,  volume + "\nData: " +
					  CalendarUtils.getDataFormatadaSemHoras(temp) ,
					  Toast.LENGTH_SHORT).show();
				}
			});
		}

	}
}
