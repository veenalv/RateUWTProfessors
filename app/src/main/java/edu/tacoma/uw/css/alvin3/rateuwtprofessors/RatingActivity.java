/**
 * TCSS 450 - Spring 2018 Team 8.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating.Rating;

/**
 * Create a parent activity of list fragment.
 */
public class RatingActivity extends AppCompatActivity implements
        RatingListFragment.OnListFragmentInteractionListener,
        RatingDeatilFragment.OnFragmentInteractionListener{

    private RatingListFragment rlf;
    MenuItem actionMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        rlf = new RatingListFragment();
        if (findViewById(R.id.rating_fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rating_fragment_container, rlf)
                    .commit();
        }

    }

    /**
     * Override OnListfragmentInteraction class's methoid.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rating, menu);
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) searchItem.getActionView();

        // Configure the search info and add any event listeners...
        // Define the listener
        MenuItem.OnActionExpandListener expandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                rlf.setAdapter(rlf.mRatingList);
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                getSupportFragmentManager().popBackStack();
                rlf.setAdapter(rlf.mRatingList);
                return true;  // Return true to expand action view
            }
        };

        // Get the MenuItem for the action item
        actionMenuItem = menu.findItem(R.id.action_search);

        // Assign the listener to that action item
        actionMenuItem.setOnActionExpandListener(expandListener);
        // Any other things you have to do when creating the options menu...
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "SUBMITTED", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(getApplicationContext(), String.valueOf(getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT)
//                        .show();
                rlf.setAdapter(rlf.filter(newText));
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
//            Toast.makeText(this, "SEARCH CLIOCKED", Toast.LENGTH_SHORT)
//                    .show();
        } else if (id == R.id.action_settings) {
//            Toast.makeText(this, "SSETTINGS CLICKED", Toast.LENGTH_SHORT)
//                    .show();
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
        actionMenuItem.collapseActionView();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
