package controllers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;

/**
 * Created by samah on 22/03/2016.
 */
public class ApplicationService extends Service {
    Timer ConTime;
    int intervalInSec;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intervalInSec=intent.getIntExtra("Interval", 60);
        // MyDBH =intent.getParcelableExtra("MyDBHandler");
        ConTime=new Timer();
       // ConTime.schedule(new CheckForConnection(), 0, intervalInSec*1000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
