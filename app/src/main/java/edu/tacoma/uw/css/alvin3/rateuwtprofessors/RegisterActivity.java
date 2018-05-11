/**
 * TCSS 450 - Spring 2018 Team 8. Alvin, Hui, and Maksim.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

/**
 * RegisterActivity is the activity that is used when a user wants to register an account.
 * In order to register an account, the app must be connected to the internet, the email must
 * end with '@uw.edu', the password must be of length 6 - 15, and the checkbox must be enabled.
 * After a successful registration, a toast will be shown that mentions that an email verification
 * has been sent. The email verification must be completed before the user can sign in.
 *
 * @author Maksim
 * @auther Alvin
 * @auther Hui
 */
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

        // Initialize all the variables.
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Method that is used to try registering an account.
     * @param view
     */
    public void register(View view) {
        // Logs for debugging
        Log.d("Register: ", mEmail.getText().toString());
        Log.d("Register: ", mPassword.getText().toString());
        Log.d("Register: ", Boolean.toString(mCheckBox.isChecked()));

        // Section that handles registration

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If not connected to internet, display dialog.
        if (networkInfo == null || !networkInfo.isConnected()) {
            notConnectedToInternet();
        // Make sure that checkbox is checked.
        } else if (!mCheckBox.isChecked()) {
            Toast.makeText(getBaseContext(),"Must agree to terms in order to register", Toast.LENGTH_SHORT).show();
        // Make sure that a @uw.edu email is used.
        } else if (!mEmail.getText().toString().endsWith("@uw.edu")) {
            Toast.makeText(getBaseContext(), "must use uw.edu email", Toast.LENGTH_SHORT).show();
        // Make sure that password has a min length of 6 and max length of 15.
        } else if (mPassword.getText().toString().length() < 6 || mPassword.getText().toString().length() > 15) {
            Toast.makeText(this, "Password must be 6 to 15 digits long", Toast.LENGTH_SHORT).show();
        } else {
            // Try registering the account with given credentials.
            mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Registration is successful.
                                FirebaseUser user = mAuth.getCurrentUser();
                                // Send email verification to the email provided.
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getBaseContext(), "Email verification sent",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            } else {
                                // If sign in fails, display a toast to the user.
                                Log.w("Registration: ", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getBaseContext(), "" + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    /**
     *  Method is called to go back to the sign in activity.
     *
     * @param view is the view that is passed in.
     */
    public void finish(View view) {
        this.finish();
    }

    /**
     * Private helper method that is used to display a dialog that warns the user
     * that the app is not connected to the internet. THey can either close the
     * dialog, or select the button to open the wifi settings.
     */
    private void notConnectedToInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Must be connected to the internet").setTitle("Error");
        builder.setNeutralButton("Open WIFI settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 'Open WIFI settings' button
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
