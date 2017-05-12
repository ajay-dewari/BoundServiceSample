package com.ajaysinghdewari.boundservicesample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnStartService, btnStopService, btnBindService, btnUnbindService, btnGetNumber;
    ServiceConnection mServiceConnection;
    MyService mService;
    Intent mServiceIntent;
    boolean isServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBindService= (Button) findViewById(R.id.bind_service);
        btnUnbindService= (Button) findViewById(R.id.unbind_service);
        btnStartService= (Button) findViewById(R.id.start_service);
        btnStopService= (Button) findViewById(R.id.stop_service);
        btnGetNumber= (Button) findViewById(R.id.get_number);
        mServiceIntent=new Intent(this, MyService.class);

        btnGetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomNumber();
            }
        });


        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(mServiceIntent);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(mServiceIntent);
                       }
        });

        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService();
            }
        });
        btnUnbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            unBindService();
            }
        });
    }
private void bindService(){
    if(mServiceConnection==null){
        mServiceConnection= new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyService.MyServiceBinder binder= (MyService.MyServiceBinder) iBinder;
                mService=binder.getServiceInstance();
            isServiceBound=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isServiceBound=false;
            }
        };
    }
    bindService(mServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
}

private void unBindService(){
if(isServiceBound){
    unbindService(mServiceConnection);
    isServiceBound=false;
}
}

private void getRandomNumber(){
    if(isServiceBound){
        Toast.makeText(MainActivity.this, "Random No is:"+mService.getRandomNumber(), Toast.LENGTH_LONG).show();
    }else{
        Toast.makeText(MainActivity.this, "Service is not bound to this activity ", Toast.LENGTH_LONG).show();
    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
    }
}
