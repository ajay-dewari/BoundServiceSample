package com.ajaysinghdewari.boundservicesample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {

    private final static String TAG=MyService.class.getSimpleName();
    private int mRandomNumber;
    private boolean mIsRandomGeneratorIsOn;
    private final static int MIN=0;
    private final static int MAX=100;
    private MyServiceBinder mServiceBinder=new MyServiceBinder();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mServiceBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "OnStartCommand() triggred, Thread_id= "+Thread.currentThread().getId());

        new Thread(new Runnable() {
            @Override
            public void run() {
           startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }

    protected int getRandomNumber(){
        return mRandomNumber;
    }
    private void stopRandomNumberGenerator(){
        mIsRandomGeneratorIsOn=false;
    }
    private void startRandomNumberGenerator(){
        mIsRandomGeneratorIsOn=true;
        int i=0;
        while(mIsRandomGeneratorIsOn){
            try{
                Thread.sleep(1000);
                mRandomNumber=new Random().nextInt(MAX)+MIN;
                Log.d(TAG, "Thread_ID=="+Thread.currentThread().getId()+"  RandomNumber = "+i++);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
    }
protected class MyServiceBinder extends Binder {
    public MyService getServiceInstance(){
        return MyService.this;
    }
}
}
