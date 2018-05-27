package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfessorAddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfessorAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfessorAddFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText mProfessorFirstName;
    private EditText mProfessorLastName;
    private EditText mProfessorNetID;

    private OnFragmentInteractionListener mListener;

    private final static String PROFESSOR_ADD_URL
            = "http://tcssalvin.000webhostapp.com/android/addProfessor.php?";

    public ProfessorAddFragment() {
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
    public static ProfessorAddFragment newInstance(String param1, String param2) {
        ProfessorAddFragment fragment = new ProfessorAddFragment();
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
//        mNetId = getArguments().getString("netid");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_professor_add, container, false);
        mProfessorFirstName = (EditText) v.findViewById(R.id.professorfirstname);
        mProfessorLastName = (EditText) v.findViewById(R.id.professorlastname);
        mProfessorNetID = (EditText) v.findViewById(R.id.professornetid);

        //hide the floating action button
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        //add a listener to the add button
        Button addProfessorButton = (Button) v.findViewById(R.id.btnaddprofessor);
        addProfessorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildCourseURL(v);
                mListener.addProfessor(url);
            }
        });


        return v;
    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(PROFESSOR_ADD_URL);

        try {

            String netId = mProfessorNetID.getText().toString();
            sb.append("netid=");
            sb.append(URLEncoder.encode(netId, "UTF-8"));

            String firstName = mProfessorFirstName.getText().toString();
            sb.append("&first=");
            sb.append(URLEncoder.encode(firstName, "UTF-8"));


            String lastName = mProfessorLastName.getText().toString();
            sb.append("&last=");
            sb.append(URLEncoder.encode(lastName, "UTF-8"));
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
            mListener.addProfessor(uri);
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
        void addProfessor(String url);
    }
}
