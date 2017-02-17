package test.com.androidtemplates.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloaderThread implements Runnable {
    private static final int BUFFER_SIZE = 4096;

    String url;
    String folderPath;

    public DownloaderThread(String url, String folderPath) {
        this.url = url;
        this.folderPath = folderPath;
    }


    @Override
    public void run() {
        try {
            URL url1 = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) url1.openConnection();
            int responseCode = httpConn.getResponseCode();

            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    // extracts file name from URL
                    fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
                }

				/*System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
				System.out.println("Content-Length = " + contentLength);
				System.out.println("fileName = " + fileName);*/

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = folderPath + File.separator + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                Log.w("Talentify", "File downloaded " + fileName);
            } else {
                Log.e("Talentify", "Downloading Resource Error-----> " + url);
            }
            httpConn.disconnect();
        } catch (Exception e) {
            Log.e("Talentify", "Downloading Resource Error-----> " + url);
            e.printStackTrace();
        }
    }
}
