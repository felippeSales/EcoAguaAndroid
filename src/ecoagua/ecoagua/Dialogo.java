package ecoagua.ecoagua;

import com.ecoagua.R;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class Dialogo {
	@SuppressWarnings("deprecation")
	public static void showDialogo(String title, String msg, Context context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int which) {
				// here you can add functions
			}
		});
		alertDialog.setIcon(R.drawable.alerta);
		alertDialog.show();
	}
}