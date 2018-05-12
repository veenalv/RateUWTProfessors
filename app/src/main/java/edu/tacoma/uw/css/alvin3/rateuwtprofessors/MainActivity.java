/**
 * TCSS 450 - Spring 2018 Team 8. Alvin, Hui, and Maksim.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * MainActivity is the first activity that is shown when the app is opened. It's the splash
 * screen for the app. To continue, the user must click on the button
 *
 * @author Maksim
 * @auther Alvin
 * @auther Hui
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Method that is called to continue to the sign in activity.
     *
     * @param view is the view passed in when the button is clicked.
     */
    public void launchLogin(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
