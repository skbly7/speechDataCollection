
package com.leaveme.ssad;

import java.io.File;
import java.sql.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author romil
 *
 */
public class selectionpage extends Activity{
	Button word;
	Button sentence;
	Bundle bundle;
	String name;
	String gender;
	String age;
	String language;
	String type;
	private boolean _doubleBackToExitPressedOnce=false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.secpage);
		bundle=getIntent().getExtras();
		name=bundle.getString("name");
		age=bundle.getString("age");
		gender=bundle.getString("gender");
		language=bundle.getString("language");
		type=bundle.getString("type");
		word=(Button)findViewById(R.id.button21);
		word.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openStartingPoint=new Intent("android.intent.action.MA");
				openStartingPoint.putExtra("name", name);
	            openStartingPoint.putExtra("gender", gender);
	            openStartingPoint.putExtra("age", age);
	            openStartingPoint.putExtra("language", language);
	            openStartingPoint.putExtra("type", type);
				startActivity(openStartingPoint);
				finish();
			}
		});
		sentence=(Button)findViewById(R.id.button22);
		sentence.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openStartingPoint=new Intent("android.intent.action.A");
				openStartingPoint.putExtra("name", name);
	            openStartingPoint.putExtra("gender", gender);
	            openStartingPoint.putExtra("age", age);
	            openStartingPoint.putExtra("language", language);
	            openStartingPoint.putExtra("type", type);
				startActivity(openStartingPoint);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		if (_doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}
		this._doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Press again to quit", Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				_doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}
}
