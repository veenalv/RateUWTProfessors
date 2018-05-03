package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /** Instance of FirebaseAuth that is used for signing in. */
    private FirebaseAuth mAuth;
    /** Email that will be used to sign in. */
    private EditText mEmail;
    /** Password that will be used to sign in. */
    private EditText mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClick(View theView) {
        //String content = getApplicationContext().getText().toString(); //gets you the contents of edit text
        Log.d("EMAIL", mEmail.getText().toString());
        Log.d("PASSWORD", mPassword.getText().toString());

        LoginActivity lol = this;
        // Add code to authenticated user.
        mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goToRatingFragment();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
                            Toast.makeText(getBaseContext(), "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goToRatingFragment() {
        Intent ratingIntent = new Intent(this, RatingActivity.class);
        startActivity(ratingIntent);
    }

    public void registerClick(View theView) {
        //String content = getApplicationContext().getText().toString(); //gets you the contents of edit text
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

}

