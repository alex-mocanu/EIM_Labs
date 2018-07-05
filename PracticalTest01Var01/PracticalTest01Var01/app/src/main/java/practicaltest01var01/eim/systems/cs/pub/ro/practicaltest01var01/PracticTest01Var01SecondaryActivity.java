package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticTest01Var01SecondaryActivity extends AppCompatActivity {
    private Button register, cancel;
    private ButtonListener buttonListener;
    private EditText instructions;

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.register:
                    setResult(RESULT_OK);
                    break;
                case R.id.cancel:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practic_test01_var01_secondary);

        buttonListener = new ButtonListener();
        register = findViewById(R.id.register);
        register.setOnClickListener(buttonListener);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(buttonListener);

        instructions = findViewById(R.id.instructions);
        Intent intent = getIntent();
        if(intent != null && intent.getExtras().containsKey(Constants.INSTRUCTIONS))
            instructions.setText(intent.getStringExtra(Constants.INSTRUCTIONS));
    }
}
