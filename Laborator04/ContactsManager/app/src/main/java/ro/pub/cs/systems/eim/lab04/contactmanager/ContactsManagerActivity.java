package ro.pub.cs.systems.eim.lab04.contactmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        Button showFields = findViewById(R.id.showFields);
        Button save = findViewById(R.id.save);
        Button cancel = findViewById(R.id.cancel);
        EditText phoneEditText = findViewById(R.id.phone);

        showFields.setOnClickListener(new ButtonListener());
        save.setOnClickListener(new ButtonListener());
        cancel.setOnClickListener(new ButtonListener());

        Intent intent = getIntent();
        if(intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if(phone != null) {
                phoneEditText.setText(phone);
            }
            else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }

    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int buttonID = view.getId();

            switch(buttonID){
                case R.id.showFields: {
                    LinearLayout hidden = findViewById(R.id.hiddenLayout);
                    Button showFields = findViewById(buttonID);

                    String buttonState = showFields.getText().toString();
                    if(buttonState.equals("SHOW ADDITIONAL FIELDS")) {
                        showFields.setText("HIDE ADDTIONAL FIELDS");
                        hidden.setVisibility(LinearLayout.GONE);
                    }
                    else {
                        showFields.setText("SHOW ADDITIONAL FIELDS");
                        hidden.setVisibility(LinearLayout.VISIBLE);
                    }
                } break;
                case R.id.save: {
                    String name = ((EditText)findViewById(R.id.name)).getText().toString();
                    String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
                    String email = ((EditText)findViewById(R.id.email)).getText().toString();
                    String address = ((EditText)findViewById(R.id.address)).getText().toString();
                    String job = ((EditText)findViewById(R.id.job)).getText().toString();
                    String company = ((EditText)findViewById(R.id.company)).getText().toString();
                    String website  = ((EditText)findViewById(R.id.website)).getText().toString();
                    String  im = ((EditText)findViewById(R.id.im)).getText().toString();


                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (job != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                } break;
                case R.id.cancel: {
                    setResult(Activity.RESULT_CANCELED, new Intent());
                } break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
