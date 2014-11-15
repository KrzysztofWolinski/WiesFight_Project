package com.wiesfight.activities;

import com.wiesfight.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PreFightActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_fight);
	}
	
	public void startTraining(View v) {
		Intent intent = new Intent(this, FightActivity.class);
		intent.putExtra("training", true);
    	startActivity(intent);
	}
}
