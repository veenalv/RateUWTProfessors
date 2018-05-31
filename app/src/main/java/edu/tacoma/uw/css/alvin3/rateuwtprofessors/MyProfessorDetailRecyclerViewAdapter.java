/**
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */

package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.ProfessorDetailFragment.OnListFragmentInteractionListener;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.ProfessorDetail;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ProfessorDetail} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyProfessorDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyProfessorDetailRecyclerViewAdapter.ViewHolder> {

    /**
     * List of ProfessorDetails.
     */
    private final List<ProfessorDetail> mValues;
    private final OnListFragmentInteractionListener mListener;

    /**
     * Constructor for the view adapter which updates the view that the user sees
     * everytime setAdapter() is called.
     * @param items
     * @param listener
     */
    public MyProfessorDetailRecyclerViewAdapter(List<ProfessorDetail> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Set the content to contain the details of the Rating.
     * @param holder the ViewHolder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Button upvote = new Button(holder.mContentView.getContext());
        upvote.setText("Upvote");
        upvote.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.mItem = mValues.get(position);
        holder.mRatingDetailView.setText(mValues.get(position).getReview());
        holder.mContentView.setText(
                "Overall Quality: " + "\n\t" + mValues.get(position).getOverallQuality()
                + "\nDifficulty: " + "\n\t" + mValues.get(position).getDifficulty()
                + "\nTeaching Ability: " + "\n\t" + mValues.get(position).getTeachingAbility()
                + "\nHelp Offered: " + "\n\t" + mValues.get(position).getHelpOffered()
                + "\nUpvotes/Downvotes: " + "\n\t" + mValues.get(position).getUpvote()
                        + "/" + mValues.get(position).getDownvote()
        );

        //COMMENT OUT here to disable the user from being able to click on
        //stuff in ProfessorDetail view.
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Inner class which is the view holder. Displays the content.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mRatingDetailView;
        public final TextView mContentView;
        public ProfessorDetail mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRatingDetailView = (TextView) view.findViewById(R.id.content);
            mContentView = (TextView) view.findViewById(R.id.item_number);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
