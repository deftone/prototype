package de.deftone.prototype.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import de.deftone.prototype.R;

import static de.deftone.prototype.activities.ExerciseDetailActivity.EXTRA_EXERCISE_NAME;

public class AudioActivity extends AppCompatActivity {

    private TextView textMaxTime;
    private TextView textCurrentPosition;
    private ImageButton buttonStart;
    private SeekBar seekBar;
    private Handler threadHandler = new Handler();
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        String audioName = getIntent().getExtras().getString(EXTRA_EXERCISE_NAME);

        this.textCurrentPosition = this.findViewById(R.id.textView_currentPosion);
        this.textMaxTime = this.findViewById(R.id.textView_maxTime);
        this.buttonStart = this.findViewById(R.id.button_start);
        this.seekBar = this.findViewById(R.id.seekBar);
        this.seekBar.setClickable(false);

        // ID of 'audio' in 'raw' folder.
        int songId = this.getRawResIdByName(audioName);

        // Create MediaPlayer.
        this.mediaPlayer = MediaPlayer.create(this, songId);

        // The duration in milliseconds
        int duration = this.mediaPlayer.getDuration();
        this.seekBar.setMax(duration);
        String maxTimeString = this.millisecondsToString(duration);
        this.textMaxTime.setText(maxTimeString);

        //title
        TextView title = findViewById(R.id.audio_title);
        title.setText(audioName);
    }

    // Find ID of resource in 'raw' folder.
    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        return resID;
    }

    // Convert millisecond to string.
    private String millisecondsToString(int milliseconds) {
        //aktuelle Position in Sekunden
        int secAbsolute = milliseconds / 1000;
        //minuten und sekunden berechnen
        int min = secAbsolute / 60;
        int sec = secAbsolute % 60;
        if (sec < 10)
            return min + ":0" + sec;
        else
            return min + ":" + sec;
    }


    public void doStart(View view) {
        if (!isPlaying) {
            isPlaying = true;
            buttonStart.setImageResource(R.drawable.icon_pause);
            this.mediaPlayer.start();
            // Create a thread to update position of SeekBar.
            UpdateSeekBarThread updateSeekBarThread = new UpdateSeekBarThread();
            threadHandler.postDelayed(updateSeekBarThread, 50);
        } else {
            isPlaying = false;
            buttonStart.setImageResource(R.drawable.icon_play);
            this.mediaPlayer.pause();
        }
    }

    // Thread to Update position for SeekBar.
    class UpdateSeekBarThread implements Runnable {

        public void run() {
            int currentPosition = mediaPlayer.getCurrentPosition();
            String currentPositionStr = millisecondsToString(currentPosition);
            textCurrentPosition.setText(currentPositionStr);

            seekBar.setProgress(currentPosition);
            // Delay thread 50 milisecond.
            threadHandler.postDelayed(this, 50);
        }
    }


    // When user click to "Rewind".
    public void doRewind(View view) {
        int currentPosition = this.mediaPlayer.getCurrentPosition();
        int duration = this.mediaPlayer.getDuration();
        // 5 seconds.
        int SUBTRACT_TIME = 5000;

        if (currentPosition - SUBTRACT_TIME > 0) {
            this.mediaPlayer.seekTo(currentPosition - SUBTRACT_TIME);
        }
    }

    // When user click to "Fast-Forward".
    public void doFastForward(View view) {
        int currentPosition = this.mediaPlayer.getCurrentPosition();
        int duration = this.mediaPlayer.getDuration();
        // 5 seconds.
        int ADD_TIME = 5000;

        if (currentPosition + ADD_TIME < duration) {
            this.mediaPlayer.seekTo(currentPosition + ADD_TIME);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
