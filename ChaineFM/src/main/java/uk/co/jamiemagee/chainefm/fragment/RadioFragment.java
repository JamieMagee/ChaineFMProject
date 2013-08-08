package uk.co.jamiemagee.chainefm.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import uk.co.jamiemagee.chainefm.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by jamagee on 05/08/13.
 */
public class RadioFragment extends Fragment {

    private ImageButton playpause;
    private ProgressBar spinner;
    private TextView title;
    private boolean playing = false;
    private MediaPlayer mediaPlayer;

    public RadioFragment() {
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.radio_fragment, container, false);

        assert v != null;
        playpause = (ImageButton)v.findViewById(R.id.playpause);
        spinner = (ProgressBar)v.findViewById(R.id.progressBar);
        title = (TextView)v.findViewById(R.id.titletext);

        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playing) {
                    if (isOnline()) {
                        play();
                    }
                    else {
                        Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    stop();
                }
            }
        });
        if (playing) {
            playpause.setImageResource(android.R.drawable.ic_media_pause);
            title.setText(R.string.click_to_pause);
        }
        return v;
    }

    public void play() {
        playpause.setImageResource(android.R.drawable.ic_media_pause);
        playpause.setClickable(false);
        spinner.setVisibility(View.VISIBLE);
        title.setText(R.string.loading);
        try {
            String url = "http://stream.psyradio.com.ua:8000/128kbps";
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                title.setText(R.string.click_to_pause);
                spinner.setVisibility(View.INVISIBLE);
                playing=true;
                playpause.setClickable(true);
            }
        });
        mediaPlayer.prepareAsync();
    }

    public void stop() {
        playpause.setImageResource(android.R.drawable.ic_media_play);
        title.setText(R.string.click_to_play);
        mediaPlayer.stop();
        mediaPlayer.reset();
        playing = false;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }
}
