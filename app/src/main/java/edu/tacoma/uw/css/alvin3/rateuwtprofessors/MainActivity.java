/**
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * MainActivity is the first activity that is shown when the app is opened. It's the splash
 * screen for the app. To continue, the user must click on the button
 */
public class MainActivity extends AppCompatActivity {

    /** A member variable for SharedPreferences*/
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     *
     * Method that checks if the user has already logged in, it call the HomeActivity.
     * If not, it call the LoginActivity.
     *
     * @param view is the view passed in when the button is clicked.
     */
    public void launchLogin(View view) {
        mSharedPreferences = getSharedPreferences("edu.tacoma.uw.rateuwtprofessors.PREFS"
                , Context.MODE_PRIVATE);

        if(!mSharedPreferences.getBoolean("loggedin", false)){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            Intent loginIntent = new Intent(this, HomeActivity.class);
            startActivity(loginIntent);
        }

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
     * Method that is used to transfer the user to the register activity.
     * @param theView is th the view that is passed in when the user clicks on the register button.
     */
    public void loginClick(View theView) {
        mSharedPreferences = getSharedPreferences("edu.tacoma.uw.rateuwtprofessors.PREFS"
                , Context.MODE_PRIVATE);

        if(!mSharedPreferences.getBoolean("loggedin", false)){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            Intent loginIntent = new Intent(this, HomeActivity.class);
            startActivity(loginIntent);
        }

    }
}
