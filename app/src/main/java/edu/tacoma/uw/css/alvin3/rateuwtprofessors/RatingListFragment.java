/**
 * TCSS 450 - Spring 2018 Team 8. Alvin, Hui, and Maksim.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating.Rating;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 * @author Maksim
 * @auther Alvin
 * @auther Hui
 */
public class RatingListFragment extends Fragment{

    protected List<Rating> mRatingList;

    //Create a member variable for the RecyclerView so that we can access it in the
    //thread to load the data.
    protected RecyclerView mRecyclerView;

    protected String filter = "";

    private static final String RATING_URL =
            "http://cssgate.insttech.washington.edu/~caih6/professorList.php?cmd=ratings";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RatingListFragment() {
    }

    public static RatingListFragment newInstance(int columnCount) {
        RatingListFragment fragment = new RatingListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_rating_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // recyclerView.setAdapter(new MyRatingRecyclerViewAdapter(Rating.ITEMS, mListener));
            RatingAsyncTask ratingAsynTask = new RatingAsyncTask();
            ratingAsynTask.execute(new String[]{RATING_URL});
        }
        return view;
    }

    protected void setAdapter(List<Rating> list) {
        Collections.sort(list, new CompareRatings());
        mRecyclerView.setAdapter(new MyRatingRecyclerViewAdapter(list, mListener));
    }

    protected List<Rating> filter(String newText) {
        List<Rating> localRatingList = new ArrayList<Rating>();
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
        void onListFragmentInteraction(Rating item);
    }

    /**
     * Create a private class to setup asynchronous loading of the data.
     */
    private class RatingAsyncTask extends AsyncTask<String, Void, String> {
        private static final String TAG = "RatingListFragment";
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
                mRatingList = Rating.parseRatingJSON(result);
            }
            catch(JSONException e){
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_LONG).show();
                return;
            }

            //Everything is good, show the list of rating
            if(!mRatingList.isEmpty()){
                setAdapter(mRatingList);
            }
        }
    }

    private class CompareRatings implements Comparator<Rating> {
        @Override
        public int compare(Rating o1, Rating o2) {
            return o1.getProfessorName().compareTo(o2.getProfessorName());
        }
    }
}
