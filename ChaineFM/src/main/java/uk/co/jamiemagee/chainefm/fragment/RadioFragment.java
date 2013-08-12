package uk.co.jamiemagee.chainefm.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import uk.co.jamiemagee.chainefm.R;
import uk.co.jamiemagee.chainefm.activity.MainActivity;

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
                addNotification();
            }
        });
        mediaPlayer.prepareAsync();
    }

    public void stop() {
        playpause.setImageResource(android.R.drawable.ic_media_play);
        title.setText(R.string.click_to_play);
        mediaPlayer.stop();
        mediaPlayer.reset();
        removeNotification();
        playing = false;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    private void addNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.click_to_pause))
                        .setTicker(getString(R.string.app_name)+"\n"+getString(R.string.click_to_pause));

        Intent resultIntent = new Intent(getActivity(), MainActivity.class);

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getActivity(), 0, resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0,mBuilder.build());
    }

    private void removeNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(0);
    }
}
