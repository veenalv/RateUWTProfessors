/**
 * Represents an ProfessorDetail object. This class is used to display
 * the details of a Professor once a user clicks on a Professor.
 * This ProfessorDetail object will display a list of ratings associated
 * with the appropriate Professor.
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
public class ProfessorDetail implements Serializable{

    /** The overall quality of professor. */
    public static final String OVERALLQUALITY ="OverallQuality";

    /** The difficulty of professor. */
    public static final String DIFFICULTY ="Difficulty";

    /**The teaching ability. */
    public static final String TEACHINGABILITY ="TeachingAbility";

    /**The help offered of professor. */
    public static final String HELPOFFERED="HelpOffered";

    /**The written review of professor.*/
    public static final String REVIEW="Review";

    /**The up votes of professor.*/
    public static final String UPVOTES = "Upvotes";

    /**The down votes of professor.*/
    public static final String DOWNVOTES = "Downvotes";

    /**The review of professor.*/
    private String mReview;
    /** The information about this professor.*/
    private int  mOverallQuality, mDifficulty,mTeachingAbility, mHelpOffered, mDownvotes, mUpvotes;

    /**
     * Create a constructor.
     *
     * param mOverallQuality Overall quality of professor
     * param mDifficulty Difficulty of professor
     * param mTeachingAbility Teaching ability of professor
     * param mHelpOffered Help offered of professor
     * param mReview Review of professor
     * param mUpvotes Up votes of professor
     * param mDownvotes Douwn votes of professor
     */
    public ProfessorDetail(int mOverallQuality, int mDifficulty, int mTeachingAbility,
                           int mHelpOffered, String mReview, int mUpvotes, int mDownvotes){
        this.mOverallQuality = mOverallQuality;
        this.mDifficulty = mDifficulty;
        this.mTeachingAbility = mTeachingAbility;
        this.mHelpOffered = mHelpOffered;
        this.mReview = mReview;
        this.mUpvotes = mUpvotes;
        this.mDownvotes = mDownvotes;
    }

    /**
     * Set the overall quality of professor.
     * @param overallQuality overall quality of professor
     */
    public void setOverallQuality(int overallQuality){
        mOverallQuality=overallQuality;
    }

    /**
     * Get the overall quality of professor
     * @return overall quality of professor
     */
    public int getOverallQuality(){
        return mOverallQuality;
    }

    /**
     * Set the difficulty of professor.
     * @param difficulty difficulty of professor
     */
    public void setDifficulty(int difficulty){
        mDifficulty=difficulty;
    }

    /**
     * Get the difficulty of professor.
     * @return difficulty of professor
     */
    public int getDifficulty(){
        return mDifficulty;
    }

    /**
     * Set teaching ability of professor.
     * @param teachingability teaching ability of professoer
     */
    public void setTeachingability(int teachingability){
        mTeachingAbility = teachingability;
    }

    /**
     * Get teaching ability of professor.
     * @return teaching ability of professor
     */
    public int getTeachingAbility(){
        return mTeachingAbility;
    }


    /**
     * Set help Offered of professor.
     * @param helpOffered help offered of professor
     */
    public void setHelpOffered(int helpOffered){
        mHelpOffered = helpOffered;
    }

    /**
     * Get help Offered of professor.
     * @return help offered of professor
     */
    public int getHelpOffered(){
        return mHelpOffered;
    }

    /**
     * Set up vote of professor.
     * @return up vote of professor
     */
    public void setUpvote(int upvotes){
        mUpvotes=upvotes;
    }

    /**
     * Get up vote of professor.
     * @return up vote of professor
     */
    public int getUpvote(){
        return mUpvotes;
    }

    /**
     * Set down vote of professor.
     * @return down vote of professor
     */
    public void setDownvote(int downvotes){
        mDownvotes=downvotes;
    }


    /**
     * Get down vote of professor.
     * @return down vote of professor
     */
    public int getDownvote(){
        return mDownvotes;
    }


    /**
     * Set Review of professor.
     * @return review of professor
     */
    public void setReview(String review){
        mReview=review;
    }

    /**
     * Get the review of professor
     * @return review of professor
     */
    public String getReview(){
        return mReview;
    }

    /**
     * Add a method to parse json String.
     * @param ratingJSON the list of rating in json format.
     * @return A list of rating
     * @throws JSONException
     */
    public static List<ProfessorDetail> parseRatingJSON(String ratingJSON) throws JSONException{
        List<ProfessorDetail> ratingList = new ArrayList<ProfessorDetail>();
        if(ratingJSON !=null){
            JSONArray arr = new JSONArray(ratingJSON);

            for(int i = 0; i< arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                ProfessorDetail professorDetail = new ProfessorDetail(obj.getInt(OVERALLQUALITY),obj.getInt(DIFFICULTY),
                        obj.getInt(TEACHINGABILITY), obj.getInt(HELPOFFERED),
                        obj.getString(REVIEW), obj.getInt(UPVOTES),
                        obj.getInt(DOWNVOTES));

                ratingList.add(professorDetail);
            }
        }
        return ratingList;
    }
}
