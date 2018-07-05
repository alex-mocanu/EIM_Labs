package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by alexm on 29.03.2018.
 */

public class PracticalTest01Var01Service extends Service {
    ProcessingThread processingThread = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Started service!");
        String instructions = intent.getStringExtra(Constants.NUMBER_OF_CLICKS);
        processingThread = new ProcessingThread(this, instructions);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {

    }
}
