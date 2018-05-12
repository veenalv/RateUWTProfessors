/**
 * TCSS 450 - Spring 2018 Team 8.
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Make the Rating class implement Serializable. This allows us to the pass
 * the object as a parameter.
 */
public class RatingDetail implements Serializable{

    /** The overall quality of professor. */
    public static final String OVERALLQUALITY ="OverallQuality";

    /** The difficulty of professor. */
    public static final String DIFFICULTY ="Difficulty";

    /**The teaching ability. */
    public static final String TEACHINGABILITY ="TeachingAbility";

    /**The help offered of professor. */
    public static final String HELPOFFERED="HelpOffered";

    /** The written review of professor.*/
    public static final String REVIEW="Review";

    public static final String UPVOTES = "Upvotes";

    public static final String DOWNVOTES = "Downvotes";

    private String mReview;
    /** The information about this professor.*/
    private int  mOverallQuality, mDifficulty,mTeachingAbility, mHelpOffered, mDownvotes, mUpvotes;

    /**
     * Create a constructor.
     * param mProfessorName Professor name.
     * param mWrittenReview Written review of professor
     * param mOverallQuality Overall quality of professor
     * param mDifficulty Difficulty of professor
     * param mTeachingAbility Teaching ability of professor
     * param mHelpOffered Help offered of professor
     */
    public RatingDetail(int mOverallQuality, int mDifficulty, int mTeachingAbility,
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
     * @param overallQuality overall quality
     */
    public void setOverallQuality(int overallQuality){
        mOverallQuality=overallQuality;
    }

    /**
     *  Set the overall quality of professor
     * @return overall quality
     */
    public int getOverallQuality(){
        return mOverallQuality;
    }

    /**
     * Set the difficulty of professor.
     * @param difficulty difficulty
     */
    public void setDifficulty(int difficulty){
        mDifficulty=difficulty;
    }

    /**
     * Get the difficulty of professor.
     * @return difficulty
     */
    public int getDifficulty(){
        return mDifficulty;
    }

    /**
     * Set teaching ability of professor.
     * @param teachingability teaching ability
     */
    public void setTeachingability(int teachingability){
        mTeachingAbility = teachingability;
    }

    /**
     * Get teaching ability of professor.
     * @return teaching ability
     */
    public int getTeachingAbility(){
        return mTeachingAbility;
    }


    /**
     * Set help Offered of professor.
     * @param helpOffered help offered
     */
    public void setHelpOffered(int helpOffered){
        mHelpOffered = helpOffered;
    }

    /**
     * Get help Offered of professor.
     * @return help offered
     */
    public int getHelpOffered(){
        return mHelpOffered;
    }

    public void setUpvote(int upvotes){
        mUpvotes=upvotes;
    }


    public int getUpvote(){
        return mUpvotes;
    }


    public void setDownvote(int downvotes){
        mDownvotes=downvotes;
    }


    public int getDownvote(){
        return mDownvotes;
    }


    public void setReview(String review){
        mReview=review;
    }

    /**
     *  Set the overall quality of professor
     * @return overall quality
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
    public static List<RatingDetail> parseRatingJSON(String ratingJSON) throws JSONException{
        List<RatingDetail> ratingList = new ArrayList<RatingDetail>();
        if(ratingJSON !=null){
            JSONArray arr = new JSONArray(ratingJSON);

            for(int i = 0; i< arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                RatingDetail ratingDetail = new RatingDetail(obj.getInt(OVERALLQUALITY),obj.getInt(DIFFICULTY),
                        obj.getInt(TEACHINGABILITY), obj.getInt(HELPOFFERED),
                        obj.getString(REVIEW), obj.getInt(UPVOTES),
                        obj.getInt(DOWNVOTES));

                ratingList.add(ratingDetail);
            }
        }
        return ratingList;
    }
}
