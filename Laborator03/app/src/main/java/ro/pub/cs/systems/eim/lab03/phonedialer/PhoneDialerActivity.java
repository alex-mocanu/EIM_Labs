package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class PhoneDialerActivity extends AppCompatActivity {
    EditText phoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        phoneNumber = (EditText)findViewById(R.id.phoneNumber);

        // Create array of buttons
        ArrayList<Button> buttons = new ArrayList<Button>();
        Button b = (Button)findViewById(R.id.button0);
        buttons.add(b);
        b = (Button)findViewById(R.id.button1);
        buttons.add(b);
        b = (Button)findViewById(R.id.button2);
        buttons.add(b);
        b = (Button)findViewById(R.id.button3);
        buttons.add(b);
        b = (Button)findViewById(R.id.button4);
        buttons.add(b);
        b = (Button)findViewById(R.id.button5);
        buttons.add(b);
        b = (Button)findViewById(R.id.button6);
        buttons.add(b);
        b = (Button)findViewById(R.id.button7);
        buttons.add(b);
        b = (Button)findViewById(R.id.button8);
        buttons.add(b);
        b = (Button)findViewById(R.id.button9);
        buttons.add(b);
        b = (Button)findViewById(R.id.buttonStar);
        buttons.add(b);
        b = (Button)findViewById(R.id.buttonHash);
        buttons.add(b);

        ImageButton eraseButton = (ImageButton)findViewById(R.id.buttonErase);
        ImageButton initCallButton = (ImageButton)findViewById(R.id.buttonInitCall);
        ImageButton endCallButton = (ImageButton)findViewById(R.id.buttonEndCall);
        ImageButton contactsButton = (ImageButton)findViewById(R.id.contacts);

        for(int i = 0; i < buttons.size(); ++i)
            buttons.get(i).setOnClickListener(new NumberButtonListener(buttons.get(i).getText().toString()));

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = phoneNumber.getText().toString();
                if(number.length() > 0)
                    number = number.substring(0, number.length() - 1);
                phoneNumber.setText(number);
            }
        });

        initCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            PhoneDialerActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            Constants.PERMISSION_REQUEST_CALL_PHONE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
                    startActivity(intent);
                }
            }
        });

        endCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bla", "Aici");
                String phone = phoneNumber.getText().toString();
                if (phone.length() > 0) {
                    Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                    intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phone);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class NumberButtonListener implements View.OnClickListener {
        String charToAdd;

        public NumberButtonListener(String charToAdd) {
            this.charToAdd = charToAdd;
        }

        @Override
        public void onClick(View view) {
            String newNumber = phoneNumber.getText().toString();
            newNumber = newNumber + this.charToAdd;
            phoneNumber.setText(newNumber);
        }
    }

}
