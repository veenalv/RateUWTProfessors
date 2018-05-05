package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating.Rating;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RatingDeatilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RatingDeatilFragment extends Fragment {
    public final static String RATING_ITEM_SELECTED ="rating_selected";
    private TextView mProfessorNameTextView;
    private TextView mQvrallQualityTextView;
    private TextView mDifficutyTextView;
    private TextView mTeachingAblityTextView;
    private TextView mHelpOfferredTextView;
    private TextView mWrittenReviewTextView;
    //private TextView mEmailAddressTextView;

    private OnFragmentInteractionListener mListener;



    public RatingDeatilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating_deatil, container, false);
        mProfessorNameTextView = (TextView) view.findViewById(R.id.rating_professor_name);
        mQvrallQualityTextView = (TextView) view.findViewById(R.id.rating_overall_quality);
        mDifficutyTextView = (TextView) view.findViewById(R.id.rating_difficuty);
        mTeachingAblityTextView = (TextView) view.findViewById(R.id.rating_teaching_ablity);
        mWrittenReviewTextView = (TextView) view.findViewById(R.id.rating_written_review);
        mHelpOfferredTextView = (TextView) view.findViewById(R.id.rating_help_offerred);
        //mEmailAddressTextView = (TextView) view.findViewById(R.id.rating_professor_eamiladdress);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateView(Rating rating){
        if(rating != null){
            mProfessorNameTextView.setText(rating.getmProfessorName());
            mQvrallQualityTextView.setText("Overall Quality:  "+rating.getmOverallQuality());
            mDifficutyTextView.setText("Difficulty:   "+rating.getmDifficuty());
            mTeachingAblityTextView.setText("Teaching Ablity:   "+rating.getmTeachingAblity());
            mWrittenReviewTextView.setText("Written Review:   "+rating.getmWrittenReview());
            mHelpOfferredTextView.setText("Help Offerred:  "+rating.getmHelpOfferred());
            // mEmailAddressTextView.setText(rating.getmEmailAddress());


        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Bundle args = getArguments();
        if(args != null){
            //Set course information based on argument passed
            updateView((Rating)args.getSerializable(RATING_ITEM_SELECTED));
        }
    }
}
