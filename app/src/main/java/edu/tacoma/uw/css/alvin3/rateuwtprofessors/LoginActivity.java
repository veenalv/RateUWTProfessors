package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick(View theView) {
        //String content = getApplicationContext().getText().toString(); //gets you the contents of edit text
        Log.d("EMAIL", findViewById(R.id.email).toString());
        Log.d("PASSWORD", findViewById(R.id.password).toString());
    }
}

