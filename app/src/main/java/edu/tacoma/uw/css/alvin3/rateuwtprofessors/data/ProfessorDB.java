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

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.R;
import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.Professor;

/**
 * Database functionality for the professor
 */

public class ProfessorDB {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME="Professor.db";
    private ProfessorDBHelper mProfessorDBHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private static final String PROFESSOR_TABLE = "Professor";

    /**
     * Create a constructor for the ProfessorDB class
     *
     * @param context
     */
    public ProfessorDB(Context context) {
        mProfessorDBHelper = new ProfessorDBHelper(context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mProfessorDBHelper.getWritableDatabase();
    }

    // The columns to return;
    // The columns for the WHERE clause
    // The values for the WHERE clause
    // don't group the rows
    // don't filter by row groups
    // The sort order
    public List<Professor> getProfessor(){
        String[] columns = {"FirstName", "LastName", "NetID"};
        Cursor c = mSQLiteDatabase.query(PROFESSOR_TABLE, columns,
                null, null, null,
                null, null);
        c.moveToFirst();
        List<Professor> list = new ArrayList<Professor>();
        for (int i = 0; i<c.getCount(); i++){
            String firstName = c.getString(0);
            String lastName = c.getString(1);
            String netId = c.getString(2);
            Professor professor = new Professor(firstName,  lastName, netId);
            list.add(professor);
            c.moveToNext();
        }
        return list;


    }


    /**
     * create an inner class called ProfessorDBHelper that extends SQLiteOpenHelper class
     * and override the onCreate and onUpgrade methods.
     */
    class ProfessorDBHelper extends SQLiteOpenHelper {
        private final String CREATE_PROFESSOR_SQL;
        private final String DROP_PROFESSOR_SQL;

        public ProfessorDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
            CREATE_PROFESSOR_SQL = context.getString((R.string.CREATE_PROFESSOR_SQL));
            DROP_PROFESSOR_SQL = context.getString(R.string.DROP_PROFESSOR_SQL);
        }

        /**
         * Create Professor table.
         * @param sqLiteDatabase SQLite database
         */
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            sqLiteDatabase.execSQL(CREATE_PROFESSOR_SQL);
        }

        /**
         * Update the version of professor table
         * @param sqLiteDatabase SQLite database
         * @param i perilous version number
         * @param i1 new version number
         */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
            sqLiteDatabase.execSQL(DROP_PROFESSOR_SQL);
            onCreate(sqLiteDatabase);
        }
    }


    /**
     * Insert the professor into the local sqlite table.
     * Return true  if successful, false otherwise.
     * @param firstName first name of professor
     * @param lastName last name of professor
     * @param netId net Id of professor
     * @return the row ID of the newly inserted row,, or -1 if an error occurred.
     */
    public boolean insertProfessor(String firstName, String lastName, String netId){

        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName",firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("NetID",  netId);

        long rowId = mSQLiteDatabase.insert(PROFESSOR_TABLE ,null, contentValues);
        return rowId != -1;

    }

    /**
     * Close the SQLite database.
     */
    public void closeDB(){
        mSQLiteDatabase.close();
    }

    /**
     * Delete all the data from the PROFESSOR_TABLE。
     */
    public void deleteProfessor(){
        mSQLiteDatabase.delete(PROFESSOR_TABLE ,
                null, null);
    }
}
