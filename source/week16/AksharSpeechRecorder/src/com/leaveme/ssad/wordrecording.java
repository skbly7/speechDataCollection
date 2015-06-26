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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.renderscript.Sampler;
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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.leaveme.ssad.R;

/**
 * @author romil
 * 
 */
public class wordrecording extends Activity {
	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;
	private static String OFileName = null;
	private static String pFileName = null;
	private static String[] tFileName = null;
	private int i = 0;
	private MediaPlayer mPlayer = null;
	boolean mStartPlaying = true;
	boolean mStartRecording = false;
	boolean alwaysPlay = false;
	boolean alwaysRecord = true;
	private static int RECORDER_SAMPLERATE = 16000;
	private static final int RECORDER_BPP = 16; // bits per sample
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private AudioRecord recorder = null;
	private Thread recordingThread = null;
	int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we
	// use only 1024
	int BytesPerElement = 2; // 2 bytes in 16bit format
	int bufferSize = 0;
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
	int j = 0;
	boolean playCheck = true;
	boolean recordCheck = false;
	int n = 0;
	String first;
	String filename2;
	int ab;
	private List<String> items = null;
	ArrayAdapter<String> fileList;
	Bundle bundle;
	Button next = null;
	Button previous = null;
	String sec = null;
	String UserName = null;
	final int MSG_START_TIMER = 0;
	final int MSG_STOP_TIMER = 1;
	final int MSG_UPDATE_TIMER = 2;
	Chronometer chronometer = null;
	int item1 = 1;
	int item2 = 1;
	int item3 = 0;
	int item4 = 1;
	String name;
	String gender;
	String age;
	String language;
	String type;
	Button submit;
	boolean isrecorded=false;
	ImageView record2;
	long timeWhenStopped = 0;
	TextView status=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_2);
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"MyPref", MODE_PRIVATE);
		i = pref.getInt("key", 0);
		RECORDER_SAMPLERATE=pref.getInt("sampleRate", 16000);
		RECORDER_AUDIO_ENCODING=pref.getInt("encoding", AudioFormat.ENCODING_PCM_16BIT);
		alwaysPlay=pref.getBoolean("alwaysPlay", false);
		alwaysRecord=pref.getBoolean("alwaysRecord", true);
		i=0;
		tFileName = new String[1000];
		bundle = getIntent().getExtras();
		name = bundle.getString("name");
		age = bundle.getString("age");
		gender = bundle.getString("gender");
		language = bundle.getString("language");
		type = bundle.getString("type");
		status=(TextView)findViewById(R.id.statusText);
		record = (ImageView) findViewById(R.id.button11);
		record.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mStartPlaying) {
					if (!alwaysPlay || playCheck || mStartRecording) {
						mStartRecording = !mStartRecording;
						if (mStartRecording) {
							if (alwaysPlay)
								playCheck = false;
							recordCheck = true;
							record2.setEnabled(false);
							chronometer = (Chronometer) findViewById(R.id.chronometer1);							
							status.setText("Recording");
							bufferSize = AudioRecord.getMinBufferSize(
									RECORDER_SAMPLERATE, RECORDER_CHANNELS,
									RECORDER_AUDIO_ENCODING);
							recorder = new AudioRecord(
									MediaRecorder.AudioSource.MIC,
									RECORDER_SAMPLERATE, RECORDER_CHANNELS,
									RECORDER_AUDIO_ENCODING, bufferSize
									* BytesPerElement);
							record.setImageResource(R.drawable.pause_button);
							int vb = recorder.getState();
							if (vb == 1) {
								recorder.startRecording();
								chronometer.setBase(SystemClock
										.elapsedRealtime()
										+ timeWhenStopped);
								chronometer.start();
								recordingThread = new Thread(new Runnable() {
									@Override
									public void run() {
										writeAudioDataToFile();
									}
								}, "AudioRecorder Thread");
								recordingThread.start();
							}
						} 
						else {
							if (null != recorder) {
								recorder.stop();
								recorder.release();
								timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
								chronometer.stop();
								recorder = null;
								recordingThread = null;		
								record2.setEnabled(true);
								record.setImageResource(R.drawable.record_button);
								status.setText("Recording paused");
							}
						}
					}
				}
			}
		});
		record2 = (ImageView) findViewById(R.id.button14);
		record2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mStartRecording=false;
				copyWaveFile();
				int e=0;
				for(e=0;e<i;e++){
					File q = new File(tFileName[e]);
					q.delete();
				}
				i=0;
				timeWhenStopped=0;
				status.setText("Recording stopped");
				isrecorded=true;
			}
		});
		submit=(Button)findViewById(R.id.button15);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isrecorded){
					Intent openStartingPoint=new Intent("android.intent.action.QW");
					openStartingPoint.putExtra("name", name);
					openStartingPoint.putExtra("gender", gender);
					openStartingPoint.putExtra("age", age);
					openStartingPoint.putExtra("language", language);
					openStartingPoint.putExtra("type", type);
					startActivity(openStartingPoint);
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), "First record the file",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		play = (ImageView) findViewById(R.id.button12);
		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				pFileName =  Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ "/AksharRecorder/"+ name+ "_"+ gender+ "_"+ age+ "_"+ language+ "_"+ type+ "/"+ name+ "_"+ gender+ "_"+ age+ "_"+ language + "_" + type + ".wav";
				File filetem = new File(pFileName);
				if (filetem.exists())
					startPlayingFile();
				else {
					Toast.makeText(getApplicationContext(), "No file to play",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	// end of onCreate()

	public void startPlayingFile() {
		if (!mStartRecording) {
			if (mStartPlaying) {
				playCheck = true;
				mPlayer = new MediaPlayer();
				try {
					mStartPlaying = !mStartPlaying;
					mPlayer.setDataSource(pFileName);
					mPlayer.prepare();
					status.setText("Recorded file Playing");
					mPlayer.start();
					play.setImageResource(R.drawable.stop);
					mPlayer.setOnCompletionListener(new OnCompletionListener() {

						public void onCompletion(MediaPlayer mPlayer) {
							stopPlaying();
						}
					});
				} catch (IOException e) {
					Log.e(LOG_TAG, "prepare() failed");
				}
			} else {
				stopPlaying();
			}
			/*
			 * final CharSequence[] aKeep = {"Yes","No"}; AlertDialog.Builder
			 * alert = new AlertDialog.Builder(this);
			 * alert.setTitle("Keep the File?");
			 * alert.setSingleChoiceItems(aKeep,-1, new
			 * DialogInterface.OnClickListener() {
			 * 
			 * @Override public void onClick(DialogInterface dialog, int which)
			 * { if (aKeep[which]=="No") { File q3 = new File(pFileName);
			 * q3.delete();
			 * Toast.makeText(getApplicationContext(),"Please record the line again"
			 * , Toast.LENGTH_LONG).show(); } } }); alert.show();
			 */

		}
	}

	void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
		play.setImageResource(R.drawable.play_button);
		status.setText("Playing stopped and not recording");
		mStartPlaying = !mStartPlaying;
	}

	public void AudioRecordTest() {
		boolean success = true;
		File folder = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/AksharRecorder/"
				+ name
				+ "_"
				+ gender
				+ "_" + age + "_" + language + "_" + type);
		if (!folder.exists()) {
			success = folder.mkdirs();
			if (success) {
				mFileName = Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ "/AksharRecorder/"
						+ name
						+ "_"
						+ gender
						+ "_"
						+ age
						+ "_"
						+ language
						+ "_"
						+ type
						+ "/"
						+ name
						+ "_"
						+ gender
						+ "_"
						+ age
						+ "_"
						+ language + "_" + type + ".wav";

				File q2 = new File(mFileName);
				if (q2.exists())
					q2.delete();
			} else {
				mFileName = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				mFileName += "/" + name+"_"+gender+"_"+age+"_"+language+"_"+type + ".wav";
				File q2 = new File(mFileName);
				if (q2.exists())
					q2.delete();
			}
		} else {
			mFileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ "/AksharRecorder/"
					+ name
					+ "_"
					+ gender
					+ "_"
					+ age
					+ "_"
					+ language
					+ "_"
					+ type
					+ "/"
					+ name
					+ "_"
					+ gender
					+ "_"
					+ age
					+ "_"
					+ language + "_" + type + ".wav";
			File q2 = new File(mFileName);
			if (q2.exists())
				q2.delete();
		}
	}

	public void AudioRecordtemp() {
		File folder = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/AksharRecorder");
		boolean success = true;

		if (!folder.exists()) {
			success = folder.mkdir();
			if (success) {
				tFileName[i] = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				tFileName[i] += "/AksharRecorder/audiorecordtest"+i+".raw";
			} 
			else {
				tFileName[i] = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				tFileName[i] += "/audiorecordtest"+i+".raw";
			}
		} 
		else {
			tFileName[i] = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			tFileName[i] += "/AksharRecorder/audiorecordtest"+i+".raw";
		}
		i+=1;
	}

	private void writeAudioDataToFile() {
		// Write the output audio in byte
		AudioRecordtemp();
		AudioRecordTest();
		short data[] = new short[bufferSize];

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(tFileName[i-1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (null != os) {
			while (mStartRecording) {
				// gets the voice output from microphone to byte format
				ab = recorder.read(data, 0, bufferSize);
				if (AudioRecord.ERROR_INVALID_OPERATION != ab) {
					try {
						byte bData[] = short2byte(data);
						os.write(bData, 0, bufferSize * BytesPerElement);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] short2byte(short[] sData) {
		int shortArrsize = sData.length;
		byte[] bytes = new byte[shortArrsize * 2];
		for (int i = 0; i < shortArrsize; i++) {
			bytes[i * 2] = (byte) (sData[i] & 0x00FF);
			bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
			sData[i] = 0;
		}
		return bytes;

	}

	private void copyWaveFile() {
		FileInputStream in[] = null;
		FileOutputStream out = null;
		long totalAudioLen = 0;
		long totalDataLen = totalAudioLen + 36;
		long longSampleRate = RECORDER_SAMPLERATE;
		int channels = 1; // use 2 if doesn't work
		long byteRate = (RECORDER_BPP * RECORDER_SAMPLERATE * channels) / 8;

		byte[] data = new byte[bufferSize];
		in = new FileInputStream[1000];

		try {
			int e=0;
			for(e=0;e<i;e++){
				in[e] = new FileInputStream(tFileName[e]);
			}
			out = new FileOutputStream(mFileName);
			for(e=0;e<i;e++){
				totalAudioLen += in[e].getChannel().size();
			}
			totalDataLen = totalAudioLen + 36;

			// AppLog.logString("File size: " + totalDataLen);

			WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
					longSampleRate, channels, byteRate);

			for(e=0;e<i;e++){
				while (in[e].read(data) != -1) {
					out.write(data);
				}
				in[e].close();
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,
			long totalDataLen, long longSampleRate, int channels, long byteRate)
					throws IOException {

		byte[] header = new byte[44];

		header[0] = 'R'; // RIFF/WAVE header
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f'; // 'fmt ' chunk
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16; // 4 bytes: size of 'fmt ' chunk
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1; // format = 1
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (2 * 16 / 8); // block align
		header[33] = 0;
		header[34] = RECORDER_BPP; // bits per sample
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

		out.write(header, 0, 44);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		return;
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
			final CharSequence rates[] = new CharSequence[] { "8000", "16000",
					"22050", "32000" };
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Pick SampleRate ");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			builder.setSingleChoiceItems(rates, item1,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					item1 = which;
					if (rates[which] == "8000") {
						RECORDER_SAMPLERATE = 8000;
					} else if (rates[which] == "16000") {
						RECORDER_SAMPLERATE = 16000;
					} else if (rates[which] == "22050") {
						RECORDER_SAMPLERATE = 22050;
					} else if (rates[which] == "32000") {
						RECORDER_SAMPLERATE = 32000;
					}
				}
			});
			builder.show();
			return true;
		}
		if (id == R.id.action_alwaysplay) {
			final CharSequence[] aPlay = { "Yes", "No" };
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Always Play file Before recording next file");
			alert.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			alert.setSingleChoiceItems(aPlay, item2,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					item2 = which;
					if (aPlay[which] == "Yes") {
						alwaysPlay = true;
					} else if (aPlay[which] == "No") {
						alwaysPlay = false;
					}
				}
			});
			alert.show();
			return true;
		}
		if (id == R.id.action_alwaysrecord) {
			final CharSequence[] aRecord = { "Yes", "No" };
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Record each line before moving to the next");
			alert.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			alert.setSingleChoiceItems(aRecord, item3,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					item3 = which;
					if (aRecord[which] == "Yes") {
						alwaysRecord = true;
					} else if (aRecord[which] == "No") {
						alwaysRecord = false;
					}
				}
			});
			alert.show();
			return true;
		}
		if (id == R.id.action_audioencoding) {
			final CharSequence[] aRecord = { "PCM_8BIT", "PCM_16BIT" };
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Select the audio encoding system ");
			alert.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			});
			alert.setSingleChoiceItems(aRecord, item4,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					item4 = which;
					if (aRecord[which] == "PCM_8BIT") {
						RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_8BIT;
					} else if (aRecord[which] == "PCM_16BIT") {
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
	protected void onStop() {
		/*if(mStartPlaying){
			mStartPlaying = false;
			stopPlaying();
		}
		if (mStartRecording) {
			mStartRecording=false;
			recorder.stop();
			recorder.release();
			timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
			chronometer.stop();
			recorder = null;
			recordingThread = null;		
			record2.setEnabled(true);
			record.setImageResource(R.drawable.record_button);
		}*/
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"MyPref", MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("key", i);
		editor.putInt("sampleRate", RECORDER_SAMPLERATE);
		editor.putInt("encoding", RECORDER_AUDIO_ENCODING);
		editor.putBoolean("alwaysPlay", alwaysPlay);
		editor.putBoolean("alwaysRecord", alwaysRecord);
		editor.commit();
		super.onStop();
	}

	@Override
	public void onPause() {
		/*if(mStartPlaying){
			mStartPlaying = false;
			stopPlaying();
		}
		if (mStartRecording) {
			mStartRecording=false;
			recorder.stop();
			recorder.release();
			timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
			chronometer.stop();
			recorder = null;
			recordingThread = null;		
			record2.setEnabled(true);
			record.setImageResource(R.drawable.record_button);
		}*/
		super.onPause();
	}
}
