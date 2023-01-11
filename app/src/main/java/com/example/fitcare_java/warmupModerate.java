package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

public class warmupModerate extends AppCompatActivity {

    //Declare variables for the video player
    ExoPlayer exoPlayer;
    ImageView bt_fullscreen;
    boolean isFullScreen = false;
    boolean isLock = false;
    String videoPath;
    PlayerView playerView;
    ProgressBar progressBar;

    //screen lock variables
    LinearLayout sec_mid;
    LinearLayout sec_bottom;

    //declare view variables
    TextView appBar, txtTitle;
    ImageView btnBack, imageView2;
    HorizontalScrollView hSV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warmup_moderate);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //set variables
        playerView = findViewById(R.id.player);
        progressBar = findViewById(R.id.progress_bar);
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
        ImageView bt_lockscreen = findViewById(R.id.exo_lock);

        //textviews images variables
        appBar = findViewById(R.id.appBar);
        btnBack = findViewById(R.id.btnBack);
        imageView2 = findViewById(R.id.imageView2);
        txtTitle = findViewById(R.id.txtTitle);
        hSV = findViewById(R.id.hSV);


        //screen lock variables
        sec_mid = findViewById(R.id.sec_controlvid1);
        sec_bottom = findViewById(R.id.sec_controlvid2);

        //getting and setting the original parameters
        final ViewGroup.LayoutParams originalLayoutParams = playerView.getLayoutParams();

        /*---------------------------------VIDEO CONTROLS-------------------------------*/

        //fullscreen onclick
        bt_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if it is in fullscreen or not and setting specified tasks
                if(!isFullScreen){
                    //change drawable icon
                    bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen_exit));

                    //change layout to landscape and full screen
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    playerView.setLayoutParams(layoutParams);

                    // to hide status and navigation
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
                else{
                    //change drawable icon
                    bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen));

                    //change layout to original form
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    playerView.setLayoutParams(originalLayoutParams);

                    //to show nav and status bar
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
                isFullScreen = !isFullScreen;

                //to hide views to fullscreen video
                if (appBar.getVisibility() == View.VISIBLE){
                    appBar.setVisibility(View.GONE);
                    btnBack.setVisibility(View.GONE);
                    imageView2.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.GONE);
                    hSV.setVisibility(View.GONE);

                } else {
                    appBar.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    txtTitle.setVisibility(View.VISIBLE);
                    hSV.setVisibility(View.VISIBLE);
                }
            }
        });

        //lockscreen onclcik
        bt_lockscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLock){
                    bt_lockscreen.setImageDrawable(ContextCompat
                            .getDrawable(getApplicationContext(), R.drawable.ic_baseline_lock));
                }
                else {
                    bt_lockscreen.setImageDrawable(ContextCompat
                            .getDrawable(getApplicationContext(), R.drawable.ic_outline_lock_open));
                }
                isLock = !isLock;
                lockScreen(isLock);
            }
        });

        //exoPlayer instance
        exoPlayer = new ExoPlayer.Builder(this)
                .setSeekBackIncrementMs(5000)
                .setSeekBackIncrementMs(5000)
                .build();
        playerView.setPlayer(exoPlayer);
        playerView.setKeepScreenOn(true);

        //track state (buff or nah)
        exoPlayer.addListener(new Player.Listener(){
            @Override
            public void onPlaybackStateChanged(int playbackState){
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                else if (playbackState == Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        //VIDEO URL
        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.comp_warmupmoderate;
        Uri uri = Uri.parse(videoPath);
        MediaItem media = MediaItem.fromUri(uri);
        exoPlayer.setMediaItem(media);
        exoPlayer.prepare();
        exoPlayer.play();

        //Back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warmupModerate.super.onBackPressed();
            }
        });

    }

    /*--------------------------------------VIDEO FUNCTIONS--------------------------------------*/
    //lock screen function
    private void lockScreen(boolean lock)
    {
        //just hide the control for lock screen and vise versa
        if(lock)
        {
            sec_mid.setVisibility(View.INVISIBLE);
            sec_bottom.setVisibility(View.INVISIBLE);
        }
        else
        {
            sec_mid.setVisibility(View.VISIBLE);
            sec_bottom.setVisibility(View.VISIBLE);
        }
    }

    //when is in lock screen we not accept for backpress button
    @Override
    public void onBackPressed()
    {
        //on lock screen back press button not work
        if(isLock) return;

        //if user is in landscape mode we turn to portriat mode first then we can exit the app.
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            bt_fullscreen.performClick();
        }
        else super.onBackPressed();
    }

    // pause or release the player prevent memory leak
    @Override
    public void onStop()
    {
        super.onStop();
        exoPlayer.stop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        exoPlayer.pause();
    }
}