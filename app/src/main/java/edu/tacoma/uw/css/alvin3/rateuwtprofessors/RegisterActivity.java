package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    /** The email used to register an account. */
    private EditText mEmail;
    /** The password used to register an account. */
    private EditText mPassword;
    /** The checkBox that is used to make sure user agreed to terms. */
    private CheckBox mCheckBox;
    /** Instance of FirebaseAuth that is used to register an email. */
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void register(View view) {
        Log.d("Register: ", mEmail.getText().toString());
        Log.d("Register: ", mPassword.getText().toString());
        Log.d("Register: ", Boolean.toString(mCheckBox.isChecked()));

        // register now
        if (!mCheckBox.isChecked()) {
            Toast.makeText(getBaseContext(),"Must agree to terms in order to register",
                    Toast.LENGTH_LONG).show();
        } else {
            
            // TODO: add checks to make sure it's a uw email.

            mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Registration: ", "createUserWithEmail:success");
                                Toast.makeText(getBaseContext(), "Account created.",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Registration: ", "createUserWithEmail:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getBaseContext(), "Account is already registered",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
    protected void finish(View view) {
        this.finish();
    }
}
