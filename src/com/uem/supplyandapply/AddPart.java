package com.uem.supplyandapply;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddPart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_part);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_part, menu);
		return true;
	}

}
