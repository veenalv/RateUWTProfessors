/**
 * TCSS 450 - Spring 2018 Team 8. Alvin, Hui, and Maksim.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * @author Maksim
 * @auther Alvin
 * @auther Hui
 */
public class RatingDeatilFragment extends Fragment {
    public final static String RATING_ITEM_SELECTED ="rating_selected";
    private TextView mProfessorNameTextView;
    private TextView mOverallQualityTextView;
    private TextView mDifficultyTextView;
    private TextView mTeachingAbilityTextView;
    private TextView mHelpOfferedTextView;
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
        mOverallQualityTextView = (TextView) view.findViewById(R.id.rating_overall_quality);
        mDifficultyTextView = (TextView) view.findViewById(R.id.rating_difficuty);
        mTeachingAbilityTextView = (TextView) view.findViewById(R.id.rating_teaching_ablity);
        mWrittenReviewTextView = (TextView) view.findViewById(R.id.rating_written_review);
        mHelpOfferedTextView = (TextView) view.findViewById(R.id.rating_help_offerred);
        //mEmailAddressTextView = (TextView) view.findViewById(R.id.rating_professor_eamiladdress);
        return view;
    }


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
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Update the view.
     * @param rating rating object
     */
    public void updateView(Rating rating){
        if(rating != null){
            mProfessorNameTextView.setText("                "+rating.getProfessorName());
            mOverallQualityTextView.setText("  Overall Quality:     "+ String.valueOf(rating.getOverallQuality()));
            mDifficultyTextView.setText("  Difficulty:               "+String.valueOf(rating.getDifficulty()));
            mTeachingAbilityTextView.setText("  Teaching Ability:    "+String.valueOf(rating.getTeachingAbility()));
            mWrittenReviewTextView.setText("  Written Review:     "+rating.getWrittenReview());
            mHelpOfferedTextView.setText("  Help Offered:        "+String.valueOf(rating.getHelpOffered()));
            // mEmailAddressTextView.setText(rating.getEmailAddress());


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
