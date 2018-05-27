/**
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.data.ProfessorDB;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.Professor;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProfessorListFragment extends Fragment{

    private List<Professor> mRatingList;

    /**
     * Create a member variable for the RecyclerView so that we can access it in the
     * thread to load the data.
     */
    private RecyclerView mRecyclerView;

    /**
     * The query that the user types is the filter we put our
     * List of Professor's through.
     */
    private String mFilter = "";

    /**
     * The initial URL, sans the NetID of the Professor we want
     * details of.
     */
    private static final String RATING_URL =
            "http://tcssalvin.000webhostapp.com/android/list.php?cmd=professors";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private ProfessorDB mProfessorDB;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfessorListFragment() {
    }

    /**
     * Create a new instance of the ProfessorDetailFragment.
     * @param columnCount the column count
     * @return the new fragment
     */
    public static ProfessorListFragment newInstance(int columnCount) {
        ProfessorListFragment fragment = new ProfessorListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get the rating list
     * @return the list of ratings
     */
    public List getRatingList() {
        return mRatingList;
    }

    /**
     * Get the recycler view
     * @return the recycler view
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * Get the filter
     * @return the filter
     */
    public String getFilter() {
        return mFilter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professor_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                // recyclerView.setAdapter(new MyProfessorRecyclerViewAdapter(Professor.ITEMS, mListener));
                ProfessorAsyncTask ratingAsynTask = new ProfessorAsyncTask();
                ratingAsynTask.execute(new String[]{RATING_URL});
            }
            else {
                Toast.makeText(view.getContext(),
                        "No network connection available. Displaying locally stored data",
                        Toast.LENGTH_SHORT).show();
                if(mProfessorDB == null) {
                    mProfessorDB = new ProfessorDB(getActivity());
                }
                if(mRatingList == null){
                    mRatingList = mProfessorDB.getProfessor();
                }

                setAdapter(mRatingList);
            }


        }
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
        return view;
    }

    /**
     * Set the adapter or update the RecyclerView with new information.
     * @param list the list of Professors
     */
    protected void setAdapter(List<Professor> list) {
        Collections.sort(list, new CompareRatings());
        mRecyclerView.setAdapter(new MyProfessorRecyclerViewAdapter(list, mListener));
    }

    /**
     * Filter the list of Professors with our filterText.
     * @param newText the filter text
     * @return the filtered list of Professors with the filterText applied
     */
    protected List<Professor> filter(String newText) {
        List<Professor> localRatingList = new ArrayList<Professor>();
        for (int i = 0; i < mRatingList.size(); i++) {
            if (mRatingList.get(i).getProfessorName().toLowerCase().contains(newText.toLowerCase())) {
                localRatingList.add(mRatingList.get(i));
            }
        }
        return localRatingList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Professor item);
    }

    /**
     * Create a private class to setup asynchronous loading of the data.
     */
    private class ProfessorAsyncTask extends AsyncTask<String, Void, String> {
        private static final String TAG = "ProfessorListFragment";
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
                    response = "Unable to download the list of course, Reason: " +
                            e.getMessage();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG,"onPostExecute");
            if(result.startsWith("Unable to")){
                Toast.makeText(getActivity().getApplicationContext(),result,Toast.LENGTH_LONG)
                        .show();
                return;
            }
            try{
                mRatingList = Professor.parseRatingJSON(result);
            }
            catch(JSONException e){
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_LONG).show();
                return;
            }

            //Everything is good, show the list of rating
            if(!mRatingList.isEmpty()){
                if(mProfessorDB == null){
                    mProfessorDB = new ProfessorDB(getActivity());
                }

                // Delete old data so that you can refresh the local
                // database with the network data.
                mProfessorDB.deleteProfessor();

                // Also, add to the local database
                for(int i = 0; i<mRatingList.size(); i++){
                    Professor professor = mRatingList.get(i);
                    mProfessorDB.insertProfessor(professor.getFirstName(),
                            professor.getLastName(),
                            professor.getNetid());

                }

                setAdapter(mRatingList);
            }
        }
    }

    /**
     * An inner class used to compare Professors to one another
     * to sort them in our list. Currently sorts by Last name in
     * alphabetical order.
     */
    private class CompareRatings implements Comparator<Professor> {
        @Override
        public int compare(Professor o1, Professor o2) {
            return o1.getProfessorName().compareTo(o2.getProfessorName());
        }
    }
}
