/**@author Sebastian Gonzalez*/

package edu.utep.cs.cs4330.mymusicplayer;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private EditText urlEdit;
    private ProgressBar progressBar;
    private Button stopButton, startButton;
    public boolean stopMe = false;
    Intent intent ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlEdit = findViewById(R.id.urlEdit);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        stopButton = findViewById(R.id.stopButton);
        startButton = findViewById(R.id.playButton);
        stopButton.setEnabled(false);
        intent = new Intent(this, MusicService.class);

        player = MediaPlayer.create(this, Uri.parse(urlEdit.getText().toString()));

        stopButton.setOnClickListener(view -> stopClicked(view));
        startButton.setOnClickListener(view -> playClicked(view));
        new Thread (){
            @Override
            public void run() {
                progressBar.setMax(player.getDuration());
                super.run();
                while(player.isPlaying()){
                    for (int i = 0; i < player.getDuration() ; i++) {
                        progressBar.incrementProgressBy(i);
                    }
                }
            }
        }.start();
    }

    /** Called when the play button is clicked. */
    public void playClicked(View view) {
        //toast("start");

        stopMe = false;
        stopButton.setEnabled(true);
        startButton.setEnabled(false);
        startButton.setBackgroundColor(Color.GREEN);
        stopButton.setBackgroundColor(Color.RED);
        startService(view);




    }

    public void startService(View view){

    String URL = urlEdit.getText().toString();
    intent.putExtra("url", URL);
    intent.putExtra("length",player.getDuration());
    startService(intent);


}

    /** Called when the stop button is clicked. */

    public void stopClicked(View view)  {
       // toast("Stop");
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        stopService(intent);

    }


    /** Shows a toast message. */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //--
    //-- WRITE YOUR CODE HERE
    //--

}
