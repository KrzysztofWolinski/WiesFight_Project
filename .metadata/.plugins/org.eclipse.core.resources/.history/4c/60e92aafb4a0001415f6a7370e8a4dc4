package com.wiesfight.enums;

import android.view.View;
import com.wiesfight.R;

public enum Tutorial {
	MAIN(R.string.tutorialMain, R.drawable.blachara, View.VISIBLE, View.INVISIBLE),
	SHOP(R.string.tutorialShop, 0, View.INVISIBLE, View.VISIBLE);
	
	private final int btnNextVisible;
	private final int btnBackVisible;
	private final int text;
	private final int screenFile;
	
	private Tutorial(int text, int file, int btnNext, int btnBack) {
		this.btnBackVisible = btnBack;
		this.btnNextVisible = btnNext;
		this.text = text;
		this.screenFile = file;
	}
	
	public int getBtnNextVisibility() {
		return this.btnNextVisible;
	}
	
	public int getBtnBackVisibility() {
		return this.btnBackVisible;
	}
	
	public int getText() {
		return this.text;
	}
	
	public int getFilename() {
		return this.screenFile;
	}
}
