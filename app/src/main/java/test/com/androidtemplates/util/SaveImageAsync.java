package test.com.androidtemplates.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Feroz on 24/11/2016.
 */

public class SaveImageAsync extends AsyncTask<String, Void, String> {
    private ImageSaver imageSaver;

    public SaveImageAsync(ImageSaver imageSaver){
        this.imageSaver = imageSaver;
    }
    @Override
    protected String doInBackground(String... params) {
        Bitmap bmp = null;
        try{
            URL url = new URL(params[0].replaceAll(" ", "%20"));
            InputStream fileInputStream = url.openStream();
            imageSaver.save(fileInputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String string) {
    }
}
