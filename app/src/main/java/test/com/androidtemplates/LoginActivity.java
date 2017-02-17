package test.com.androidtemplates;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import test.com.androidtemplates.dummy.DummyActivity;


public class LoginActivity extends AppCompatActivity {

    EditText ppt_id;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WAKE_LOCK, Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.SET_ALARM, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS, Manifest.permission.VIBRATE, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.BROADCAST_STICKY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //for all permission
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        Button button4 = (Button) findViewById(R.id.button4);
        ppt_id = (EditText) findViewById(R.id.ppt_id);

       /* button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Main3Activity.class);
                startActivity(intent);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Main4Activity.class);
                startActivity(intent);
            }
        });
*/
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

       /* button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HotelDemoActivity.class);
                startActivity(intent);
            }
        });*/
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
