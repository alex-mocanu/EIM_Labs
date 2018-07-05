package ro.pub.cs.systems.eim.lab10.googlemapsgeofencing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

import ro.pub.cs.systems.eim.lab10.R;

public class GoogleMapsGeofenceEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_geofence_event);

        TextView notificationContentTextView = (TextView)findViewById(R.id.notification_content_text_view);

        Button okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> iterator = keys.iterator();
            while(iterator.hasNext()) {
                String key = iterator.next();
                String value = bundle.getString(key);
                notificationContentTextView.append(key + ": " + value + "\n");
            }
        }
    }
}
