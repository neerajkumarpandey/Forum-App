package com.joel.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainPageActivity extends Activity implements OnClickListener{

	boolean started = false;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable(){
		public void run() {
			//Toast.makeText(MainPageActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.acitivity_main);
		
		ImageButton plyButton = (ImageButton) findViewById(R.id.imgPlayBtn);
		plyButton.setOnClickListener(this);
		handler.postDelayed(runnable,1500000);
	}

	@Override
	public void onClick(View v) {
		handler.removeCallbacks(runnable);
		switch(v.getId()) {
		case R.id.imgPlayBtn:
			launchControlPage();
			break;
		}
	}
	
	private void launchControlPage() {
		started = true;
		Intent intent = new Intent(this, ControlPageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		
	//	Toast.makeText(getApplicationContext(), "Back button pressed", Toast.LENGTH_SHORT).show();
	}
	
	/*@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		if(!started) {
			finish();
			Intent i = new Intent(this, MainPageActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		}
	}*/
}
