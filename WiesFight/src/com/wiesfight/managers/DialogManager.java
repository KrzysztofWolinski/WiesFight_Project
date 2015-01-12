package com.wiesfight.managers;

import com.wiesfight.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class DialogManager {
	public static void showInfoDialog(final Activity ctx, final String infoText) {
		ctx.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				LayoutInflater inflater = ctx.getLayoutInflater();
			    View view = inflater.inflate(R.layout.dialog_ok, null);
			    final AlertDialog dialog = new AlertDialog.Builder(ctx)
			    	.setView(view).create();
			    Button btn1 = (Button) view.findViewById(R.id.btnOk);
			    TextView txt = (TextView) view.findViewById(R.id.txtMessageOk);
			    txt.setText(infoText);
			    btn1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			    if (!ctx.isFinishing()) {
			    	dialog.show();
			    }
			}
		});
	}
	
	public static Boolean showConfirmDialog(Activity ctx, String text) {
		return false;
	}
}
