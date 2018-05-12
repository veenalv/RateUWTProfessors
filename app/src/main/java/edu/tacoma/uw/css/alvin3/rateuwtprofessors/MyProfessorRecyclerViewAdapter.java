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
import android.widget.TextView;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.ProfessorListFragment.OnListFragmentInteractionListener;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.Professor;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Professor} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyProfessorRecyclerViewAdapter extends RecyclerView.Adapter<MyProfessorRecyclerViewAdapter.ViewHolder> {

    /**
     * List of Professors.
     */
    private final List<Professor> mValues;
    private final OnListFragmentInteractionListener mListener;

    /**
     * Constructor for the view adapter which updates the view that the user sees
     * everytime setAdapter() is called.
     * @param items
     * @param listener
     */
    public MyProfessorRecyclerViewAdapter(List<Professor> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_rating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mProfessorView.setText(mValues.get(position).getProfessorName());
        //holder.mContentView.setText(mValues.get(position).getOverallQuality());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
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
        public final TextView mProfessorView;
        public final TextView mContentView;
        public Professor mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProfessorView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
