package com.leaveme.ssad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
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
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.leaveme.ssad.R;
//import com.leaveme.ssad.MainActivity.PlayButton;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);        
        Button record = (Button)findViewById(R.id.button2);
        record.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 	mRecorder = new MediaRecorder();
			        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			        mRecorder.setOutputFile(mFileName);
			        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			        try {
			            mRecorder.prepare();
			        } catch (IOException e) {
			            Log.e(LOG_TAG, "prepare() failed");
			        }
			        mRecorder.start();				
			}
		});
        Button stopRecord = (Button)findViewById(R.id.button3);
        stopRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRecorder.stop();
		        mRecorder.release();
		        mRecorder = null;
			}        	
        });
        Button play = (Button)findViewById(R.id.button5);
        play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mStartPlaying){
					mPlayer = new MediaPlayer();
			        try {
			            mPlayer.setDataSource(mFileName);
			            mPlayer.prepare();
			            mPlayer.start();
			        } catch (IOException e) {
			            Log.e(LOG_TAG, "prepare() failed");
			        }
					
				}
				else{
					mPlayer.release();
			        mPlayer = null;
				}
				mStartPlaying=!mStartPlaying;
			}
        	
        });
        Button open = (Button)findViewById(R.id.button1);
        open.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		text = (TextView) findViewById(R.id.fileArea);
        		try {
        			// open the file for reading
        			instream = new FileInputStream("textfile1.txt");

        			// if file the available for reading
        			if (instream != null) {
        			  // prepare the file for reading
        			  inputreader = new InputStreamReader(instream);
        			  buffreader = new BufferedReader(inputreader);

        			  
        			  line[j] = buffreader.readLine();
        			  text.setText(line[j]);
        			  do {
        				  	j+=1;
        				     line[j] = buffreader.readLine();
        				    // do something with the line 
        				  } while (line[j] != null);
        			  n=j;
        			  j=0;

        			  Button next = (Button) findViewById(R.id.button6);
        			  next.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(j!=n){
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
    private int i=0;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
    boolean mStartPlaying = true;
    InputStream in;
    BufferedReader reader;
    String[] line;
    TextView text;
    InputStream instream;
    InputStreamReader inputreader;
    BufferedReader buffreader;
    int j=0;
    int n=0;
    
    public void AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName +="/" +i+ "audiorecordtest.3gp";
        i+=1;
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
