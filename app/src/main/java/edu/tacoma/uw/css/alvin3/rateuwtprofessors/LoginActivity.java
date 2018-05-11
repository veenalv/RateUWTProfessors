/**
 * TCSS 450 - Spring 2018 Team 8.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

/**
 * LoginActivity is the activity is used when a user tries to
 * sign into the app. The user can click to go to the register activity,
 * or click to sign in.
 *
 * @author Maksim
 * @auther Alvin
 * @auther Hui
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

    /**
     * Method that is used to try signing in the user given the email
     * and password. In order to sign in the app must be connected to the internet,
     * and the account has been registered and verified via email.
     *
     * @param theView is the view passed in when the sign in button is clicked.
     */
    public void onClick(View theView) {
        // Log information for help during debugging.
        Log.d("EMAIL", mEmail.getText().toString());
        Log.d("PASSWORD", mPassword.getText().toString());

        // sign in section below.

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // check if app is connected to the internet.
        if (networkInfo == null || !networkInfo.isConnected()) {
            notConnectedToInternet();
        // Make sure that the email is a @uw.edu email
        } else if (!mEmail.getText().toString().endsWith("@uw.edu")) {
            Toast.makeText(getBaseContext(), "must use uw.edu email", Toast.LENGTH_SHORT).show();
        // Make sure that password has a min length of 6 and max length of 15.
        } else if (mPassword.getText().toString().length() < 6 || mPassword.getText().toString().length() > 15) {
            Toast.makeText(this, "Password must be 6 to 15 digits long", Toast.LENGTH_SHORT).show();
        } else {
            // try signing in
            mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // if account has been created.
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                // if email is verified, go to next activity.
                                if (user.isEmailVerified()) {
                                    goToRatingActivity();
                                } else { // login is successful, but account is not verified.
                                    Toast.makeText(getBaseContext(), "Please verify account with email", Toast.LENGTH_SHORT).show();
                                }
                            // if sign in fails, display message to the user
                            } else {
                                Log.w("login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(getBaseContext(), "" + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    /**
     * method that is used to transfer the user to the rating activity.
     */
    public void goToRatingActivity() {
        Intent ratingIntent = new Intent(this, RatingActivity.class);
        startActivity(ratingIntent);
    }

    /**
     * Method that is used to transfer the user to the register activity.
     * @param theView is th the view that is passed in when the user clicks on the register button.
     */
    public void registerClick(View theView) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
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

