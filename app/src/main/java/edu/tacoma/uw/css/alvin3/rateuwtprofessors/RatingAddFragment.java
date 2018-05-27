package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RatingAddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RatingAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingAddFragment extends Fragment {

    //The default upvote and downvotes for a rating
    private static final int DEFAULT_UPVOTE = 0;
    private static final int DEFAULT_DOWNVOTE = 0;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String mNetId;

    private EditText mOverallQuality;
    private EditText mDifficulty;
    private EditText mTeachingAbility;
    private EditText mHelpOffered;
    private EditText mWrittenReview;

    private OnFragmentInteractionListener mListener;

    private final static String RATING_ADD_URL
            = "http://tcssalvin.000webhostapp.com/android/addRating.php?";

    public RatingAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RatingAddFragment.
     */
    public static RatingAddFragment newInstance(String param1, String param2) {
        RatingAddFragment fragment = new RatingAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //receive netID here
        mNetId = getArguments().getString("netid");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rating_add, container, false);
        mOverallQuality = (EditText) v.findViewById(R.id.overallquality);
        mDifficulty = (EditText) v.findViewById(R.id.difficulty);
        mTeachingAbility = (EditText) v.findViewById(R.id.teachingability);
        mHelpOffered = (EditText) v.findViewById(R.id.helpoffered);
        mWrittenReview = (EditText) v.findViewById(R.id.writtenreview);

        //hide the floating action button
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        //add a listener to the add button
        Button addRatingButton = (Button) v.findViewById(R.id.btnCourse);
        addRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addRating(url);
            }
        });


        return v;
    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(RATING_ADD_URL);

        try {

            sb.append("netid=" + mNetId);
            String quality = mOverallQuality.getText().toString();
            sb.append("&quality=");
            sb.append(URLEncoder.encode(quality, "UTF-8"));

            String difficulty = mDifficulty.getText().toString();
            sb.append("&difficulty=");
            sb.append(URLEncoder.encode(difficulty, "UTF-8"));


            String teachingAbility = mTeachingAbility.getText().toString();
            sb.append("&teaching=");
            sb.append(URLEncoder.encode(teachingAbility, "UTF-8"));

            String helpOffered = mHelpOffered.getText().toString();
            sb.append("&help=");
            sb.append(URLEncoder.encode(helpOffered, "UTF-8"));

            String writtenReview = mWrittenReview.getText().toString();
            sb.append("&review=");
            sb.append(URLEncoder.encode(writtenReview, "UTF-8"));

            sb.append("&upvote=" + DEFAULT_UPVOTE);
            sb.append("&downvote=" + DEFAULT_DOWNVOTE);
            Log.i("MY_URL", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.addRating(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void addRating(String url);
    }
}