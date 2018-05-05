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
    private String mProfessorName, mWrittenReview, mEmailAddress,
            mOverallQuality, mDifficuty,mTeachingAblity,mHelpOfferred;

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
                  String mOverallQuality, String mDifficuty, String mTeachingAblity,
                  String mHelpOfferred){
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

    public void setmOverallQuality(String overallQuality){
        mOverallQuality=overallQuality;
    }
    public String getmOverallQuality(){
        return mOverallQuality;
    }


    public void setmDifficuty(String difficuty){
        mDifficuty=difficuty;
    }
    public String getmDifficuty(){
        return mDifficuty;
    }


    public void setmTeachingablity(String teachingablity){
        mTeachingAblity = teachingablity;
    }
    public String getmTeachingAblity(){
        return mTeachingAblity;
    }


    public void setmHelpofferred(String helpofferred){
        mHelpOfferred = helpofferred;
    }
    public String getmHelpOfferred(){
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
                        obj.getString(EMAILADDRESS), obj.getString(OVRALLQUALITY),
                        obj.getString(DIFFICUTY), obj.getString(TEACHINGABLITY),
                        obj.getString(HELPOFFERRED));

                ratingList.add(rating);
            }
        }
        return ratingList;
    }


}
