package edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Make the Rating class implement Serializable. This allows us to the pass
 * the object as a parameter.
 */
public class Rating implements Serializable{
    public static final String NAME ="Name";
    public static final String OVRALLQUALITY ="OvrallQuality";
    public static final String DIFFICUTY ="Difficuty";
    public static final String TEACHINGABLITY ="TeachingAblity";
    public static final String HELPOFFERRED="HelpOfferred";
    public static final String WRITTENREVIEW="WrittenReview";
    public static final String EMAILADDRESS="EmailAddress";
    private String mProfessorName, mWrittenReview, mEmailAddress;
           ;
    private int  mOverallQuality, mDifficuty,mTeachingAblity,mHelpOfferred;

    /**
     * Create a constructor.
     * @param mProfessorName
     * @param mWrittenReview
     * @param mOverallQuality
     * @param mDifficuty
     * @param mTeachingAblity
     * @param mHelpOfferred
     */
    public Rating(String mProfessorName, String mWrittenReview, String mEmailAddress,
                  int mOverallQuality, int mDifficuty, int mTeachingAblity,
                  int mHelpOfferred){
        this.mProfessorName = mProfessorName;
        this.mWrittenReview = mWrittenReview;
        this.mOverallQuality = mOverallQuality;
        this.mDifficuty = mDifficuty;
        this.mTeachingAblity = mTeachingAblity;
        this.mHelpOfferred = mHelpOfferred;
        this.mEmailAddress = mEmailAddress;

    }

    public void setmProfessorName(String professorName){
        mProfessorName = professorName;
    }
    public String getmProfessorName(){
        return mProfessorName;
    }


    public void setmWrittenReview(String writtenReview){
        mWrittenReview = writtenReview;
    }
    public String getmWrittenReview(){
        return mWrittenReview;
    }

    public void setmEmailAddress(String emailAddress){
        mEmailAddress=emailAddress;
    }
    public String getmEmailAddress(){
        return mEmailAddress;
    }

    public void setmOverallQuality(int overallQuality){
        mOverallQuality=overallQuality;
    }
    public int getmOverallQuality(){
        return mOverallQuality;
    }


    public void setmDifficuty(int difficuty){
        mDifficuty=difficuty;
    }
    public int getmDifficuty(){
        return mDifficuty;
    }


    public void setmTeachingablity(int teachingablity){
        mTeachingAblity = teachingablity;
    }
    public int getmTeachingAblity(){
        return mTeachingAblity;
    }


    public void setmHelpofferred(int helpofferred){
        mHelpOfferred = helpofferred;
    }
    public int getmHelpOfferred(){
        return mHelpOfferred;
    }

    /**
     * Add a method to parse json String.
     * @param ratingJSON
     * @return
     * @throws JSONException
     */
    public static List<Rating> parseRatingJSON(String ratingJSON) throws JSONException{
        List<Rating> ratingList = new ArrayList<Rating>();
        if(ratingJSON !=null){
            JSONArray arr = new JSONArray(ratingJSON);

            for(int i = 0; i< arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Rating rating = new Rating(obj.getString(NAME),obj.getString(WRITTENREVIEW),
                        obj.getString(EMAILADDRESS), obj.getInt(OVRALLQUALITY),
                        obj.getInt(DIFFICUTY), obj.getInt(TEACHINGABLITY),
                        obj.getInt(HELPOFFERRED));

                ratingList.add(rating);
            }
        }
        return ratingList;
    }


}
