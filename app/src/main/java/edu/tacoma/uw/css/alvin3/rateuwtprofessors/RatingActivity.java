/**
 * TCSS 450 - Spring 2018 Team 8.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating.Rating;

/**
 * Create a parent activity of list fragment.
 */
public class RatingActivity extends AppCompatActivity implements
        RatingListFragment.OnListFragmentInteractionListener,
        RatingDeatilFragment.OnFragmentInteractionListener{

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

    /**
     * Override OnListfragmentInteraction class's methoid.
     * @param item rating object
     */
    @Override
    public void onListFragmentInteraction(Rating item) {
        RatingDeatilFragment ratingDeatilFragment = new RatingDeatilFragment();
        Bundle args = new Bundle();
        args.putSerializable(RatingDeatilFragment.RATING_ITEM_SELECTED,item);
        ratingDeatilFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rating_fragment_container,ratingDeatilFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
