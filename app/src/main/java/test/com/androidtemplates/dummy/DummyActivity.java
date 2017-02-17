package test.com.androidtemplates.dummy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rey.material.widget.ProgressView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringReader;

import test.com.androidtemplates.R;
import test.com.androidtemplates.dummy.fragments.NonScrollableTemplate;
import test.com.androidtemplates.dummy.fragments.ScroollabelTemplate;
import test.com.androidtemplates.dummy.pojo.PPT;


public class DummyActivity extends AppCompatActivity {
    String ppt_id;
    private ProgressView progressView;
    private PPT presentation_canvas;
   // DatabaseHandler databaseHandler;


    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WAKE_LOCK, Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.SET_ALARM, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS, Manifest.permission.VIBRATE, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.BROADCAST_STICKY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for all permission
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dummy);
        progressView = (ProgressView) findViewById(R.id.progress);

        try {
            Bundle bundle = getIntent().getExtras();
            ppt_id = bundle.getString("ppt_id");
           // databaseHandler = new DatabaseHandler(DummyActivity.this);

            setupData();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setupData() {
        new CmsAsyncTask(DummyActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    public class CmsAsyncTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private boolean response_success = true;


        public CmsAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressView.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... params) {
           /* String xml_object = "";

            try {
                Serializer serializer = new Persister();
                presentation_canvas = serializer.read(PPT.class, getResources().getAssets().open("template2.xml"));
            } catch (Exception e) {
                e.printStackTrace();
            }*/


            String xml_object = "";

            try {
               /* Cursor c = databaseHandler.getData(Integer.parseInt(ppt_id));
                if (c != null && c.moveToFirst()) {
                    StringReader reader = new StringReader(c.getString(1));
                    Serializer serializer = new Persister();
                    presentation_canvas = serializer.read(PPT.class, reader);
                } else*/ {
                    //loacl xml
                    //fetchData();

                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        String BASE_URL = context.getResources().getString(R.string.server_ip) + "/video/xmls/" + ppt_id.replaceAll(" ","%20")+".xml";
                        Log.v("Talentify", "BASE_URL " + BASE_URL);

                        int timeout = 80; // seconds
                        HttpParams httpParams = httpclient.getParams();
                        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
                        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

                        HttpPost httppost = new HttpPost(BASE_URL);

                        HttpResponse response = httpclient.execute(httppost);

                        HttpEntity entity = response.getEntity();
                        xml_object = EntityUtils.toString(entity, "UTF-8");
                        xml_object = xml_object.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");

                        try {
                            StringReader reader = new StringReader(xml_object);
                            Serializer serializer = new Persister();
                            presentation_canvas = serializer.read(PPT.class, reader);

                        } catch (Exception e) {
                            e.printStackTrace();
                            response_success = false;
                        }

                        if (response_success) {
                            Log.v("Talentify", "Successfully got response from ->" + ppt_id);

                            final AssessmentResourceUtility cmsResourceUtility = new AssessmentResourceUtility(context, xml_object, 1000);
                            //cmsResourceUtility.fetchResource();
                           // databaseHandler.saveContent(ppt_id + "", xml_object);
                        } else {
                            response = httpclient.execute(httppost);
                            entity = response.getEntity();
                            xml_object = EntityUtils.toString(entity, "UTF-8");
                            xml_object = xml_object.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");

                            try {
                                response_success = true;
                                StringReader reader = new StringReader(xml_object);
                                Serializer serializer = new Persister();
                                presentation_canvas = serializer.read(PPT.class, reader);
                                Log.v("Talentify", "Successfully got response from ->" + ppt_id);
                                final AssessmentResourceUtility cmsResourceUtility = new AssessmentResourceUtility(context, xml_object, 1000);
                               // cmsResourceUtility.fetchResource();
                              //  databaseHandler.saveContent(ppt_id + "", xml_object);

                            } catch (Exception e) {
                                e.printStackTrace();
                                response_success = false;
                            }
                        }


                        httpclient.getConnectionManager().shutdown();
                    } catch (Exception e) {
                        e.printStackTrace();
                        response_success = false;
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return xml_object;
        }

        @Override
        public void onCancelled() {

        }

        protected void onPostExecute(String result) {
            if (presentation_canvas != null) {
                progressView.setVisibility(View.GONE);
                setupFragments();
            }
        }
    }

    private void setupFragments() {

        if (presentation_canvas != null && presentation_canvas.getScrollable() != null && presentation_canvas.getScrollable()) {
            ScroollabelTemplate scroollabelTemplate = new ScroollabelTemplate();
            Bundle bundle = new Bundle();
            bundle.putSerializable("presentation", presentation_canvas);
            scroollabelTemplate.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, scroollabelTemplate).commit();
        } else {
            NonScrollableTemplate nonScrollableTemplate = new NonScrollableTemplate();
            Bundle bundle = new Bundle();
            bundle.putSerializable("questions", presentation_canvas.getQuestions());
            bundle.putSerializable("variables", presentation_canvas.getVariables());
            bundle.putString("audioUrl", presentation_canvas.getAudioUrl());
            nonScrollableTemplate.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, nonScrollableTemplate).commit();
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
