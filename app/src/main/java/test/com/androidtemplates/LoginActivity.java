package test.com.androidtemplates;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;

import test.com.androidtemplates.dummy.DummyActivity;
import test.com.androidtemplates.dummy.pojo.PPTFile;


public class LoginActivity extends AppCompatActivity {

    EditText ppt_id;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WAKE_LOCK, Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.SET_ALARM, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS, Manifest.permission.VIBRATE, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.BROADCAST_STICKY};

    ListView list_view;

    ArrayList<PPTFile> files;
    XMLS xmls;
    Button button3;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        //for all permission
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        list_view = (ListView) findViewById(R.id.list_view);


        files = new ArrayList<>();

        button3 = (Button) findViewById(R.id.button3);
        Button button2 = (Button) findViewById(R.id.button2);
        ppt_id = (EditText) findViewById(R.id.ppt_id);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CmsAsyncTask(LoginActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {

                    String filename = files.get(i).getName().replaceAll(".xml", "");
                    System.out.println(filename);
                    ppt_id.setText(filename);
                    button3.performClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ppt_id.getText().toString().equalsIgnoreCase("")) {
                    try {
                        Intent intent = new Intent(LoginActivity.this, DummyActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("ppt_id", ppt_id.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        new CmsAsyncTask(LoginActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String xml_object = "";

            try {
                HttpClient httpclient = new DefaultHttpClient();
                String BASE_URL = context.getResources().getString(R.string.server_ip) + "/cmstest/get_ppt_files";
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
                    xmls = serializer.read(XMLS.class, reader);

                } catch (Exception e) {
                    e.printStackTrace();
                    response_success = false;
                }

                httpclient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
                response_success = false;
            }


            return xml_object;
        }

        @Override
        public void onCancelled() {

        }

        protected void onPostExecute(String result) {
            if (xmls != null) {
                setupListView();
            }
        }
    }

    private void setupListView() {
        try {
            files = xmls.getFiles();
            customAdapter = new CustomAdapter(LoginActivity.this);
            list_view.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class CustomAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater1;

        public CustomAdapter(Context context) {
            this.context = context;
            inflater1 = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return files.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class Holder {
            TextView tv1, tv2;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            View rowView;
            rowView = inflater1.inflate(R.layout.files_list_item, null);
            holder.tv1 = (TextView) rowView.findViewById(R.id.textView1);
            holder.tv2 = (TextView) rowView.findViewById(R.id.textView2);

            holder.tv1.setText(files.get(position).getName() + "  ( " + files.get(position).getId() + " ) ");
            holder.tv2.setText("Created At : "+files.get(position).getCreatedAt() + "");


            return rowView;
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
