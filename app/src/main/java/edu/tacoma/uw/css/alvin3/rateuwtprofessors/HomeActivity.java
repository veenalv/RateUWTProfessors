/**
 * The HomeActivity is the main activity our app is based around.
 * This activity represents the home screen of our app after the user
 * has successfully navigated through the login/registration stage.
 *
 * Most of the logic tying together the app is currently held in here.
 *
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.Professor;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.ProfessorDetail;

/**
 * The activity for our home page. This is where most of the
 * logic is tied together.
 */
public class HomeActivity extends AppCompatActivity implements
        ProfessorListFragment.OnListFragmentInteractionListener,
        ProfessorDetailFragment.OnListFragmentInteractionListener{

    /**
     * The list of professors fragment
     */
    private ProfessorListFragment rlf;

    /**
     * The search menu item.
     */
    MenuItem actionMenuItem;

    /**
     * The Drawer Layout member object.
     */
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*
            The navigationView is our navigation bar object.
            The logic for closing the drawer and acting upon
            a drawer item being clicked is handled here.
         */
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        //menuItem.setChecked(true);

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        onOptionsItemSelected(menuItem);
                        return true;
                    }
                });

        mDrawerLayout = findViewById(R.id.drawer_layout);

        /*
            Toolbar creation and creation of navigation bar's menu item
         */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        /*
            List of Professors creation
         */
        rlf = new ProfessorListFragment();
        if (findViewById(R.id.rating_fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rating_fragment_container, rlf)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rating, menu);
        getMenuInflater().inflate(R.menu.menu_settings, menu);
       // getMenuInflater().inflate(R.menu.drawer_view, menu);

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

        // Add am event listener to our searchView
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Everytime the submit button is clicked, show a toast message for now.
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "SUBMITTED", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }

            //Everytime the query text changes, re-filter our list of professors.
            @Override
            public boolean onQueryTextChange(String newText) {
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

        if (id == R.id.action_settings) {
            //if settings button was clicked
            Toast.makeText(this, "Settings coming soon", Toast.LENGTH_SHORT)
                    .show();
            return true;
        } else if (id == 16908332) {
            //hard coded - 16908332 is navigation drawer, R.id.homeAsUp wasn't
            //working for some reason
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_professors) {
            //if professors button from navdrawer was clicked
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStack();
                }
            }
            item.setChecked(true);
            return true;
        } else if (id == R.id.nav_savedratings) {
            //if saved ratings button from navdrawer was clicked
            Toast.makeText(this, "Saved Ratings coming soon", Toast.LENGTH_SHORT)
                    .show();
            return true;
        } else if (id == R.id.nav_settings) {
            //if settings button from navdrawer was clicked
            Toast.makeText(this, "Settings coming soon", Toast.LENGTH_SHORT)
                    .show();
            return true;
        } else if( id == R.id.nav_logout) {
            // if logout button from navdrawer was clicked. Store this information into the file
            // using SharedPreferences API. Then goto the MainActivity.
            SharedPreferences sharedPreferences =
                    getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN),false)
                    .commit();
//            Intent ratingIntent = new Intent(this, MainActivity.class);
//            startActivity(ratingIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Professor item) {
        //Create a new ProfessorDetailFragment if a user clicks on a Professor
        ProfessorDetailFragment professorDetailFragment = new ProfessorDetailFragment();
        Bundle args = new Bundle();

        //send the netID to the fragment
        args.putString("netid", item.getNetid());
        professorDetailFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rating_fragment_container, professorDetailFragment)
                .addToBackStack(null)
                .commit();
        actionMenuItem.collapseActionView();
    }


    @Override
    public void onListFragmentInteraction(ProfessorDetail item) {
        //handle user clicking on ProfessorDetail items here.
        //currently nothing here because we disabled clicking on items in ProfessorDetail.
    }
}
