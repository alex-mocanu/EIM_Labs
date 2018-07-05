package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var01MainActivity extends AppCompatActivity {
    private Button north, south, east, west;
    private Button secondActivity;
    private ButtonListener buttonListener;
    private EditText editText;
    private String numberOfClicks = "0";

    private int serviceStatus = Constants.SERVICE_STOPPED;
    private MessageBroadcastReceiver messageBroadcastReceiver;
    private IntentFilter intentFilter = new IntentFilter();

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String text;
            int nrClicks;
            Log.d("Clicks", "Number of clicks: " + numberOfClicks);

            switch(view.getId()) {
                case R.id.north:
                    text = editText.getText().toString();
                    text += ",North";
                    editText.setText(text);
                    nrClicks = Integer.valueOf(numberOfClicks);
                    nrClicks++;
                    numberOfClicks = String.valueOf(nrClicks);
                    break;
                case R.id.south:
                    text = editText.getText().toString();
                    text += ",South";
                    editText.setText(text);
                    nrClicks = Integer.valueOf(numberOfClicks);
                    nrClicks++;
                    numberOfClicks = String.valueOf(nrClicks);
                    break;
                case R.id.east:
                    text = editText.getText().toString();
                    text += ",East";
                    editText.setText(text);
                    nrClicks = Integer.valueOf(numberOfClicks);
                    nrClicks++;
                    numberOfClicks = String.valueOf(nrClicks);
                    break;
                case R.id.west:
                    text = editText.getText().toString();
                    text += ",West";
                    editText.setText(text);
                    nrClicks = Integer.valueOf(numberOfClicks);
                    nrClicks++;
                    numberOfClicks = String.valueOf(nrClicks);
                    break;
                case R.id.secondActivity:
                    Intent intent = new Intent(getApplicationContext(), PracticTest01Var01SecondaryActivity.class);
                    intent.putExtra(Constants.INSTRUCTIONS, editText.getText().toString());
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }

            if(Integer.valueOf(numberOfClicks) > Constants.COUNT_THRESHOLD &&
                    serviceStatus == Constants.SERVICE_STOPPED) {
                Log.d("Bla", "Starting service...");
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01Service.class);
                intent.putExtra(Constants.NUMBER_OF_CLICKS, editText.getText().toString());
                getApplicationContext().startService(intent);
                Log.d("Bla", "Should be started!");
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA).toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_main);

        buttonListener = new ButtonListener();
        north = findViewById(R.id.north);
        north.setOnClickListener(buttonListener);
        south = findViewById(R.id.south);
        south.setOnClickListener(buttonListener);
        east = findViewById(R.id.east);
        east.setOnClickListener(buttonListener);
        west = findViewById(R.id.west);
        west.setOnClickListener(buttonListener);
        secondActivity = findViewById(R.id.secondActivity);
        secondActivity.setOnClickListener(buttonListener);

        editText = findViewById(R.id.editText);

        messageBroadcastReceiver = new MessageBroadcastReceiver();
        intentFilter.addAction(Constants.ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, PracticalTest01Var01Service.class);
        stopService(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.CLICK_COUNTER, numberOfClicks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString(Constants.CLICK_COUNTER) != null) {
            numberOfClicks = savedInstanceState.get(Constants.CLICK_COUNTER).toString();
        } else {
            numberOfClicks = "0";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}
