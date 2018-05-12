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
public class Rating implements Serializable{
    /** The name of Professor, FirstName*/
    public static final String FIRSTNAME ="FirstName";

    /** The name of Professor, LastName*/
    public static final String LASTNAME ="LastName";

    /** The NetID of Professor*/
    public static final String NETID ="NetID";

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
    private String mFirstName, mLastName, mNetID;

    /** The information about this professor.*/
    private int  mOverallQuality, mDifficulty,mTeachingAbility,mHelpOffered;

    /**
     * Create a constructor.
     * param mProfessorName Professor name.
     * param mWrittenReview Written review of professor
     * param mOverallQuality Overall quality of professor
     * param mDifficulty Difficulty of professor
     * param mTeachingAbility Teaching ability of professor
     * param mHelpOffered Help offered of professor
     */
    public Rating(String mFirstName, String mLastName, String mNetID){
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mNetID = mNetID;
//        this.mWrittenReview = mWrittenReview;
//        this.mOverallQuality = mOverallQuality;
//        this.mDifficulty = mDifficulty;
//        this.mTeachingAbility = mTeachingAbility;
//        this.mHelpOffered = mHelpOffered;
//        this.mEmailAddress = mEmailAddress;
    }

    /**
     * Set professor's first name.
     * @param professorName the name of professor
     */
    public void setFirstName(String professorName){
        mFirstName = professorName;
    }

    /**
     * Set professor's last name.
     * @param professorName the name of professor
     */
    public void setLastName(String professorName){
        mLastName = professorName;
    }

    /**
     * Get professor' first name.
     * @return professor's name
     */
    public String getFirstName(){
        return mFirstName;
    }

    /**
     * Get professor' last name.
     * @return professor's name
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
//                Rating rating = new Rating(obj.getString(NAME),obj.getString(WRITTENREVIEW),
////                        obj.getString(EMAILADDRESS), obj.getInt(OVERALLQUALITY),
////                        obj.getInt(DIFFICULTY), obj.getInt(TEACHINGABILITY),
////                        obj.getInt(HELPOFFERED));
                Rating rating = new Rating(obj.getString(FIRSTNAME),obj.getString(LASTNAME),
                        obj.getString(NETID));

                ratingList.add(rating);
            }
        }
        return ratingList;
    }


}
