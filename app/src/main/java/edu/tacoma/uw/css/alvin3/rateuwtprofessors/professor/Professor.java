/**
 * Represents an Professor object. This class is used to display
 * a list of professors on our HomeActivity. Professors will be displayed
 * by Last Name, First Name.
 *
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Make the Professor class implement Serializable. This allows us to the pass
 * the object as a parameter.
 */
public class Professor implements Serializable{

    /** The name of Professor, FirstName*/
    public static final String FIRSTNAME ="FirstName";

    /** The name of Professor, LastName*/
    public static final String LASTNAME ="LastName";

    /** The NetID of Professor*/
    public static final String NETID ="NetID";

    /**The information about this professor. */
    private String mFirstName, mLastName, mNetID;

//    /** The information about this professor.*/
//    private int  mOverallQuality, mDifficulty,mTeachingAbility,mHelpOffered;

    /**
     * Create a constructor.
     * param mProfessorName Professor name.
     * param mWrittenReview Written review of professor
     * param mOverallQuality Overall quality of professor
     * param mDifficulty Difficulty of professor
     * param mTeachingAbility Teaching ability of professor
     * param mHelpOffered Help offered of professor
     */
    public Professor(String mFirstName, String mLastName, String mNetID){
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mNetID = mNetID;
    }

    /**
     * Set professor's first name.
     * @param professorName the first name of professor
     */
    public void setFirstName(String professorName){
        mFirstName = professorName;
    }

    /**
     * Set professor's last name.
     * @param professorName the last name of professor
     */
    public void setLastName(String professorName){
        mLastName = professorName;
    }

    /**
     * Get professor' first name.
     * @return professor's first name
     */
    public String getFirstName(){
        return mFirstName;
    }

    /**
     * Get professor' last name.
     * @return professor's last name
     */
    public String getLastName(){
        return mLastName;
    }

    /**
     * Get professor' name.
     * @return professor's name
     */
    public String getProfessorName(){
        return mLastName + ", " + mFirstName;
    }


    /**
     * Get professor' Net Id.
     * @return professor's Net Id
     */
    public String getNetid(){
        return mNetID;
    }

    /**
     * Add a method to parse json String.
     * @param ratingJSON the list of rating in json format.
     * @return A list of rating
     * @throws JSONException
     */
    public static List<Professor> parseRatingJSON(String ratingJSON) throws JSONException{
        List<Professor> ratingList = new ArrayList<Professor>();
        if(ratingJSON !=null){
            JSONArray arr = new JSONArray(ratingJSON);

            for(int i = 0; i< arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Professor rating = new Professor(obj.getString(FIRSTNAME),obj.getString(LASTNAME),
                        obj.getString(NETID));

                ratingList.add(rating);
            }
        }
        return ratingList;
    }


}
