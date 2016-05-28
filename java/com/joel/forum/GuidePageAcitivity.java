package com.joel.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class GuidePageAcitivity extends Activity implements OnClickListener {

    private RelativeLayout rlGuide;
    private String lang;
    boolean guideStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_guidepage);
        rlGuide = (RelativeLayout) findViewById(R.id.guide_relative);

        lang = getIntent().getStringExtra("prefix");

        int resId = getResources().getIdentifier("guide_start_" + lang + "_1", "drawable", getPackageName());
        rlGuide.setBackgroundDrawable(getResources().getDrawable(resId));
        rlGuide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guide_relative:
                startGuideVideo();
                break;
        }
    }

    private void startGuideVideo() {
        int resId = getResources().getIdentifier("guide_start_" + lang + "_2", "drawable", getPackageName());
        rlGuide.setBackgroundDrawable(getResources().getDrawable(resId));

        guideStart = true;
        Intent intent = new Intent(this, PlayVideoActivity.class);
        intent.putExtra("prefix", lang);
        intent.putExtra("guide", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onUserLeaveHint() {
        if (!guideStart) {
            finish();
            Intent i = new Intent(this, MainPageActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}
