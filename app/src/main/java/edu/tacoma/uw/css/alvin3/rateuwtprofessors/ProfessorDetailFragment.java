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
import java.util.List;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.data.ProfessorDetailDB;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.ProfessorDetail;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProfessorDetailFragment extends Fragment{

    /**
     * List of ProfessorDetails. NOTE: This could later be just one Object.
     */
    private List<ProfessorDetail> mRatingList;

    /**
     * Create a member variable for the RecyclerView so that we can access it in the
     * thread to load the data.
     */
    private RecyclerView mRecyclerView;

    /**
     * The NetID of the Professor we want details of.
     */
    private String mNetId;

    /**
     * The initial URL, sans the NetID of the Professor we want
     * details of.
     */
    private static final String RATING_URL =
            "http://tcssalvin.000webhostapp.com/android/list.php?cmd=";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ProfessorDetailDB mProfessorDeatilDB;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfessorDetailFragment() {
    }

    /**
     * Create a new instance of the ProfessorDetailFragment.
     * @param columnCount the column count
     * @return the new fragment
     */
    public static ProfessorDetailFragment newInstance(int columnCount) {
        ProfessorDetailFragment fragment = new ProfessorDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get professor' Net Id.
     * @return professor's Net Id
     */
    public String getNetId(){
        return mNetId;
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
        //receive netID here
        mNetId =getArguments().getString("netid");
        // Set the adapter, or update the RecyclerView.
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
                //Fetch the JSON object for our ProfessorDetails.
                ratingAsynTask.execute(new String[]{RATING_URL + mNetId});
            }
            else {
                Toast.makeText(view.getContext(),
                        "No network connection available. Displaying locally stored data",
                        Toast.LENGTH_SHORT).show();
                if(mProfessorDeatilDB == null) {
                    mProfessorDeatilDB = new ProfessorDetailDB(getActivity(),mNetId);
                }
                if(mRatingList == null){
                    mRatingList = mProfessorDeatilDB.getProfessorDetail();
                }

                setAdapter(mRatingList);
            }



        }
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.show();


        return view;
    }

    /**
     * Update the adapter or the view.
     * @param list the list of ProfessorDetail
     */
    protected void setAdapter(List<ProfessorDetail> list) {
        mRecyclerView.setAdapter(new MyProfessorDetailRecyclerViewAdapter(list, mListener));
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
        void onListFragmentInteraction(ProfessorDetail item);
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
                    response = "Unable to download the list of professors, Reason: " +
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
                mRatingList = ProfessorDetail.parseRatingJSON(result);
            }
            catch(JSONException e){
                //Show a message here if the Professor has no ratings.
                Toast.makeText(getActivity().getApplicationContext(), "Professor has no ratings!",
                        Toast.LENGTH_LONG).show();
                return;
            }

            //Everything is good, show the list of rating
            if(!mRatingList.isEmpty()){
                if(mProfessorDeatilDB== null){
                    mProfessorDeatilDB = new ProfessorDetailDB(getActivity(),mNetId);
                    Log.i("netId = ", mNetId);
                }

               mProfessorDeatilDB.deleteProfessorDetail();

                for(int i = 0; i<mRatingList.size(); i++){
                    ProfessorDetail professorDetail = mRatingList.get(i);
                    mProfessorDeatilDB.insertProfessorDetail(professorDetail.getOverallQuality(),
                            professorDetail.getDifficulty(), professorDetail.getTeachingAbility(),
                            professorDetail.getHelpOffered(), professorDetail.getReview(),
                            professorDetail.getUpvote(), professorDetail.getDownvote());
                }
                setAdapter(mRatingList);
            }
        }
    }
}
