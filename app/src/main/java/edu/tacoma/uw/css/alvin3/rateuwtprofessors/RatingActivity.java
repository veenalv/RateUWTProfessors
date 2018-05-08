package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating.Rating;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rating, menu);
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(this, "SEARCH CLIOCKED", Toast.LENGTH_SHORT)
                    .show();
        } else if (id == R.id.action_settings) {
            Toast.makeText(this, "SSETTINGS CLICKED", Toast.LENGTH_SHORT)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

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
