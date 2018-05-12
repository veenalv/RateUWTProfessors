package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.RatingDeatilFragment.OnListFragmentInteractionListener;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating.Rating;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Rating} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRatingDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyRatingDetailRecyclerViewAdapter.ViewHolder> {

    private final List<Rating> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyRatingDetailRecyclerViewAdapter(List<Rating> items, OnListFragmentInteractionListener listener) {
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

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mProfessorView;
        public final TextView mContentView;
        public Rating mItem;

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
