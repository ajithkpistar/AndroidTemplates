package test.com.androidtemplates.dummy.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import test.com.androidtemplates.R;
import test.com.androidtemplates.util.AudioVideoSaver;
import test.com.androidtemplates.util.SaveAudioVideoAsync;


/**
 * Created by ajith on 15-02-2017.
 */

public class AudioPlayerUtil {

    private int length;
    private Context context;
    private MediaPlayer mediaPlayer;
    private String url;
    private boolean isChildPlaying;


    public AudioPlayerUtil(Context context, String url) {
        this.context = context;
        this.url = url;

        if (this.url != null && !this.url.equalsIgnoreCase("") && !this.url.equalsIgnoreCase("none")) {
            cretaeAudio();
        }

    }

    public void startPlay() {

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

    }

    public void resumePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }

    public void pausePlay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        } else {
            length = 0;
        }
    }


    public void restart() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }

    public void cretaeAudio() {
        try {

            if (!url.contains("http")) {
                url = context.getResources().getString(R.string.resources_fetch_ip) + "/" + url;
            }
            int index = url.lastIndexOf("/");
            String audio_name = url.substring(index, url.length()).replace("/", "");
            //url = url.replace("", "/content/compress?src=orgadmin&file=/video/");
            AudioVideoSaver audioVideoSaver = new AudioVideoSaver(context).
                    setParentDirectoryName("" + 1000).
                    setFileName(audio_name.replace(".wav", ".mp3")).
                    setExternal(AudioVideoSaver.isExternalStorageReadable());
            Boolean file_exist = audioVideoSaver.checkFile();

            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer = null;
                //mediaPlayer.release();
            }
            mediaPlayer = new MediaPlayer();

            Uri videouri = null;

            if (file_exist) {
                try {
                    videouri = Uri.fromFile(audioVideoSaver.load());
                    mediaPlayer.setDataSource(context, videouri);
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    videouri = Uri.parse(url);
                    mediaPlayer.setDataSource(context, videouri);
                    mediaPlayer.prepare();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                new SaveAudioVideoAsync(audioVideoSaver).execute(url);
            }
        } catch (Exception e) {

        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }


    public boolean isChildPlaying() {
        return isChildPlaying;
    }

    public void setChildPlaying(boolean childPlaying) {
        isChildPlaying = childPlaying;
    }
}
