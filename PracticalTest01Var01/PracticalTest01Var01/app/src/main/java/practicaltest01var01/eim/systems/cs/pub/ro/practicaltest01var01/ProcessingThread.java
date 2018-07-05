package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by alexm on 29.03.2018.
 */

public class ProcessingThread extends Thread {
    private Context context;
    private boolean isRunning = true;
    private String instructions;

    public ProcessingThread(Context ctx, String instructions) {
        context = ctx;
        this.instructions = instructions;
    }

    @Override
    public void run() {
        Log.d("Thread", "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        sleep();
        sendMessage();
        Log.d("Thread", "Thread sent data!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + instructions);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Log.d("Service", "Sleep");
            Thread.sleep(5000);
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
