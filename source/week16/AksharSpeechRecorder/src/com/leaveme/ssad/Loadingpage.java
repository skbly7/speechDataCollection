
package com.leaveme.ssad;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

/**
 * @author romil
 *
 */
public class Loadingpage extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.frontpage);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally{
					File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AksharRecorder");
					folder.mkdir();
					Intent openStartingPoint=new Intent("android.intent.action.MAI");
					startActivity(openStartingPoint);
					finish();
				}
			}
		};
		timer.start();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
		return;
	}
}
