package com.joel.forum;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class PlayAudioActivity extends Activity implements OnClickListener, OnCompletionListener, OnSeekBarChangeListener{
	
	private int audioId;
	private String prefix;
	
	private RelativeLayout rlPlayAudio;
	private ImageButton btnPlay;
	private SeekBar volSeekbar, timeSeekbar;
	private MediaPlayer mp;
	private File audioFile;
	private String audioFilePath;
	private AudioManager am;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.activity_playaudio);
		
		rlPlayAudio = (RelativeLayout) findViewById(R.id.playaudio_relative);
		
		audioId = getIntent().getIntExtra("audiono", 1);
		prefix = getIntent().getStringExtra("prefix");
		
		am = (AudioManager) getSystemService(AUDIO_SERVICE);
		
		InitBackground();
		InitWidgetsAndVariables();
		audioPlay();
	}
	
	private void InitBackground() {
		int resId = getResources().getIdentifier(prefix + String.valueOf(audioId), "drawable", getPackageName());
		rlPlayAudio.setBackgroundDrawable(getResources().getDrawable(resId));
	}
	
	private void InitWidgetsAndVariables() {
		ImageButton btnDone = (ImageButton) findViewById(R.id.imgBtnDone);
		btnDone.setOnClickListener(this);
		
		btnPlay = (ImageButton) findViewById(R.id.imgBtnAudioPlay);
		btnPlay.setOnClickListener(this);
		
		int volMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		volSeekbar = (SeekBar) findViewById(R.id.seekVolume);
		volSeekbar.setOnSeekBarChangeListener(this);
		volSeekbar.setMax(volMax);
		volSeekbar.setProgress(curVol);
		
		timeSeekbar = (SeekBar) findViewById(R.id.seekTime);
		timeSeekbar.setOnSeekBarChangeListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.imgBtnDone:
				G.isExitVideo = false;
				if(mp != null) {
					mp.stop();
					mp.release();
					mp = null;
				}
				finish();
				break;
			case R.id.imgBtnAudioPlay:
				audioPlay();
				break;
		}
	} 
	
	private void audioPlay() {
		boolean isExist;
		isExist = checkAudioFilePresent();
		if(isExist) {
			try {
				if(mp == null) {
					mp = new MediaPlayer();
					mp.setDataSource(audioFilePath);
					mp.prepare();
					mp.seekTo(0);
					mp.start();
					mp.setOnCompletionListener(this);
					btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause));

					int duration = mp.getDuration() / 1000;
					int min = duration / 60;
					int sec = duration - min * 60;

					TextView tvEndTime = (TextView) findViewById(R.id.txtEndTime);
					tvEndTime.setText(String.format("%02d:%02d", min, sec));

					timeSeekbar.setMax(mp.getDuration());
					timeSeekbar.post(mRunnable);

				} else {
					if(mp.isPlaying()) {
						mp.pause();
						btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.play));
					} else {
						mp.start();
						btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause));
					}
						
				}
			} catch (IllegalArgumentException e) {
			} catch (SecurityException e) {
			} catch (IllegalStateException e) {
			} catch (IOException e) {
			}
		} else {
			new AlertDialog.Builder(this)
            .setTitle("Data Error!!")
            .setMessage("Audio data not present. Please copy audio data to phone.")
            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            }).show();
		}
	}
	
	private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if(mp != null){
            	try {
            		int mCurrentPosition = mp.getCurrentPosition();
            		timeSeekbar.setProgress(mCurrentPosition);
            		showDuration(mp.getCurrentPosition() / 1000);
            	}
                catch(Exception e) {
					e.printStackTrace();
                }
            }
            timeSeekbar.postDelayed(this, 1000);
        }
    };
    
	private void showDuration(int duration) {
		int sec, min;
		String time;
		if(duration < 60)
			time = "00:" + String.format("%02d", duration);
        else{
       	 min = duration / 60;
       	 sec = duration - min * 60;
       	 time = String.format("%02d:%02d", min, sec);
        }
		
		TextView tvStartTime = (TextView) findViewById(R.id.txtStartTime);
		tvStartTime.setText(time);
	}
	
	private boolean checkAudioFilePresent() {
		File rootDirectory = Environment.getExternalStorageDirectory();
		audioFilePath = rootDirectory.getAbsolutePath() +"/"+ getPackageName() + "/audio/" + prefix + "/" + prefix.toUpperCase() + " - " + String.valueOf(audioId) + ".mp3";
		audioFile = new File(audioFilePath);
		if(audioFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		G.isExitVideo = false;
		this.mp.stop();
		this.mp.release();
		this.mp = null;
		finish();
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
	public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
		switch(seekbar.getId()) {
			case R.id.seekTime:
				if(mp != null && fromUser) {
					mp.seekTo(progress);
				}
				break;
			case R.id.seekVolume:
				am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				break;
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		//finish();
	}
	
	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		if(mp.isPlaying())
			mp.stop();
		finish();
		Intent i = new Intent(this, MainPageActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mp != null  && !mp.isPlaying()) {
			audioPlay();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mp != null && mp.isPlaying()) {
			audioPlay();
		}
	}
}
