package com.joel.forum;

import com.joel.forum.ImageMap;
import com.joel.forum.TouchImageView.OnTouchImageViewListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ControlPageActivity extends Activity implements OnClickListener,
		OnTouchListener {
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable(){
		public void run() {
			//Toast.makeText(ControlPageActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	};
	private ImageButton btnLanuage, btnAudio, btnVideo, btnMap;
	private static int language = -1;
	private String prefix = "";
	private static int audioNo = 0;
	private static int videoNo = 0;
	private int mapId = 1;
	private int VideoId = 1;
	private int MapID = 2;
	public static boolean mapSelect = false;

	private static Point map1points[];
	private static Point map2points[];

	// private TextView lblMap1, lblMap2;
	private AudioManager am;

	private Button btnExit;
	boolean started = false;
	private ImageButton v1;
	private ImageButton v2;
	private ImageButton v3;
	private ImageButton v4;
	private ImageButton v5;
	private ImageButton v6;
	private ImageButton v7;

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_controlpage);
		am = (AudioManager) getSystemService(AUDIO_SERVICE);

		InitMapXY();
		InitLangWidgets();
		InitLanguage();
		handler.postDelayed(runnable,2400000);
	}

	private void InitMapXY() {
		map1points = new Point[3];
		map1points[0] = new Point(290, 230); // No1
		map1points[1] = new Point(355, 135); // No6
		map1points[2] = new Point(310, 80); // No7
	
		map2points = new Point[4];
		map2points[0] = new Point(198, 133); // No2
		map2points[1] = new Point(276, 57); // No3
		map2points[2] = new Point(385, 135); // No4
		map2points[3] = new Point(425, 145); // No5

	}

	@Override
	public void onClick(View v) {
		System.gc();
		//handler.removeCallbacks(runnable);
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, 1500000);
		//android.os.Process.killProcess(android.os.Process.myPid());
		switch (v.getId()) {
		// Language Page
		//
		case R.id.imgBtnAudio:
			onAudio();
			break;
		case R.id.imgBtnVideo:
			MapID = 2;
			onVideo();
			break;
		case R.id.imgBtnMap:
			MapID = 1;
			G.isExitVideo = false;
			onMap();
			break;
		// Audio Page
		case R.id.imgBtnMap_audio:
			MapID = 1;
			G.isExitVideo = false;
			onAudioMap();
			break;
		case R.id.imgBtnLanuage_audio:
			VideoId = 1;
			onAudioLang();
			break;
		case R.id.imgBtnVideo_audio:
			MapID = 2;
			G.isExitVideo = false;
			onAudioVideo();
			break;
		// Video Page
		case R.id.imgBtnMap_video:
			MapID = 1;
			G.isExitVideo = false;
			onVideoMap();
			break;
		case R.id.imgBtnLanuage_video:
			VideoId = 1;
			onVideoLang();
			break;
		case R.id.imgBtnAudio_video:
			onVideoAudio();
			break;
		// Map Page
		case R.id.imgBtnVideo_map:
			MapID = 2;
			onMapVideo();
			break;
		case R.id.imgBtnLanuage_map:
			VideoId = 1;
			onMapLang();
			break;
		case R.id.imgBtnAudio_map:
			onMapAudio();
			break;
		// Language Button
		case R.id.imgBtnUK:
			MapID = 2;
			VideoId = 1;
			language = 1;
			prefix = "en";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnFrance:
			MapID = 2;
			VideoId = 1;
			language = 2;
			prefix = "fr";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnSpain:
			MapID = 2;
			VideoId = 1;
			language = 3;
			prefix = "es";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnItaly:
			MapID = 2;
			VideoId = 1;
			language = 4;
			prefix = "it";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnGermany:
			MapID = 2;
			VideoId = 1;
			language = 5;
			prefix = "de";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnRussia:
			MapID = 2;
			VideoId = 1;
			language = 6;
			prefix = "ru";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnChina:
			language = 7;
			prefix = "cn";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnPort:
			MapID = 2;
			VideoId = 1;
			language = 8;
			prefix = "pt";
			InitLanguage();
			startGuide();
			break;
		case R.id.imgBtnJapan:
			language = 9;
			prefix = "jp";
			InitLanguage();
			startGuide();
			break;
		// Audio Clips
		case R.id.txtAudio1:
			audioNo = 1;
			PlayAudio();
			break;
		case R.id.txtAudio2:
			audioNo = 2;
			PlayAudio();
			break;
//		case R.id.txtAudio3:
//			audioNo = 3;
//			PlayAudio();
//			break;
//		case R.id.txtAudio4:
//			audioNo = 4;
//			PlayAudio();
//			break;
//		case R.id.txtAudio5:
//			audioNo = 5;
//			PlayAudio();
//			break;
		// Video Clips
		case R.id.txtVideo1:

			if (VideoId == 1) {
				videoNo = 1;
			} else {
				videoNo = 11;
			}
			PlayVideo();
			break;
		case R.id.txtVideo2:

			if (VideoId == 1) {
				videoNo = 6;
			} else {
				videoNo = 10;
			}
			PlayVideo();
			break;
		case R.id.txtVideo3:

			if (VideoId == 1) {
				videoNo = 5;
			} else {
				videoNo = 12;
			}
			PlayVideo();
			break;
		case R.id.txtVideo4:
			if (VideoId == 1) {
				videoNo = 4;
			} else {
				videoNo = 13;
			}
			PlayVideo();
			break;
		case R.id.txtVideo5:
			if (VideoId == 1) {
				videoNo = 9;
			} else {
				videoNo = 3;
			}
			PlayVideo();
			break;
		case R.id.txtVideo6:
			if (VideoId == 1) {
				videoNo = 8;
			} else {
				videoNo = 14;
			}
			PlayVideo();
			break;
		case R.id.txtVideo7:
			if (VideoId == 1) {
				videoNo = 7;
			} else {
				videoNo = 2;
			}
			PlayVideo();
			break;

		case R.id.txtVideo1To7:
			VideoId = 1;
			InitVideo1To7();
			break;
		case R.id.txtVideo7to14:
			VideoId = 2;
			InitVideo7To14();
			break;

		// Map

		/*
		 * case R.id.txtViewMap1: mapId = 1; lblMap1.setTextColor(Color.WHITE);
		 * lblMap2.setTextColor(Color.BLACK); changeMap(); break; case
		 * R.id.txtViewMap2: mapId = 2; lblMap1.setTextColor(Color.BLACK);
		 * lblMap2.setTextColor(Color.WHITE); changeMap(); break;
		 */
		case R.id.btnExit:
			finish();
			break;
		}

	}

	/*
	 * Init Widgets
	 */
	private void InitLangWidgets() {
		btnLanuage = (ImageButton) findViewById(R.id.imgBtnLanuage);
		btnLanuage.setOnClickListener(this);

		btnAudio = (ImageButton) findViewById(R.id.imgBtnAudio);
		btnAudio.setOnClickListener(this);

		btnVideo = (ImageButton) findViewById(R.id.imgBtnVideo);
		btnVideo.setOnClickListener(this);

		btnMap = (ImageButton) findViewById(R.id.imgBtnMap);
		btnMap.setOnClickListener(this);

		btnExit = (Button) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(this);

		ImageButton ukButton = (ImageButton) findViewById(R.id.imgBtnUK);
		ukButton.setOnClickListener(this);

		ImageButton fraButton = (ImageButton) findViewById(R.id.imgBtnFrance);
		fraButton.setOnClickListener(this);

		ImageButton spaButton = (ImageButton) findViewById(R.id.imgBtnSpain);
		spaButton.setOnClickListener(this);

		ImageButton itaButton = (ImageButton) findViewById(R.id.imgBtnItaly);
		itaButton.setOnClickListener(this);

		ImageButton detButton = (ImageButton) findViewById(R.id.imgBtnGermany);
		detButton.setOnClickListener(this);

		ImageButton rusButton = (ImageButton) findViewById(R.id.imgBtnRussia);
		rusButton.setOnClickListener(this);

		ImageButton chiButton = (ImageButton) findViewById(R.id.imgBtnChina);
		chiButton.setOnClickListener(this);

		ImageButton potButton = (ImageButton) findViewById(R.id.imgBtnPort);
		potButton.setOnClickListener(this);

		ImageButton japButton = (ImageButton) findViewById(R.id.imgBtnJapan);
		japButton.setOnClickListener(this);

	}

	private void InitAudioWidgets() {
		ImageButton langA = (ImageButton) findViewById(R.id.imgBtnLanuage_audio);
		langA.setOnClickListener(this);

		ImageButton videoA = (ImageButton) findViewById(R.id.imgBtnVideo_audio);
		videoA.setOnClickListener(this);

		ImageButton mapA = (ImageButton) findViewById(R.id.imgBtnMap_audio);
		mapA.setOnClickListener(this);

		TextView audio1 = (TextView) findViewById(R.id.txtAudio1);
//		audio1.setOnTouchListener(this);
		audio1.setOnClickListener(this);

		TextView audio2 = (TextView) findViewById(R.id.txtAudio2);
//		audio2.setOnTouchListener(this);
		audio2.setOnClickListener(this);

//		TextView audio3 = (TextView) findViewById(R.id.txtAudio3);
//		audio3.setOnTouchListener(this);
//		audio3.setOnClickListener(this);
//
//		TextView audio4 = (TextView) findViewById(R.id.txtAudio4);
//		audio4.setOnTouchListener(this);
//		audio4.setOnClickListener(this);
//
//		TextView audio5 = (TextView) findViewById(R.id.txtAudio5);
//		audio5.setOnTouchListener(this);
//		audio5.setOnClickListener(this);
	}

	private void InitVideoWidgets() {
		ImageButton langV = (ImageButton) findViewById(R.id.imgBtnLanuage_video);
		langV.setOnClickListener(this);

		ImageButton audioV = (ImageButton) findViewById(R.id.imgBtnAudio_video);
		audioV.setOnClickListener(this);

		ImageButton mapV = (ImageButton) findViewById(R.id.imgBtnMap_video);
		mapV.setOnClickListener(this);

		TextView video1 = (TextView) findViewById(R.id.txtVideo1);
		video1.setOnTouchListener(this);
		video1.setOnClickListener(this);

		TextView video2 = (TextView) findViewById(R.id.txtVideo2);
		video2.setOnTouchListener(this);
		video2.setOnClickListener(this);

		TextView video3 = (TextView) findViewById(R.id.txtVideo3);
		video3.setOnTouchListener(this);
		video3.setOnClickListener(this);

		TextView video4 = (TextView) findViewById(R.id.txtVideo4);
		video4.setOnTouchListener(this);
		video4.setOnClickListener(this);

		TextView video5 = (TextView) findViewById(R.id.txtVideo5);
		video5.setOnTouchListener(this);
		video5.setOnClickListener(this);

		TextView video6 = (TextView) findViewById(R.id.txtVideo6);
		video6.setOnTouchListener(this);
		video6.setOnClickListener(this);

		TextView video7 = (TextView) findViewById(R.id.txtVideo7);
		video7.setOnTouchListener(this);
		video7.setOnClickListener(this);
		InitLanguage();
	}

	// TouchImageView imageEn, imageFr, imageIt, imageEs, imageDe, imageRu,
	// imagePt;
	ImageMap mImageMapEn, mImageMapFr, mImageMapIt, mImageMapEs, mImageMapDe,
			mImageMapRu, mImageMapPt;

	private void InitMapWidgets() {
		mapId = 1;
		changeMap();
		// image = (TouchImageView) findViewById(R.id.img);
		ImageButton langM = (ImageButton) findViewById(R.id.imgBtnLanuage_map);
		langM.setOnClickListener(this);

		ImageButton audioM = (ImageButton) findViewById(R.id.imgBtnAudio_map);
		audioM.setOnClickListener(this);

		ImageButton videoM = (ImageButton) findViewById(R.id.imgBtnVideo_map);
		videoM.setOnClickListener(this);

	}

	/*
	 * Action of Map Layout
	 */

	private void onMapLang() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.activity_controlpage, null);
		setContentView(view);
		InitLangWidgets();
		InitLanguage();
	}

	private void onMapAudio() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.audio_layout, null);
		setContentView(view);
		InitAudioWidgets();
		InitAudioLanguage();
	}

	private void onMapVideo() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.video_layout, null);
		setContentView(view);
		InitVideoWidgets();
		InitVideoLanguage();
	}

	/*
	 * Action of Video Layout
	 */
	private void onVideoLang() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.activity_controlpage, null);
		setContentView(view);
		InitLangWidgets();
		InitLanguage();
	}

	private void onVideoAudio() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.audio_layout, null);
		setContentView(view);
		InitAudioWidgets();
		InitAudioLanguage();
	}

	private void onVideoMap() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.map_layout, null);
		setContentView(view);
		InitMapXY();
		InitMapWidgets();
		InitMapLanguage();
	}

	/*
	 * Action of Audio Layout
	 */
	private void onAudioLang() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.activity_controlpage, null);
		setContentView(view);
		InitLangWidgets();
		InitLanguage();
	}

	private void onAudioVideo() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.video_layout, null);
		setContentView(view);
		InitVideoWidgets();
		InitVideoLanguage();
	}

	private void onAudioMap() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.map_layout, null);
		setContentView(view);
		InitMapXY();
		InitMapWidgets();
		InitMapLanguage();
	}

	/*
	 * Action of Language Layout
	 */
	private void onAudio() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.audio_layout, null);
		setContentView(view);
		InitAudioWidgets();
		InitAudioLanguage();
	}

	private void onVideo() {
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.video_layout, null);
		setContentView(view);

		InitVideoWidgets();

		if (VideoId == 1) {
			InitVideo1To7();
		} else if (VideoId == 2) {
			InitVideo7To14();
		}
		// InitVideoLanguage();
	}

	private void onMap() {
		mapSelect = true;
		LayoutInflater inflate = getLayoutInflater();
		view = inflate.inflate(R.layout.map_layout, null);
		setContentView(view);
		InitMapWidgets();
		 InitMapLanguage();
	}

	/*
	 * Initialize Diaplay Languages
	 */
	private void InitLanguage() {
		TextView lblLang = (TextView) findViewById(R.id.textView1);
		TextView lblAudio = (TextView) findViewById(R.id.textView2);
		TextView lblVideo = (TextView) findViewById(R.id.textView3);
		TextView lblMap = (TextView) findViewById(R.id.textView4);

		if (language == -1)
			language = 1;
		if (prefix == "")
			prefix = "en";

		int idLang = getResources().getIdentifier(prefix + "_language",
				"string", getPackageName());
		int idAudio = getResources().getIdentifier(prefix + "_audio", "string",
				getPackageName());
		int idVideo = getResources().getIdentifier(prefix + "_video", "string",
				getPackageName());
		int idMap = getResources().getIdentifier(prefix + "_map", "string",
				getPackageName());

		lblLang.setText(idLang);
		lblAudio.setText(idAudio);
		lblVideo.setText(idVideo);
		lblMap.setText(idMap);
	}

	private void InitAudioLanguage() {
		InitLanguage();

		TextView lblAudio1 = (TextView) findViewById(R.id.txtAudio1);
		TextView lblAudio2 = (TextView) findViewById(R.id.txtAudio2);
//		TextView lblAudio3 = (TextView) findViewById(R.id.txtAudio3);
//		TextView lblAudio4 = (TextView) findViewById(R.id.txtAudio4);
//		TextView lblAudio5 = (TextView) findViewById(R.id.txtAudio5);
		TextView lblAudioTitle = (TextView) findViewById(R.id.txtAudioTitle);

		if (language == -1)
			language = 1;
		if (prefix == "")
			prefix = "en";

		int idAudio1 = getResources().getIdentifier(prefix + "_audio1",
				"string", getPackageName());
		int idAudio2 = getResources().getIdentifier(prefix + "_audio2",
				"string", getPackageName());
		int idAudio3 = getResources().getIdentifier(prefix + "_audio3",
				"string", getPackageName());
		int idAudio4 = getResources().getIdentifier(prefix + "_audio4",
				"string", getPackageName());
		int idAudio5 = getResources().getIdentifier(prefix + "_audio5",
				"string", getPackageName());
		int idAudioTitle = getResources().getIdentifier(prefix + "_audioTitle",
				"string", getPackageName());

		lblAudio1.setText(idAudio1);
		lblAudio2.setText(idAudio2);
		// lblAudio3.setText(idAudio3);
		// /lblAudio4.setText(idAudio4);
		// lblAudio5.setText(idAudio5);

		lblAudioTitle.setText(idAudioTitle);
	}

	private void InitVideoLanguage() {
		// InitLanguage();

		TextView lblVideo1 = (TextView) findViewById(R.id.txtVideo1);
		TextView lblVideo2 = (TextView) findViewById(R.id.txtVideo2);
		TextView lblVideo3 = (TextView) findViewById(R.id.txtVideo3);
		TextView lblVideo4 = (TextView) findViewById(R.id.txtVideo4);
		TextView lblVideo5 = (TextView) findViewById(R.id.txtVideo5);
		TextView lblVideo6 = (TextView) findViewById(R.id.txtVideo6);
		TextView lblVideo7 = (TextView) findViewById(R.id.txtVideo7);
		TextView lblVideoTitle = (TextView) findViewById(R.id.txtVideoTitle);
		// TextView lblVideo = (TextView) findViewById(R.id.txtVideo7);
		TextView lblVideo1to7 = (TextView) findViewById(R.id.txtVideo1To7);
		TextView lblVideo7to14 = (TextView) findViewById(R.id.txtVideo7to14);
		if (language == -1)
			language = 1;
		if (prefix == "")
			prefix = "en";

		int idVideo1 = getResources().getIdentifier(prefix + "_video1",
				"string", getPackageName());
		int idVideo2 = getResources().getIdentifier(prefix + "_video2",
				"string", getPackageName());
		int idVideo3 = getResources().getIdentifier(prefix + "_video3",
				"string", getPackageName());
		int idVideo4 = getResources().getIdentifier(prefix + "_video4",
				"string", getPackageName());
		int idVideo5 = getResources().getIdentifier(prefix + "_video5",
				"string", getPackageName());
		int idVideo6 = getResources().getIdentifier(prefix + "_video6",
				"string", getPackageName());
		int idVideo7 = getResources().getIdentifier(prefix + "_video7",
				"string", getPackageName());
		int idVideoTitle = getResources().getIdentifier(prefix + "_videoTitle",
				"string", getPackageName());

		lblVideo1.setText(idVideo1);
		lblVideo2.setText(idVideo2);
		lblVideo3.setText(idVideo3);
		lblVideo4.setText(idVideo4);
		lblVideo5.setText(idVideo5);
		lblVideo6.setText(idVideo6);
		lblVideo7.setText(idVideo7);
		lblVideoTitle.setText(idVideoTitle);
		lblVideo1to7.setOnClickListener(this);
		lblVideo7to14.setOnClickListener(this);
		v1 = (ImageButton) findViewById(R.id.imgBtnVideo1);
		v2 = (ImageButton) findViewById(R.id.imgBtnVideo2);
		v3 = (ImageButton) findViewById(R.id.imgBtnVideo3);
		v4 = (ImageButton) findViewById(R.id.imgBtnVideo4);
		v5 = (ImageButton) findViewById(R.id.imgBtnVideo5);
		v6 = (ImageButton) findViewById(R.id.imgBtnVideo6);
		v7 = (ImageButton) findViewById(R.id.imgBtnVideo7);
	}

	@SuppressWarnings("deprecation")
	private void InitVideo1To7() {
		// InitLanguage();
		LinearLayout llMap = (LinearLayout) findViewById(R.id.video_layout);
		llMap.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.videos_menu1_new));
		v1 = (ImageButton) findViewById(R.id.imgBtnVideo1);
		v2 = (ImageButton) findViewById(R.id.imgBtnVideo2);
		v3 = (ImageButton) findViewById(R.id.imgBtnVideo3);
		v4 = (ImageButton) findViewById(R.id.imgBtnVideo4);
		v5 = (ImageButton) findViewById(R.id.imgBtnVideo5);
		v6 = (ImageButton) findViewById(R.id.imgBtnVideo6);
		v7 = (ImageButton) findViewById(R.id.imgBtnVideo7);
		v1.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video1_selector));
		v2.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video2_selector));
		v3.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video3_selector));
		v4.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video4_selector));
		v5.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video5_selector));
		v6.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video6_selector));
		v7.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video7_selector));
		TextView lblVideo1 = (TextView) findViewById(R.id.txtVideo1);
		TextView lblVideo2 = (TextView) findViewById(R.id.txtVideo2);
		TextView lblVideo3 = (TextView) findViewById(R.id.txtVideo3);
		TextView lblVideo4 = (TextView) findViewById(R.id.txtVideo4);
		TextView lblVideo5 = (TextView) findViewById(R.id.txtVideo5);
		TextView lblVideo6 = (TextView) findViewById(R.id.txtVideo6);
		TextView lblVideo7 = (TextView) findViewById(R.id.txtVideo7);
		TextView lblVideoTitle = (TextView) findViewById(R.id.txtVideoTitle);
		// TextView lblVideo = (TextView) findViewById(R.id.txtVideo7);
		TextView lblVideo1to7 = (TextView) findViewById(R.id.txtVideo1To7);
		TextView lblVideo7to14 = (TextView) findViewById(R.id.txtVideo7to14);
		if (language == -1)
			language = 1;
		if (prefix == "")
			prefix = "en";

		int idVideo1 = getResources().getIdentifier(prefix + "_video1",
				"string", getPackageName());
		int idVideo2 = getResources().getIdentifier(prefix + "_video4",
				"string", getPackageName());
		int idVideo3 = getResources().getIdentifier(prefix + "_video3",
				"string", getPackageName());
		int idVideo4 = getResources().getIdentifier(prefix + "_video2",
				"string", getPackageName());
		int idVideo5 = getResources().getIdentifier(prefix + "_video7",
				"string", getPackageName());
		int idVideo6 = getResources().getIdentifier(prefix + "_video6",
				"string", getPackageName());
		int idVideo7 = getResources().getIdentifier(prefix + "_video5",
				"string", getPackageName());
		int idVideoTitle = getResources().getIdentifier(prefix + "_videoTitle",
				"string", getPackageName());

		lblVideo1.setText(idVideo1);
		lblVideo2.setText(idVideo2);
		lblVideo3.setText(idVideo3);
		lblVideo4.setText(idVideo4);
		lblVideo5.setText(idVideo5);
		lblVideo6.setText(idVideo6);
		lblVideo7.setText(idVideo7);
		lblVideoTitle.setText(idVideoTitle);

		lblVideo1to7.setOnClickListener(this);

		lblVideo7to14.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	private void InitVideo7To14() {
		// InitLanguage();

		LinearLayout llMap = (LinearLayout) findViewById(R.id.video_layout);
		llMap.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.videos_menu2_new));

		v1 = (ImageButton) findViewById(R.id.imgBtnVideo1);
		v2 = (ImageButton) findViewById(R.id.imgBtnVideo2);
		v3 = (ImageButton) findViewById(R.id.imgBtnVideo3);
		v4 = (ImageButton) findViewById(R.id.imgBtnVideo4);
		v5 = (ImageButton) findViewById(R.id.imgBtnVideo5);
		v6 = (ImageButton) findViewById(R.id.imgBtnVideo6);
		v7 = (ImageButton) findViewById(R.id.imgBtnVideo7);

		v1.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video8_selector));
		v2.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video9_selector));
		v3.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video10_selector));
		v4.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video11_selector));
		v5.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video12_selector));
		v6.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video13_selector));
		v7.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.btn_video14_selector));

		TextView lblVideo1 = (TextView) findViewById(R.id.txtVideo1);
		TextView lblVideo2 = (TextView) findViewById(R.id.txtVideo2);
		TextView lblVideo3 = (TextView) findViewById(R.id.txtVideo3);
		TextView lblVideo4 = (TextView) findViewById(R.id.txtVideo4);
		TextView lblVideo5 = (TextView) findViewById(R.id.txtVideo5);
		TextView lblVideo6 = (TextView) findViewById(R.id.txtVideo6);
		TextView lblVideo7 = (TextView) findViewById(R.id.txtVideo7);
		TextView lblVideoTitle = (TextView) findViewById(R.id.txtVideoTitle);
		// TextView lblVideo = (TextView) findViewById(R.id.txtVideo7);
		TextView lblVideo1to7 = (TextView) findViewById(R.id.txtVideo1To7);
		TextView lblVideo7to14 = (TextView) findViewById(R.id.txtVideo7to14);
		if (language == -1)
			language = 1;
		if (prefix == "")
			prefix = "en";

		int idVideo1 = getResources().getIdentifier(prefix + "_video9",
				"string", getPackageName());
		int idVideo2 = getResources().getIdentifier(prefix + "_video8",
				"string", getPackageName());
		int idVideo3 = getResources().getIdentifier(prefix + "_video10",
				"string", getPackageName());
		int idVideo4 = getResources().getIdentifier(prefix + "_video11",
				"string", getPackageName());
		int idVideo5 = getResources().getIdentifier(prefix + "_video14",
				"string", getPackageName());
		int idVideo6 = getResources().getIdentifier(prefix + "_video13",
				"string", getPackageName());
		int idVideo7 = getResources().getIdentifier(prefix + "_video12",
				"string", getPackageName());
		int idVideoTitle = getResources().getIdentifier(prefix + "_videoTitle",
				"string", getPackageName());

		lblVideo1.setText(idVideo1);
		lblVideo2.setText(idVideo2);
		lblVideo3.setText(idVideo3);
		lblVideo4.setText(idVideo4);
		lblVideo5.setText(idVideo5);
		lblVideo6.setText(idVideo6);
		lblVideo7.setText(idVideo7);
		lblVideoTitle.setText(idVideoTitle);
		lblVideo1to7.setOnClickListener(this);
		lblVideo7to14.setOnClickListener(this);
	}

	private void InitMapLanguage() {
		InitLanguage();
		// lblMap1 = (TextView) findViewById(R.id.txtViewMap1);
		// lblMap1.setTextColor(Color.WHITE);
		//
		// lblMap2 = (TextView) findViewById(R.id.txtViewMap2);
		// lblMap2.setTextColor(Color.BLACK);

		if (language == -1)
			language = 1;
		if (prefix == "")
			prefix = "en";

		int idMap1 = getResources().getIdentifier(prefix + "_map1", "string",
				getPackageName());
		int idMap2 = getResources().getIdentifier(prefix + "_map2", "string",
				getPackageName());

		// lblMap1.setText(idMap1);
		// lblMap1.setOnClickListener(this);
		//
		// lblMap2.setText(idMap2);
		// lblMap2.setOnClickListener(this);
	}

	/*
	 * Launch Audio Activity with Audio Clip Number
	 */
	private void PlayAudio() {
		started = true;
		Intent intent = new Intent(this, PlayAudioActivity.class);
		intent.putExtra("audiono", audioNo);
		intent.putExtra("prefix", prefix);
		startActivity(intent);
	}

	private void PlayVideo() {
		started = true;
		Intent intent = new Intent(this, PlayVideoActivity.class);
		intent.putExtra("videono", videoNo);
		intent.putExtra("prefix", prefix);
		startActivity(intent);
	}

	/*
	 * Launch Guide activity with Language no.
	 */
	private void startGuide() {
		started = true;
		// Set Volume Max
		int volMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, volMax, 0);

		Intent intent = new Intent(this, GuidePageAcitivity.class);
		intent.putExtra("prefix", prefix);
		startActivity(intent);
	}

	/*
	 * Change Map
	 */

	private void changeMap() {
		TextView lbInfo = (TextView) findViewById(R.id.txt_info);
		mImageMapEn = (ImageMap) findViewById(R.id.mapen);
		mImageMapEs = (ImageMap) findViewById(R.id.map_es);

		mImageMapIt = (ImageMap) findViewById(R.id.map_it);
		mImageMapFr = (ImageMap) findViewById(R.id.map_fr);
		mImageMapDe = (ImageMap) findViewById(R.id.map_de);
		mImageMapRu = (ImageMap) findViewById(R.id.map_ru);
		mImageMapPt = (ImageMap) findViewById(R.id.map_pt);

		switch (prefix) {
		case "en":
			mImageMapEn.setImageResource(R.drawable.map_new);
			mImageMapEn.setVisibility(View.VISIBLE);
			mImageMapEs.setVisibility(View.GONE);
			mImageMapIt.setVisibility(View.GONE);
			mImageMapFr.setVisibility(View.GONE);
			mImageMapDe.setVisibility(View.GONE);
			mImageMapRu.setVisibility(View.GONE);
			mImageMapPt.setVisibility(View.GONE);
			lbInfo.setText(R.string.en_info);

			if (!G.isExitVideo) {
				mImageMapEn.moveToPointOne();
				//G.isExitVideo = true;
			}

			openMapEn();

			break;
		case "fr":
			mImageMapFr.setImageResource(R.drawable.latestmap);
			mImageMapEn.setVisibility(View.GONE);
			mImageMapEs.setVisibility(View.GONE);
			mImageMapIt.setVisibility(View.GONE);
			mImageMapFr.setVisibility(View.VISIBLE);
			mImageMapDe.setVisibility(View.GONE);
			mImageMapRu.setVisibility(View.GONE);
			mImageMapPt.setVisibility(View.GONE);
			lbInfo.setText(R.string.fr_info);

			if (!G.isExitVideo) {
				mImageMapFr.moveToPointOne();
				//G.isExitVideo = true;
			}

			openMapFr();
			break;
		case "es":
			mImageMapEs.setImageResource(R.drawable.latestmap);
			mImageMapEn.setVisibility(View.GONE);
			mImageMapEs.setVisibility(View.VISIBLE);
			mImageMapIt.setVisibility(View.GONE);
			mImageMapFr.setVisibility(View.GONE);
			mImageMapDe.setVisibility(View.GONE);
			mImageMapRu.setVisibility(View.GONE);
			mImageMapPt.setVisibility(View.GONE);
			lbInfo.setText(R.string.es_info);

			if (!G.isExitVideo) {
				mImageMapEs.moveToPointOne();
				//G.isExitVideo = true;
			}

			openMapEs();
			break;

		case "it":
			mImageMapIt.setImageResource(R.drawable.latestmap);
			mImageMapEn.setVisibility(View.GONE);
			mImageMapEs.setVisibility(View.GONE);
			mImageMapIt.setVisibility(View.VISIBLE);
			mImageMapFr.setVisibility(View.GONE);
			mImageMapDe.setVisibility(View.GONE);
			mImageMapRu.setVisibility(View.GONE);
			mImageMapPt.setVisibility(View.GONE);
			lbInfo.setText(R.string.it_info);

			if (!G.isExitVideo) {
				mImageMapIt.moveToPointOne();
				//G.isExitVideo = true;
			}

			openMapIt();
			break;
		case "de":
			mImageMapDe.setImageResource(R.drawable.latestmap);
			mImageMapEn.setVisibility(View.GONE);
			mImageMapEs.setVisibility(View.GONE);
			mImageMapIt.setVisibility(View.GONE);
			mImageMapFr.setVisibility(View.GONE);
			mImageMapDe.setVisibility(View.VISIBLE);
			mImageMapRu.setVisibility(View.GONE);
			mImageMapPt.setVisibility(View.GONE);
			lbInfo.setText(R.string.de_info);

			if (!G.isExitVideo) {
				mImageMapDe.moveToPointOne();
				//G.isExitVideo = true;
			}

			openMapDe();
			break;
		case "ru":
			mImageMapRu.setImageResource(R.drawable.latestmap);
			mImageMapEn.setVisibility(View.GONE);
			mImageMapEs.setVisibility(View.GONE);
			mImageMapIt.setVisibility(View.GONE);
			mImageMapFr.setVisibility(View.GONE);
			mImageMapDe.setVisibility(View.GONE);
			mImageMapRu.setVisibility(View.VISIBLE);
			mImageMapPt.setVisibility(View.GONE);
			lbInfo.setText(R.string.ru_info);

			if (!G.isExitVideo) {
				mImageMapRu.moveToPointOne();
				//G.isExitVideo = true;
			}

			openMapRu();
			break;

		case "pt":
			mImageMapPt.setImageResource(R.drawable.latestmap);
			mImageMapEn.setVisibility(View.GONE);
			mImageMapEs.setVisibility(View.GONE);
			mImageMapIt.setVisibility(View.GONE);
			mImageMapFr.setVisibility(View.GONE);
			mImageMapDe.setVisibility(View.GONE);
			mImageMapRu.setVisibility(View.GONE);
			mImageMapPt.setVisibility(View.VISIBLE);
			lbInfo.setText(R.string.pt_info);

			if (!G.isExitVideo) {
				mImageMapPt.moveToPointOne();
				//G.isExitVideo = false;
			}

			openMapPt();
			break;
		default:
			break;
		}

	}

	private void openMapPt() {
		mImageMapPt
				.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

					@Override
					public void onImageMapClicked(int id, ImageMap imageMap) {
						if (id == 2131361899) {
							videoNo = 1;
							PlayVideo();
						} else if (id == 2131361900) {
							videoNo = 4;
							PlayVideo();
						} else if (id == 2131361901) {
							videoNo = 5;
							PlayVideo();
						} else if (id == 2131361902) {
							videoNo = 6;
							PlayVideo();
						} else if (id == 2131361903) {
							videoNo = 7;
							PlayVideo();
						} else if (id == 2131361904) {
							videoNo = 8;
							PlayVideo();
						} else if (id == 2131361905) {
							videoNo = 9;
							PlayVideo();
						} else if (id == 2131361906) {
							videoNo = 10;
							PlayVideo();
						} else if (id == 2131361907) {
							videoNo = 11;
							PlayVideo();
						} else if (id == 2131361908) {
							videoNo = 12;
							PlayVideo();
						} else if (id == 2131361909) {
							videoNo = 13;
							PlayVideo();
						}else if (id == 2131361911) {
							videoNo = 14;
							PlayVideo();
						}else if (id == 2131361910) {
							videoNo = 2;
							PlayVideo();
						}else if (id == 2131361912) {
							videoNo = 3;
							PlayVideo();
						}
						
					}

					@Override
					public void onBubbleClicked(int id) {
						// TODO Auto-generated method stub

					}

				});

	}

	private void openMapRu() {
		mImageMapRu
				.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

					@Override
					public void onImageMapClicked(int id, ImageMap imageMap) {
						if (id == 2131361899) {
							videoNo = 1;
							PlayVideo();
						} else if (id == 2131361900) {
							videoNo = 4;
							PlayVideo();
						} else if (id == 2131361901) {
							videoNo = 5;
							PlayVideo();
						} else if (id == 2131361902) {
							videoNo = 6;
							PlayVideo();
						} else if (id == 2131361903) {
							videoNo = 7;
							PlayVideo();
						} else if (id == 2131361904) {
							videoNo = 8;
							PlayVideo();
						} else if (id == 2131361905) {
							videoNo = 9;
							PlayVideo();
						} else if (id == 2131361906) {
							videoNo = 10;
							PlayVideo();
						} else if (id == 2131361907) {
							videoNo = 11;
							PlayVideo();
						} else if (id == 2131361908) {
							videoNo = 12;
							PlayVideo();
						} else if (id == 2131361909) {
							videoNo = 13;
							PlayVideo();
						}else if (id == 2131361911) {
							videoNo = 2;
							PlayVideo();
						}else if (id == 2131361910) {
							videoNo = 14; //now right
							PlayVideo();
						}else if (id == 2131361912) {
							videoNo = 3;
							PlayVideo();
						}
						
						

					}

					@Override
					public void onBubbleClicked(int id) {
						// TODO Auto-generated method stub

					}

				});

	}

	private void openMapDe() {
		mImageMapDe
				.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

					@Override
					public void onImageMapClicked(int id, ImageMap imageMap) {
						if (id == 2131361899) {
							videoNo = 1;
							PlayVideo();
						} else if (id == 2131361900) {
							videoNo = 4;
							PlayVideo();
						} else if (id == 2131361901) {
							videoNo = 5;
							PlayVideo();
						} else if (id == 2131361902) {
							videoNo = 6;
							PlayVideo();
						} else if (id == 2131361903) {
							videoNo = 7;
							PlayVideo();
						} else if (id == 2131361904) {
							videoNo = 8;
							PlayVideo();
						} else if (id == 2131361905) {
							videoNo = 9;
							PlayVideo();
						} else if (id == 2131361906) {
							videoNo = 10;
							PlayVideo();
						} else if (id == 2131361907) {
							videoNo = 11;
							PlayVideo();
						} else if (id == 2131361908) {
							videoNo = 12;
							PlayVideo();
						} else if (id == 2131361909) {
							videoNo = 13;
							PlayVideo();
						}else if (id == 2131361911) {
							videoNo = 14;
							PlayVideo();
						}else if (id == 2131361910) {
							videoNo = 2;
							PlayVideo();
						}else if (id == 2131361912) {
							videoNo = 3;
							PlayVideo();
						}
						

					}

					@Override
					public void onBubbleClicked(int id) {
						// TODO Auto-generated method stub

					}

				});

	}

	private void openMapIt() {
		mImageMapIt
				.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

					@Override
					public void onImageMapClicked(int id, ImageMap imageMap) {
						if (id == 2131361899) {
							videoNo = 1;
							PlayVideo();
						} else if (id == 2131361900) {
							videoNo = 4;
							PlayVideo();
						} else if (id == 2131361901) {
							videoNo = 5;
							PlayVideo();
						} else if (id == 2131361902) {
							videoNo = 6;
							PlayVideo();
						} else if (id == 2131361903) {
							videoNo = 7;
							PlayVideo();
						} else if (id == 2131361904) {
							videoNo = 8;
							PlayVideo();
						} else if (id == 2131361905) {
							videoNo = 9;
							PlayVideo();
						} else if (id == 2131361906) {
							videoNo = 10;
							PlayVideo();
						} else if (id == 2131361907) {
							videoNo = 11;
							PlayVideo();
						} else if (id == 2131361908) {
							videoNo = 12;
							PlayVideo();
						} else if (id == 2131361909) {
							videoNo = 13;
							PlayVideo();
						}else if (id == 2131361911) {
							videoNo = 14;
							PlayVideo();
						}else if (id == 2131361910) {
							videoNo = 2;
							PlayVideo();
						}else if (id == 2131361912) {
							videoNo = 3;
							PlayVideo();
						}
						
					}

					@Override
					public void onBubbleClicked(int id) {
						// TODO Auto-generated method stub

					}

				});

	}

	private void openMapEs() {
		mImageMapEs
				.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

					@Override
					public void onImageMapClicked(int id, ImageMap imageMap) {
						if (id == 2131361899) {
							videoNo = 1;
							PlayVideo();
						} else if (id == 2131361900) {
							videoNo = 4;
							PlayVideo();
						} else if (id == 2131361901) {
							videoNo = 5;
							PlayVideo();
						} else if (id == 2131361902) {
							videoNo = 6;
							PlayVideo();
						} else if (id == 2131361903) {
							videoNo = 7;
							PlayVideo();
						} else if (id == 2131361904) {
							videoNo = 8;
							PlayVideo();
						} else if (id == 2131361905) {
							videoNo = 9;
							PlayVideo();
						} else if (id == 2131361906) {
							videoNo = 10;
							PlayVideo();
						} else if (id == 2131361907) {
							videoNo = 11;
							PlayVideo();
						} else if (id == 2131361908) {
							videoNo = 12;
							PlayVideo();
						} else if (id == 2131361909) {
							videoNo = 13;
							PlayVideo();
						}else if (id == 2131361911) {
							videoNo = 14;
							PlayVideo();
						}else if (id == 2131361910) {
							videoNo = 2;
							PlayVideo();
						}else if (id == 2131361912) {
							videoNo = 3;
							PlayVideo();
						}
						
					}

					@Override
					public void onBubbleClicked(int id) {
						// TODO Auto-generated method stub

					}

				});

	}

	private void openMapFr() {

		mImageMapFr
				.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {
					@Override
					public void onImageMapClicked(int id, ImageMap imageMap) {

						if (id == 2131361899) {
							videoNo = 1;
							PlayVideo();
						} else if (id == 2131361900) {
							videoNo = 4;
							PlayVideo();
						} else if (id == 2131361901) {
							videoNo = 5;
							PlayVideo();
						} else if (id == 2131361902) {
							videoNo = 6;
							PlayVideo();
						} else if (id == 2131361903) {
							videoNo = 7;
							PlayVideo();
						} else if (id == 2131361904) {
							videoNo = 8;
							PlayVideo();
						} else if (id == 2131361905) {
							videoNo = 9;
							PlayVideo();
						} else if (id == 2131361906) {
							videoNo = 10;
							PlayVideo();
						} else if (id == 2131361907) {
							videoNo = 11;
							PlayVideo();
						} else if (id == 2131361908) {
							videoNo = 12;
							PlayVideo();
						} else if (id == 2131361909) {
							videoNo = 13;
							PlayVideo();
						}else if (id == 2131361911) {
							videoNo = 14;
							PlayVideo();
						}else if (id == 2131361910) {
							videoNo = 2;
							PlayVideo();
						}else if (id == 2131361912) {
							videoNo = 3;
							PlayVideo();
						}
						
					}

					@Override
					public void onBubbleClicked(int id) {
					}

				});

	}

	private void openMapEn() {
		mImageMapEn.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

			@Override
			public void onImageMapClicked(int id, ImageMap imageMap) {
				/*if (id == 2131361896) {
					videoNo = 1;
					PlayVideo();
				} else if (id == 2131361897) {
					videoNo = 2;
					PlayVideo();
				} else if (id == 2131361898) {
					videoNo = 3;
					PlayVideo();
				} else*/
				if (id == 2131361899) {
					videoNo = 1;
					PlayVideo();
				} else if (id == 2131361900) {
					videoNo = 4;
					PlayVideo();
				} else if (id == 2131361901) {
					videoNo = 5;
					PlayVideo();
				} else if (id == 2131361902) {
					videoNo = 6;
					PlayVideo();
				} else if (id == 2131361903) {
					videoNo = 7;
					PlayVideo();
				} else if (id == 2131361904) {
					videoNo = 8;
					PlayVideo();
				} else if (id == 2131361905) {
					videoNo = 9;
					PlayVideo();
				} else if (id == 2131361906) {
					videoNo = 10;
					PlayVideo();
				} else if (id == 2131361907) {
					videoNo = 11;
					PlayVideo();
				} else if (id == 2131361908) {
					videoNo = 12;
					PlayVideo();
				} else if (id == 2131361909) {
					videoNo = 13;
					PlayVideo();
				} else if (id == 2131361911) {
					videoNo = 2; //swap with 14
					PlayVideo();
				} else if (id == 2131361910) {
					videoNo = 14;
					PlayVideo();
				} else if (id == 2131361912) {
					videoNo = 3;
					PlayVideo();
				} else if (id == 2131361913) {                    // added by Prashant
					videoNo = 1;
					PlayVideo();
				}

			}

			@Override
			public void onBubbleClicked(int id) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean selected = false;
		if (event.getAction() == event.ACTION_DOWN) {
			if (mapSelect) {
				if (mapId == 1) {
					Log.e("asdas", "x = " + event.getX());
					Log.e("asdas", "y = " + event.getY());
					for (int i = 0; i < 3; i++) {
						if (event.getX() > map1points[i].x - 25
								&& event.getX() < map1points[i].x + 25) {
							if (event.getY() > map1points[i].y - 25
									&& event.getY() < map1points[i].y + 25) {
								if (i == 0) {
									videoNo = 1;
									selected = true;
									break;
								} else if (i == 1) {
									videoNo = 6;
									selected = true;
									break;
								} else {
									videoNo = 7;
									selected = true;
									break;
								}

							}
						}
					}
					if (selected) {
						PlayVideo();
					}
				} else {
					Log.e("asdas", "x = " + event.getX());
					Log.e("asdas", "y = " + event.getY());
					for (int i = 0; i < 4; i++) {
						if (event.getX() > map2points[i].x - 25
								&& event.getX() < map2points[i].x + 25) {
							if (event.getY() > map2points[i].y - 25
									&& event.getY() < map2points[i].y + 25) {
								videoNo = i + 2;
								selected = true;
								break;
							}
						}
					}
					if (selected) {
						PlayVideo();
					}
				}
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (v.getId()) {
		case R.id.txtVideo1:

			if (event.getAction() == MotionEvent.ACTION_DOWN)
				v1.setPressed(true);
			else
				v1.setPressed(false);
			break;
		case R.id.txtVideo2:

			if (event.getAction() == MotionEvent.ACTION_DOWN)
				v2.setPressed(true);
			else
				v2.setPressed(false);
			break;
		case R.id.txtVideo3:

			if (event.getAction() == MotionEvent.ACTION_DOWN)
				v3.setPressed(true);
			else
				v3.setPressed(false);
			break;
		case R.id.txtVideo4:

			if (event.getAction() == MotionEvent.ACTION_DOWN)
				v4.setPressed(true);
			else
				v4.setPressed(false);
			break;
		case R.id.txtVideo5:

			if (event.getAction() == MotionEvent.ACTION_DOWN)
				v5.setPressed(true);
			else
				v5.setPressed(false);
			break;
		case R.id.txtVideo6:

			if (event.getAction() == MotionEvent.ACTION_DOWN)
				v6.setPressed(true);
			else
				v6.setPressed(false);
			break;
		case R.id.txtVideo7:

			if (event.getAction() == MotionEvent.ACTION_DOWN)
				v7.setPressed(true);
			else
				v7.setPressed(false);
			break;
//		case R.id.txtAudio1:
//			ImageButton v8 = (ImageButton) findViewById(R.id.imgBtnAudio1);
//			if (event.getAction() == MotionEvent.ACTION_DOWN)
//				v8.setPressed(true);
//			else
//				v8.setPressed(false);
//			break;
//		case R.id.txtAudio2:
//			ImageButton v9 = (ImageButton) findViewById(R.id.imgBtnAudio2);
//			if (event.getAction() == MotionEvent.ACTION_DOWN)
//				v9.setPressed(true);
//			else
//				v9.setPressed(false);
//			break;
//		case R.id.txtAudio3:
//			ImageButton v10 = (ImageButton) findViewById(R.id.imgBtnAudio3);
//			if (event.getAction() == MotionEvent.ACTION_DOWN)
//				v10.setPressed(true);
//			else
//				v10.setPressed(false);
//			break;
//		case R.id.txtAudio4:
//			ImageButton v11 = (ImageButton) findViewById(R.id.imgBtnAudio4);
//			if (event.getAction() == MotionEvent.ACTION_DOWN)
//				v11.setPressed(true);
//			else
//				v11.setPressed(false);
//			break;
//		case R.id.txtAudio5:
//			ImageButton v12 = (ImageButton) findViewById(R.id.imgBtnAudio5);
//			if (event.getAction() == MotionEvent.ACTION_DOWN)
//				v12.setPressed(true);
//			else
//				v12.setPressed(false);
//			break;

		}
		return false;
	}

	@Override
	protected void onUserLeaveHint() {
		// super.onUserLeaveHint();
		if (!started) {
			finish();
			Intent i = new Intent(this, MainPageActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		}
	}

	@Override
	protected void onResume() {
		Log.d("TAG", "onResume");
		started = false;
		// VideoId = 1;
		if (G.isExitVideo) {
			if (MapID == 1) {
				//onMap(); //maybe it's don't need
			} else if (MapID == 2) {
				G.isExitVideo = false;
				onVideo();
			}

		}
		if (!isMapLoad) {
			//onMap(); //uncomment this to open map instead of language choose screen
		}
		isMapLoad = true;
		super.onResume();
	}

	private boolean isMapLoad = false;

	@Override
	protected void onPause() {
		Log.d("TAG", "onPause");
		super.onPause();
	}
	
}
