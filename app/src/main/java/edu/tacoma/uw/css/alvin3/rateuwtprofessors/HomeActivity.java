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
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.Professor;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.ProfessorDetail;

/**
 * The activity for our home page. This is where most of the
 * logic is tied together.
 */
public class HomeActivity extends AppCompatActivity implements
        ProfessorListFragment.OnListFragmentInteractionListener,
        RatingAddFragment.OnFragmentInteractionListener,
        ProfessorAddFragment.OnFragmentInteractionListener,
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

    /**
     * Default sort method.
     * 1 is for first name a-z
     * 2 is for last name a-z
     */
    private int mSort = 2;

    /**
     * Create a floating action button here and add an onClickListener() to it. This
     * onClickListener() detects which fragment (the detail, or list fragment) is displayed and
     * will launch the ProfessorAddFragment if the list fragment is displayed, or will launch
     * the RatingAddFragment ifthe detail fragment is displayed.
     *
     * We will pass the NetID of the professors to the appropriate fragments for logic processing.
     * The navigation view is started here as well as the tool bar.
     * Finally, the list of professors will be populated here.
     *
     * @param savedInstanceState the bundle state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(this,"successful login",Toast.LENGTH_SHORT).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Find the current fragment
                ProfessorDetailFragment currentDetailFragment = (ProfessorDetailFragment) getSupportFragmentManager().findFragmentByTag("currentDetailFragment");
                ProfessorListFragment currentListFragment = (ProfessorListFragment) getSupportFragmentManager().findFragmentByTag("currentListFragment");
                Bundle args = new Bundle();
                //if the current fragment displayed in the rating_fragment_container
                //is our detail fragment, float action button will add a rating
                if (currentDetailFragment != null && currentDetailFragment.isVisible()) {
                    args.putString("netid", currentDetailFragment.getNetId());

                    //Send the netId into the ratingAddFragment
                    RatingAddFragment ratingAddFragment = new RatingAddFragment();
                    ratingAddFragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.rating_fragment_container, ratingAddFragment)
                            .addToBackStack(null)
                            .commit();
                } else if (currentListFragment != null && currentListFragment.isVisible()) {
                    //if the current fragment displayed in the rating_fragment_container
                    //is our list fragment, float action button will add a professor

                    //args.putString("netid", currentDetailFragment.getNetId());

                    //Send the netId into the ratingAddFragment
                    ProfessorAddFragment professorAddFragment = new ProfessorAddFragment();
                    professorAddFragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.rating_fragment_container, professorAddFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

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
            Bundle args = new Bundle();
            args.putInt("sort", mSort);

            //Send the sort into the ProfessorListFragment
            rlf.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rating_fragment_container, rlf, "currentListFragment")
                    .commit();
        }

    }

    /**
     * Inflate the menus in the HomeActivity.
     * @param menu menu
     * @return true/ false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rating, menu);
        getMenuInflater().inflate(R.menu.menu_sort, menu);
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
                rlf.setAdapter(rlf.getRatingList(), mSort);
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                getSupportFragmentManager().popBackStack();
                rlf.setAdapter(rlf.getRatingList(), mSort);
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
            /**
             *  Everytime the submit button is clicked, show a toast message for now.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "SUBMITTED", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }

            /**
             * Every time the text changes as we type, we filter the list of professors
             * and set the recyclerview adapter to reflect this change in our UI.
             * @param newText the new text that the user entered
             * @return false
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                rlf.setAdapter(rlf.filter(newText), mSort);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle the interactions from the user on the MenuItem objects.
     * @param item item
     * @return true/false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.drop1) {
            //sort by first name a-z
            mSort = 1;
            rlf.setAdapter(rlf.getRatingList(), mSort);
            return true;
        } else if (id == R.id.drop2) {
            //sort by last name a-z
            mSort = 2;
            rlf.setAdapter(rlf.getRatingList(), mSort);
            return true;
        } else if (id == R.id.action_settings) {
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

    /**
     * Overridden method from the ProfessorListFragment which handles the interactions
     * of each Professor item in the RecyclerView.
     *
     * @param item the Professor item
     */
    @Override
    public void onListFragmentInteraction(Professor item) {
        //Create a new ProfessorDetailFragment if a user clicks on a Professor
        ProfessorDetailFragment professorDetailFragment = new ProfessorDetailFragment();
        Bundle args = new Bundle();

        //send the netID to the fragment
        args.putString("netid", item.getNetid());
        professorDetailFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rating_fragment_container, professorDetailFragment, "currentDetailFragment")
                .addToBackStack(null)
                .commit();
        actionMenuItem.collapseActionView();
    }

    /**
     * Overridden method from the ProfessorDetailFragment which handles the interactions
     * of each Professor item in the RecyclerView.
     *
     * @param item the Professor item
     */
    @Override
    public void onListFragmentInteraction(ProfessorDetail item) {
        //handle user clicking on ProfessorDetail items here.
        //currently nothing here because we disabled clicking on items in ProfessorDetail.

        // make the dialog here
        ShareProfessorDialogFragment shareProfessorsDialog = new ShareProfessorDialogFragment();

        String messageBody = String.format("Overall Quality: %d\nDifficulty: %d\nTeaching Ability: %d\nHelp Offered: %d\n\nRating:\n %s\n",
                        item.getOverallQuality(), item.getDifficulty(), item.getTeachingAbility(), item.getHelpOffered(), item.getReview());
        shareProfessorsDialog.setMessage(messageBody);
        shareProfessorsDialog.show(getSupportFragmentManager(), "launch");

    }

    @Override
    /**
     * Over ridden methods from RatingAddFragment
     * Add a rating with the given URL.
     * @param url the url
     */
    public void addRating(String url) {

        AddRatingProfessorTask task = new AddRatingProfessorTask();
        task.execute(new String[]{url.toString()});

        // Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
    }


    @Override
    /**
     * Overridden methods from ProfessorAddFragment
     * Add a professor with the given URL.
     * @param url the url
     */
    public void addProfessor(String url) {
        AddRatingProfessorTask task = new AddRatingProfessorTask();
        task.execute(new String[]{url.toString()});

        // Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * An inner class to add a rating or professor into our database.
     */
    private class AddRatingProfessorTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add , Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result the result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Successfully added!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
