package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        if (findViewById(R.id.rating_fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rating_fragment_container, new RatingListFragment())
                    .commit();
        }


    }
}
