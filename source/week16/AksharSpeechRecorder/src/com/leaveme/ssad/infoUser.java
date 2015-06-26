package com.leaveme.ssad;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.app.ListActivity;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import com.leaveme.ssad.R;


/**
 * @author romil
 *
 */
public class infoUser extends Activity {
	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;
	private static String OFileName = null;
	private static String pFileName = null;
	private static String tFileName = null;
	private int i=0;
	private MediaPlayer   mPlayer = null;
	boolean mStartPlaying = true;
	boolean mStartRecording = false;
	boolean alwaysPlay = false;
	boolean alwaysRecord = true;
	private static int RECORDER_SAMPLERATE = 16000;
	private static final int RECORDER_BPP = 16; //bits per sample
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private AudioRecord recorder = null;
	private Thread recordingThread = null;
	int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
	int BytesPerElement = 2; // 2 bytes in 16bit format
	int bufferSize=0;
	String filename;
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
	boolean playCheck=true;
	boolean recordCheck=false;
	int n=0;
	String first;
	String filename2;
	int ab;
	private List<String> items = null;
	ArrayAdapter<String> fileList;
	Bundle bundle;
	Button next=null;
	Button previous=null;
	String sec=null;
	String UserName=null;
	final int MSG_START_TIMER = 0;
	final int MSG_STOP_TIMER = 1;
	final int MSG_UPDATE_TIMER = 2;
	Chronometer chronometer=null;
	int item1=1;
	int item2=1;
	int item3=0;
	int item4=1;
	String name = null;
	String environment = null;
	String age=null;
	String oS=null;
	String time=null;
	String language=null;
	String place=null;
	String education=null;
	String gender=null;
	String type=null;
	String name1 = null;
	String environment1 = null;
	String age1=null;
	String oS1=null;
	String time1=null;
	String language1=null;
	String place1=null;
	String education1=null;
	String gender1=null;
	String type1=null;
	Button send;
	//String[] option;
	String filename1223=null;
	String filename1234=null;
	EditText ET;
	EditText ET2;
	EditText ET3;
	EditText ET4;
	private boolean _doubleBackToExitPressedOnce    = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.info); 
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		i=pref.getInt("key",0);
		ET = (EditText)findViewById(R.id.editText1);
		ET2 = (EditText)findViewById(R.id.editText2);
		ET4 = (EditText)findViewById(R.id.editText4);
		final Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		final Spinner spinner2= (Spinner)findViewById(R.id.spinner2);
		final Spinner spinner3 = (Spinner)findViewById(R.id.spinner3);
		final Spinner spinner4 = (Spinner)findViewById(R.id.spinner4);
		final Spinner spinner5 = (Spinner)findViewById(R.id.Spinner5);
		final Spinner spinner6 = (Spinner)findViewById(R.id.Spinner6);
		send = (Button)findViewById(R.id.button111);
		send.setOnClickListener(new View.OnClickListener() {
			@Override

			public void onClick(View v) {
				name=ET.getText().toString().trim();
				age=ET2.getText().toString().trim();
				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String formattedDate = df.format(c.getTime());
				time=formattedDate;
				place=ET4.getText().toString().trim();
				environment=(String) spinner.getSelectedItem();
				oS=(String) spinner2.getSelectedItem();
				language=(String) spinner3.getSelectedItem();
				education=(String) spinner4.getSelectedItem();
				gender=(String) spinner5.getSelectedItem();
				type=(String) spinner6.getSelectedItem();
				AudioRecordTest();
				name1="Name: "+name+"\n";
				age1="Age: "+age+"\n";
				environment1="Environment: "+environment+"\n";
				oS1="PhoneModel: "+oS+"\n";
				time1="Date: "+time+"\n";
				language1="Language: "+language+"\n";
				place1="Place: "+place+"\n";
				education1="Education: "+education+"\n";
				gender1="Gender: "+gender+"\n";
				type1="Type: "+type+"\n";
				if(!name.equals("") && !age.equals("")){
					int age2=Integer.parseInt(age);
					if(age2>0 && age2<99){
						try {
							FileOutputStream fos = new FileOutputStream(filename1223+filename1234+"/"+"info.txt");
							GZIPOutputStream gzos = new GZIPOutputStream(fos);
							ObjectOutputStream out = new ObjectOutputStream(gzos);
							fos.write(name1.getBytes());
							fos.write(age1.getBytes());
							fos.write(environment1.getBytes());
							fos.write(oS1.getBytes());
							fos.write(time1.getBytes());
							fos.write(language1.getBytes());
							fos.write(place1.getBytes());
							fos.write(education1.getBytes());
							fos.write(gender1.getBytes());
							fos.write(type1.getBytes());
							//out.flush();
							out.close();
							Intent openStartingPoint=new Intent("android.intent.action.QW");
							openStartingPoint.putExtra("name", name);
							openStartingPoint.putExtra("gender", gender);
							openStartingPoint.putExtra("age", age);
							openStartingPoint.putExtra("language", language);
							openStartingPoint.putExtra("type", type);
							startActivity(openStartingPoint);
							finish();
						}
						catch (IOException e) {
							e.getStackTrace();
						}
					}
					else{
						Toast.makeText(getApplicationContext(), "Enter valid age",
								Toast.LENGTH_LONG).show();
					}
				}
				else{
					Toast.makeText(getApplicationContext(), "Insufficient data provided",
							Toast.LENGTH_LONG).show();
				}
			}
		});	


	}


	public void AudioRecordTest() {
		if(name!=null && gender!=null && age!=null && language!=null&&type!=null){
			filename1223=Environment.getExternalStorageDirectory().getAbsolutePath() ;
			filename1234="/AksharRecorder/"+name+"_"+gender+"_"+age+"_"+language+"_"+type;
			File folder = new File(filename1223+filename1234);

			if (!folder.exists()) {
				i=0;
				boolean success;
				success = folder.mkdirs();
			}
			File filename = new File(filename1223+filename1234+"/"+"info.txt");
		}
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
		if (id == R.id.action_samplerate) {
			final CharSequence rates[] = new CharSequence[] {"8000", "16000", "22050", "32000"};
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Pick SampleRate ");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			builder.setSingleChoiceItems(rates,item1,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					item1 = which;
					if(rates[which]=="8000"){RECORDER_SAMPLERATE =8000;}
					else if(rates[which]=="16000"){RECORDER_SAMPLERATE =16000;}
					else if(rates[which]=="22050"){RECORDER_SAMPLERATE =22050;}
					else if(rates[which]=="32000"){RECORDER_SAMPLERATE =32000;}
				}
			});
			builder.show();
			return true;
		}
		if (id == R.id.action_alwaysplay){
			final CharSequence[] aPlay = {"Yes","No"};
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Always Play file Before recording next file");
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			alert.setSingleChoiceItems(aPlay,item2, new 
					DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					item2 = which;
					if(aPlay[which]=="Yes")
					{
						alwaysPlay=true;
					}
					else if (aPlay[which]=="No")
					{
						alwaysPlay=false;
					}
				}
			});
			alert.show();
			return true;
		}
		if (id == R.id.action_alwaysrecord){
			final CharSequence[] aRecord = {"Yes","No"};
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Record each line before moving to the next");
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			alert.setSingleChoiceItems(aRecord,item3, new 
					DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					item3 = which;
					if(aRecord[which]=="Yes")
					{
						alwaysRecord=true;
					}
					else if (aRecord[which]=="No")
					{
						alwaysRecord=false;
					}
				}
			});
			alert.show();
			return true;
		}
		if (id == R.id.action_audioencoding){
			final CharSequence[] aRecord = {"PCM_8BIT","PCM_16BIT"};
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Select the audio encoding system ");
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			alert.setSingleChoiceItems(aRecord,item4, new 
					DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					item4 = which;
					if(aRecord[which]=="PCM_8BIT")
					{
						RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_8BIT;
					}
					else if (aRecord[which]=="PCM_16BIT")
					{
						RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
					}
				}
			});
			alert.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
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

	@Override
	protected void onStop(){
		super.onStop();
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
		Editor editor = pref.edit();
		editor.putInt("key", i);
		editor.commit();
	}

}
