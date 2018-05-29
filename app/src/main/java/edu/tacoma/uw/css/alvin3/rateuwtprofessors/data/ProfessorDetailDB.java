/**
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.ProfessorDetail;

/**
 *
 * Database functionality for this professor detail
 *
 */
public class ProfessorDetailDB {

    /** The version of this database*/
    public static final int DB_VERSION = 1;

    /** The database name*/
    public  String DB_NAME;

    /** The professor Deatil database helper object*/
    private ProfessorDeatilDBHelper mProfessorDetailDBHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private  String PROFESSOR_DETAIL_TABLE ;
    private  String CREATE_PROFESSOR_DETAIL_SQL;
    private  String DROP_PROFESSOR_DETAIL_SQL;

    /**
     * Create a constructor for the ProfessorDeatilDB class
     *
     * @param context context
     */
    public ProfessorDetailDB(Context context, String netId) {
        PROFESSOR_DETAIL_TABLE = netId;
        DB_NAME = netId+".db";

        CREATE_PROFESSOR_DETAIL_SQL = "CREATE TABLE IF NOT EXISTS "+ netId +
                "(OverallQuality INTEGER, Difficulty INTEGER, TeachingAbility INTEGER," +
                " HelpOffered INTEGER, Review TEXT, Upvotes INTEGER, Downvotes INTEGER) ";

        DROP_PROFESSOR_DETAIL_SQL = "DROP TABLE IF EXISTS " + netId;

        mProfessorDetailDBHelper = new ProfessorDeatilDBHelper(context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase =  mProfessorDetailDBHelper.getWritableDatabase();
    }

    // The columns to return;
    // The columns for the WHERE clause
    // The values for the WHERE clause
    // don't group the rows
    // don't filter by row groups
    // The sort order
    public List<ProfessorDetail> getProfessorDeatil(){
        String[] columns = {"OverallQuality", "Difficulty", "TeachingAbility", "HelpOffered",
                "Review", "Upvotes", "Downvotes"};
        Cursor c = mSQLiteDatabase.query(PROFESSOR_DETAIL_TABLE, columns,
                null, null, null,
                null, null);
        c.moveToFirst();
        List<ProfessorDetail> list = new ArrayList<ProfessorDetail>();
        for (int i = 0; i<c.getCount(); i++){
            int overallQuality = c.getInt(0);
            int difficulty = c.getInt(1);
            int teachingAbility= c.getInt(2);
            int helpOffered= c.getInt(3);
            String Review= c.getString(4);
            int upvotes= c.getInt(5);
            int downvotes = c.getInt(6);

            ProfessorDetail professorDeatil  = new ProfessorDetail(overallQuality,  difficulty,
                    teachingAbility, helpOffered,  Review,  upvotes, downvotes);
            list.add(professorDeatil);
            c.moveToNext();
        }
        return list ;

    }


    /**
     * create an inner class called ProfessorDetailDBHelper that extends SQLiteOpenHelper class
     * and override the onCreate and onUpgrade methods.
     */
    class ProfessorDeatilDBHelper extends SQLiteOpenHelper {

        public ProfessorDeatilDBHelper(Context context, String name,
                                       SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);

        }

        /**
         * Create a professor detail table
         * @param sqLiteDatabase SQLite database
         */
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            sqLiteDatabase.execSQL(CREATE_PROFESSOR_DETAIL_SQL);
        }

        /**
         * Update the version of professor detail table.
         * @param sqLiteDatabase SQLite database
         * @param i perilous version number
         * @param i1 new version number
         */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
            sqLiteDatabase.execSQL(DROP_PROFESSOR_DETAIL_SQL);
            onCreate(sqLiteDatabase);
        }
    }


    /**
     * Insert the professor deatil  into the local sqlite table.
     * @param overallQuality overallQuality of this professor
     * @param difficulty difficulty of this professor
     * @param teachingAbility teaching ability of this professor
     * @param helpOffered help offered of this professor
     * @param review review of this professor
     * @param upvotes upvote of this professor
     * @param downvotes downvote of this professor
     * @return the row ID of the newly inserted row,, or -1 if an error occurred.
     */
    public boolean insertProfessorDeatil(int overallQuality, int difficulty, int teachingAbility,
                                   int helpOffered, String review, int upvotes, int downvotes  ){

        ContentValues contentValues = new ContentValues();
        contentValues.put("OverallQuality",overallQuality);
        contentValues.put("Difficulty", difficulty);
        contentValues.put("TeachingAbility",  teachingAbility);
        contentValues.put("HelpOffered",  helpOffered);
        contentValues.put("Review",  review);
        contentValues.put("Upvotes",  upvotes);
        contentValues.put("downvotes",  downvotes);

        long rowId = mSQLiteDatabase.insert(PROFESSOR_DETAIL_TABLE  ,null, contentValues);
        return rowId != -1;

    }

    /**
     * Close the SQlite database.
     */
    public void closeDB(){
        mSQLiteDatabase.close();
    }

    /**
     * Delete all the data from the COURSE_TABLE。
     */
    public void deleteProfessorDetail(){
        mSQLiteDatabase.delete(PROFESSOR_DETAIL_TABLE ,
                null, null);
    }

}
