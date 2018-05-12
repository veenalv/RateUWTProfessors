/**
 * TCSS 450 - Spring 2018 Team 8. Alvin, Hui, and Maksim.
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
 * @author Maksim
 * @auther Alvin
 * @auther Hui
 */
public class Rating implements Serializable{
    /** The name of Professor, FirstName,LastName.*/
    public static final String NAME ="Name";

    /** The overall quality of professor. */
    public static final String OVERALLQUALITY ="OverallQuality";

    /** The difficulty of professor. */
    public static final String DIFFICULTY ="Difficulty";

    /**The teaching ability. */
    public static final String TEACHINGABILITY ="TeachingAbility";

    /**The help offered of professor. */
    public static final String HELPOFFERED="HelpOffered";

    /** The written review of professor.*/
    public static final String WRITTENREVIEW="WrittenReview";

    /** The email address of professor. */
    public static final String EMAILADDRESS="EmailAddress";

    /**The information about this professor. */
    private String mProfessorName, mWrittenReview, mEmailAddress;

    /** The information about this professor.*/
    private int  mOverallQuality, mDifficulty,mTeachingAbility,mHelpOffered;

    /**
     * Create a constructor.
     * @param mProfessorName Professor name.
     * @param mWrittenReview Written review of professor
     * @param mOverallQuality Overall quality of professor
     * @param mDifficulty Difficulty of professor
     * @param mTeachingAbility Teaching ability of professor
     * @param mHelpOffered Help offered of professor
     */
    public Rating(String mProfessorName, String mWrittenReview, String mEmailAddress,
                  int mOverallQuality, int mDifficulty, int mTeachingAbility,
                  int mHelpOffered){
        this.mProfessorName = mProfessorName;
        this.mWrittenReview = mWrittenReview;
        this.mOverallQuality = mOverallQuality;
        this.mDifficulty = mDifficulty;
        this.mTeachingAbility = mTeachingAbility;
        this.mHelpOffered = mHelpOffered;
        this.mEmailAddress = mEmailAddress;
    }

    /**
     * Set professor's name.
     * @param professorName the name of professor
     */
    public void setProfessorName(String professorName){
        mProfessorName = professorName;
    }

    /**
     * Get professor'name.
     * @return professor's name
     */
    public String getProfessorName(){
        return mProfessorName;
    }

    /**
     * Set written review
     * @param writtenReview written review
     */
    public void setWrittenReview(String writtenReview){
        mWrittenReview = writtenReview;
    }

    /**
     * Get written review.
     * @return writtenReview
     */
    public String getWrittenReview(){
        return mWrittenReview;
    }

    /**
     * Set professor's email address.
     * @param emailAddress email address
     */
    public void setEmailAddress(String emailAddress){
        mEmailAddress=emailAddress;
    }

    /**
     * Get professor's email address.
     * @return email address
     */
    public String getEmailAddress(){
        return mEmailAddress;
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

    /**
     * Add a method to parse json String.
     * @param ratingJSON the list of rating in json format.
     * @return A list of rating
     * @throws JSONException
     */
    public static List<Rating> parseRatingJSON(String ratingJSON) throws JSONException{
        List<Rating> ratingList = new ArrayList<Rating>();
        if(ratingJSON !=null){
            JSONArray arr = new JSONArray(ratingJSON);

            for(int i = 0; i< arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Rating rating = new Rating(obj.getString(NAME),obj.getString(WRITTENREVIEW),
                        obj.getString(EMAILADDRESS), obj.getInt(OVERALLQUALITY),
                        obj.getInt(DIFFICULTY), obj.getInt(TEACHINGABILITY),
                        obj.getInt(HELPOFFERED));

                ratingList.add(rating);
            }
        }
        return ratingList;
    }
}
