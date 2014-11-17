package com.wiesfight.activities;

import com.wiesfight.R;
import com.wiesfight.enums.Tutorial;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialActivity extends Activity {
	private int currentStep = 0;
	private Button btnNext;
	private Button btnBack;
	private ImageView img;
	private TextView txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
		this.btnNext = (Button) findViewById(R.id.btnNext);
		this.btnBack = (Button) findViewById(R.id.btnBack);
		this.img = (ImageView) findViewById(R.id.imgTutorial);
		this.txt = (TextView) findViewById(R.id.txtTutorial);
		this.drawControls();
	}

	private void drawControls() {
		Tutorial step = Tutorial.values()[currentStep];
		this.btnNext.setVisibility(step.getBtnNextVisibility());
		this.btnBack.setVisibility(step.getBtnBackVisibility());
		this.txt.setText(step.getText());
		this.img.setImageResource(step.getFilename());
	}
	
	public void btnNext_Click(View v) {
		this.currentStep++;
		this.drawControls();
	}
	
	public void btnBack_Click(View v) {
		this.currentStep--;
		this.drawControls();
	}
}
