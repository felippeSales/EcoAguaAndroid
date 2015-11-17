package ecoagua.ecoagua;

import com.ecoagua.R;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import ecoagua.ecoagua.grafico.MesActivity;
import ecoagua.ecoagua.grafico.SemanaActivity;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		
		setTabHost();
	}

	private void setTabHost() {
		final TabHost mTabHost = (TabHost) findViewById(android.R.id.tabhost);

	    mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator("", getResources().getDrawable(R.drawable.home)).setContent(new Intent(this  ,PerfilActivity.class )));
	    mTabHost.addTab(mTabHost.newTabSpec("ranking").setIndicator("", getResources().getDrawable(R.drawable.ranking)).setContent(new Intent(this , RankingActivity.class )));
	    mTabHost.addTab(mTabHost.newTabSpec("estatisticas").setIndicator("", getResources().getDrawable(R.drawable.grafico)).setContent(new Intent(this , EstatisticasActivity.class )));
	    mTabHost.addTab(mTabHost.newTabSpec("notificacoes").setIndicator("", getResources().getDrawable(R.drawable.notificacoes)).setContent(new Intent(this , NotificacoesActivity.class )));
	    mTabHost.setCurrentTab(0);
	    
	    mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String arg0) {
				setTabColor(mTabHost);
			}
		});
		setTabColor(mTabHost);
		
	}
	
	private void setTabColor(TabHost tabhost) {
		for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
			tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#62b3cc")); //unselected
		}
		if(tabhost.getCurrentTab() == 0) {
			tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#b5eef7")); //1st tab selected
		} else {
			tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#b5eef7")); //2nd tab selected
		}
	}
}
