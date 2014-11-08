package com.leaveme.ssad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.leaveme.ssad.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main); 
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        i=pref.getInt("key",0);
        record = (ImageView)findViewById(R.id.button2);
        record.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mStartPlaying){
					 if(mStartRecording){
						 recordCheck=1;
						 mRecorder = new MediaRecorder();
						 mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
						 mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
						 AudioRecordTest();
						 mRecorder.setOutputFile(mFileName);
						 mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	
						 try {
							 mRecorder.prepare();
						 } 
						 catch (IOException e) {
				            Log.e(LOG_TAG, "prepare() failed");
						 }
						 mRecorder.start();	
						 record.setImageResource(R.drawable.stop);
						 mStartRecording=!mStartRecording;
					 }
					 else{
						 mRecorder.stop();
					     mRecorder.release();
					     mRecorder = null;
					     record.setImageResource(R.drawable.record_button);
					     mStartRecording=!mStartRecording;
					 }
				}
			}
		});
        
        play = (ImageView)findViewById(R.id.button5);
        play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mStartRecording && recordCheck==1){
					if(mStartPlaying){
						mPlayer = new MediaPlayer();
				        try {
				            mPlayer.setDataSource(mFileName);
				            mPlayer.prepare();
				            mPlayer.start();
				            play.setImageResource(R.drawable.stop);
				        } catch (IOException e) {
				            Log.e(LOG_TAG, "prepare() failed");
				        }
				        mStartPlaying=!mStartPlaying;
					}
					else{
						mPlayer.release();
				        mPlayer = null;
				        play.setImageResource(R.drawable.play_button);
				        mStartPlaying=!mStartPlaying;
					}
				}
			}
        	
        });
        open = (Button)findViewById(R.id.button1);
        open.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		text = (TextView) findViewById(R.id.fileArea);
        		OpenFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
    			OpenFileName+="/SSAD/new.txt";
    			j=0;
    			line = new String[100];
        		try {
        			// open the file for reading
        			instream = new FileInputStream(OpenFileName);

        			// if file the available for reading
        			if (instream != null) 
        			{
        			  // prepare the file for reading
        			  inputreader = new InputStreamReader(instream);
        			  buffreader = new BufferedReader(inputreader);

        			  try {
        				  line[j] = buffreader.readLine();
        				  text.setText(line[0]);
        			  }
        			  catch (Exception e) {
        				  text.setText(e.getMessage());
        			  }
        			  while (line[j] != null) {
        				  	j+=1;
        				    line[j] = buffreader.readLine(); 
        				  }
        			  n=j;
        			  j=0;

        			  Button next = (Button) findViewById(R.id.button6);
        			  next.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(j<n-1){
								j+=1;
								text.setText(line[j]);
							}	        			
						}
					});
        			  Button previous = (Button) findViewById(R.id.button4);
        			  previous.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {			
							if(j>0){
								j=j-1;
								text.setText(line[j]);
							}
						}
					});
        			 

        			}
        			else{
        				text.setText(mFileName);
        			}
        		}
        		catch (Exception ex) {
        			ex.printStackTrace();
        		}
    		}
        });	
    }
        

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    private static String OpenFileName = null;
    private int i=0;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
    boolean mStartPlaying = true;
    boolean mStartRecording = true;
    InputStream in;
    BufferedReader reader;
    String[] line;
    TextView text;
    InputStream instream;
    InputStreamReader inputreader;
    BufferedReader buffreader;
    ImageView record;
    ImageView play;
    Button open;
    int j=0;
    int recordCheck=0;
    int n=0;
    
    public void AudioRecordTest() {
    	File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SSAD19");
    	boolean success = true;
    	
    	if (!folder.exists()) {
    	    success = folder.mkdir();
    	    if(success){
    	    	mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
    	    	mFileName +="/SSAD19/" +i+ "audiorecordtest.3gp";
    	    	i+=1;
    	    }
    	    else{
    	    	mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
    	    	mFileName +="/" +i+ "audiorecordtest.3gp";
    	    	i+=1;
    	    }
    	}
    	else{
    		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
	    	mFileName +="/SSAD19/" +i+ "audiorecordtest.3gp";
	    	i+=1;
    	}
    }
    protected void onStop(){
        super.onStop();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
        Editor editor = pref.edit();
        editor.putInt("key", i);
        editor.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    
}
