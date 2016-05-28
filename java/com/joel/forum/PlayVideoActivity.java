package com.joel.forum;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayVideoActivity extends Activity implements
        MediaPlayer.OnCompletionListener, OnClickListener,
        OnSeekBarChangeListener {

    private int videoId;
    private String prefix;

    private SeekBar timeSeekbar;
    private static MediaPlayer mp; // i fixed some bugs here and found that static is not needed, but i'm not sure in 100%
    private File videoFile;
    private String videoFilePath;
    private SurfaceView mVideoView;
    private SurfaceHolder holder;
    private static RelativeLayout rlControl, playPauseArea;

    private static ImageButton imgBtnVideoPlay;
    private static ImageButton imgBtnVideoPause;
    private boolean isExist;
    private boolean guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_playvideo);

        mVideoView = (SurfaceView) findViewById(R.id.videoView);
        imgBtnVideoPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        imgBtnVideoPause = (ImageButton) findViewById(R.id.imgBtnVideoPause);

        holder = mVideoView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setKeepScreenOn(true);
        mVideoView.setClickable(true);
        mVideoView.setOnClickListener(new SurfaceViewOnClickListener());
        holder.addCallback(new SurfaceViewCallBack());

        videoId = getIntent().getIntExtra("videono", 1);
        prefix = getIntent().getStringExtra("prefix");

        Log.e("", "prefix=====" + prefix);

        guide = getIntent().getBooleanExtra("guide", false);

        rlControl = (RelativeLayout) findViewById(R.id.control_relative);
        rlControl.setVisibility(View.INVISIBLE);
        imgBtnVideoPlay.setVisibility(View.INVISIBLE);
        imgBtnVideoPause.setVisibility(View.INVISIBLE);
        InitWidgetsAndVariables();
    }

    private void InitWidgetsAndVariables() {
        ImageButton btnDone = (ImageButton) findViewById(R.id.imgVideoDone);
        btnDone.setOnClickListener(this);
        imgBtnVideoPause.setOnClickListener(this);
        imgBtnVideoPlay.setOnClickListener(this);

        timeSeekbar = (SeekBar) findViewById(R.id.seekVideoTime);
        timeSeekbar.setOnSeekBarChangeListener(this);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audiomanager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audiomanager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                break;
        }
        super.onKeyDown(keyCode, event);
        return true;
    }

    private void videoPlay() {
        isExist = checkVideoFilePresent();
        if (isExist) {
            try {
                if (mp == null) {
                    mp = new MediaPlayer();
                    mp.setDataSource(videoFilePath);
                    mp.setDisplay(holder);
                    mp.prepare();
                    mp.setScreenOnWhilePlaying(true);
                    mp.seekTo(0);
                    mp.start();
                    mp.setOnCompletionListener(this);
                    // btnPlay.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause));

                    int duration = mp.getDuration() / 1000;
                    int min = duration / 60;
                    int sec = duration - min * 60;

                    TextView tvEndTime = (TextView) findViewById(R.id.txtEndVideoTime);
                    tvEndTime.setText(String.format("%02d:%02d", min, sec));

                    timeSeekbar.setMax(mp.getDuration());
                    timeSeekbar.post(mRunnable);
                }
            } catch (IllegalArgumentException e) {
            } catch (SecurityException e) {
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Data Error!!")
                    .setMessage(
                            "Video data not present. Please copy video data to phone.")
                    .setNeutralButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    finish();
                                }
                            }).show();
        }
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {

            if (mp != null) {
                try {
                    int mCurrentPosition = mp.getCurrentPosition();
                    timeSeekbar.setProgress(mCurrentPosition);
                    showDuration(mp.getCurrentPosition() / 1000);
                } catch (Exception e) {
                }
            }
            timeSeekbar.postDelayed(this, 1000);
        }
    };

    private void showDuration(int duration) {
        int sec, min;
        String time;
        if (duration < 60)
            time = "00:" + String.format("%02d", duration);
        else {
            min = duration / 60;
            sec = duration - min * 60;
            time = String.format("%02d:%02d", min, sec);
        }

        TextView tvStartTime = (TextView) findViewById(R.id.txtStartVideoTime);
        tvStartTime.setText(time);
    }

    private boolean checkVideoFilePresent() {
        File rootDirectory = Environment.getExternalStorageDirectory();
        if (!guide)
            videoFilePath = rootDirectory.getAbsolutePath() + "/"
                    + getPackageName() + "/video/" + prefix + "/"
                    + prefix.toUpperCase() + " - " + String.valueOf(videoId)
                    + ".mp4";

			/*videoFilePath = rootDirectory.getAbsolutePath() + "/Download/"
					+ prefix.toUpperCase() + " - " + String.valueOf(videoId)
					+ ".mp4";*/

        else
            videoFilePath = rootDirectory.getAbsolutePath() + "/"
                    + getPackageName() + "/video/" + prefix + "/" + "Guide_"
                    + prefix.toUpperCase() + ".mp4";
			
			/*videoFilePath = rootDirectory.getAbsolutePath() + "/Download/"
					+ prefix.toUpperCase() + " - " + String.valueOf(videoId)
					+ ".mp4";*/

        videoFile = new File(videoFilePath);
        if (videoFile.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private final class SurfaceViewCallBack implements Callback {

        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                                   int arg3) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            videoPlay();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            if (mp != null) {
                mp.release();
                mp = null;
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        G.isExitVideo = true;
        this.mp.stop();
        this.mp.release();
        this.mp = null;
        finish();
    }

    private static class SurfaceViewOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (mp != null) {
                if (mp.isPlaying()) {
                    imgBtnVideoPlay.setVisibility(View.GONE);
                    imgBtnVideoPause.setVisibility(View.VISIBLE);
                    rlControl.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rlControl.setVisibility(View.INVISIBLE);
                            imgBtnVideoPause.setVisibility(View.GONE);
                        }
                    }, 3000);
                } else {
                    imgBtnVideoPlay.setVisibility(View.GONE);
                    imgBtnVideoPause.setVisibility(View.VISIBLE);
                    rlControl.setVisibility(View.VISIBLE);
                    imgBtnVideoPlay.setVisibility(View.VISIBLE);
                    imgBtnVideoPause.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rlControl.setVisibility(View.INVISIBLE);
                            imgBtnVideoPause.setVisibility(View.GONE);
                            imgBtnVideoPlay.setVisibility(View.GONE);
                            imgBtnVideoPause.setVisibility(View.GONE);
                        }
                    }, 3000);

                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgVideoDone:
                if (mp != null) {
                    mp.stop();
                    mp.release();
                    mp = null;
                }
                G.isExitVideo = true;
                finish();
                break;
            case R.id.imgBtnPlay:
                if (mp != null) {
                    if (!mp.isPlaying()) {
                        mp.start();
                        imgBtnVideoPause.setVisibility(View.VISIBLE);
                        imgBtnVideoPlay.setVisibility(View.GONE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imgBtnVideoPause.setVisibility(View.GONE);
                                imgBtnVideoPlay.setVisibility(View.GONE);
                            }
                        }, 3000);

                    }
                }

                break;

            case R.id.imgBtnVideoPause:
                if (mp != null) {
                    if (mp.isPlaying()) {
                        mp.pause();
                        imgBtnVideoPause.setVisibility(View.GONE);
                        imgBtnVideoPlay.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imgBtnVideoPause.setVisibility(View.GONE);
                                imgBtnVideoPlay.setVisibility(View.GONE);
                            }
                        }, 3000);

                    }
                }

                break;

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekbar, int progress,
                                  boolean fromUser) {
        if (mp != null && fromUser) {
            mp.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (mp != null) {
            if (mp.isPlaying())
                mp.stop();
        }
        finish();
        Intent i = new Intent(this, ControlPageActivity.class);
        //	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mp != null && !mp.isPlaying()) {
            mp.start();
            imgBtnVideoPause.setVisibility(View.VISIBLE);
            imgBtnVideoPlay.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgBtnVideoPause.setVisibility(View.GONE);
                    imgBtnVideoPlay.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mp != null && mp.isPlaying()) {
            mp.pause();
            imgBtnVideoPause.setVisibility(View.GONE);
            imgBtnVideoPlay.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imgBtnVideoPause.setVisibility(View.GONE);
                    imgBtnVideoPlay.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }

}
