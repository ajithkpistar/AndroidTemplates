package test.com.androidtemplates.util;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;


/**
 * Created by Feroz on 24/11/2016.
 */

public class SaveAudioVideoAsync extends AsyncTask<String, Void, String> {
    private AudioVideoSaver audioVideoSaver;

    public SaveAudioVideoAsync(AudioVideoSaver audioVideoSaver){
        this.audioVideoSaver = audioVideoSaver;
    }
    @Override
    protected String doInBackground(String... params) {
        InputStream fileInputStream = null;
        try {
            fileInputStream  = new URL(params[0].replaceAll(" ", "%20")).openStream();
            audioVideoSaver.save(fileInputStream);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String fileInputStream) {

    }
}