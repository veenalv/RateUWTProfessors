/**
 *
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.tacoma.uw.css.alvin3.rateuwtprofessors.professor.Professor;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 *  Test fo the professor class.
 */

public class ProfessorTest {
    /** The professor */
    private Professor mProfessor;

    /** The first name of menaks.*/
    private String mFirstName = "Menaka";

    /**The last name of menaks*/
    private String mLastName = "Abraham";

    /** the net id of menaka. */
    private String mNetId="mmuppa";



    @Before
    // This method run before EACH test method.
    public void setUp(){
        mProfessor = new Professor(mFirstName, mLastName,mNetId);
    }

    /**
     * Test the getFirstName method.
     */
    @Test
    public void testGetFirstName(){
        assertEquals(mFirstName,mProfessor.getFirstName());
    }

    @Test
    public void testGetLastName(){
        assertEquals(mLastName, mProfessor.getLastName());
    }

    @Test
    public void testGetNetId(){
        assertEquals(mNetId,mProfessor.getNetid());
    }

    @Test
    public void testSetFirstName(){
        mProfessor.setFirstName("menaka");
        assertEquals("menaka1",mProfessor.getFirstName());

    }

    @Test
    public void testSetLastName(){
        mProfessor.setLastName("abraham");
        assertEquals("abraham",mProfessor.getLastName());

    }

    @Test
    public void testParseRatingJSON(){

        String input = "[{\"FirstName\":\"Menaka\",\"LastName\":\"Abraham\",\"NetID\":\"mmuppa\"}," +
                "{\"FirstName\":\"Muhammad Aurangzeb\",\"LastName\":\"Ahmad\",\"NetID\":\"maahmad\"}]";

        List<Professor> expectedList = new ArrayList<Professor>();
        expectedList.add(new Professor("Menaka", "Abraham", "mmuppa"));
        expectedList.add(new Professor("Muhammad Aurangzeb", "Ahmad", "maahmad"));

        try{
            List<Professor> genList = Professor.parseRatingJSON(input);

            assertEquals(expectedList, genList);

        } catch (JSONException e){

        }



    }

}
