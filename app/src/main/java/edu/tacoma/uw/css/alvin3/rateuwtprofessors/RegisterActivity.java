package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    protected void onClick(View view) {
        Log.d("REG","REGISTER!@");
    }
    protected void finish(View view) {
        this.finish();
    }
}
