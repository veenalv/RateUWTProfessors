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

import static org.junit.Assert.assertEquals;

/**
 *  Test for the professor class.
 */
public class ProfessorTest {
    /** The professor */
    private Professor mProfessor;

    /** The first name of menaks.*/
    private String mFirstName = "Menaka";

    /** The last name of menaks.*/
    private String mLastName = "Abraham";

    /** The net id of menaka. */
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

    /**
     * Test the getLastName method.
     */
    @Test
    public void testGetLastName(){
        assertEquals(mLastName, mProfessor.getLastName());
    }

    /**
     * Test the getNetId method.
     */
    @Test
    public void testGetNetId(){
        assertEquals(mNetId,mProfessor.getNetid());
    }

    /**
     * Test the setFirstName method.
     */
    @Test
    public void testSetFirstName(){
        mProfessor.setFirstName("menaka");
        assertEquals("menaka",mProfessor.getFirstName());

    }

    /**
     * Test the sesLastName method.
     */
    @Test
    public void testSetLastName(){
        mProfessor.setLastName("abraham");
        assertEquals("abraham",mProfessor.getLastName());

    }

    /**
     * Test the getProfessorName method.
     */
    @Test
    public void testGetProfessorName(){
        assertEquals(mLastName + ", " + mFirstName, mProfessor.getProfessorName());
    }

//    /**
//     * Test the parseRatingJSON method.
//     */
//    @Test
//    public void testParseRatingJSON(){
//
//        String input = "[{\"FirstName\":\"Menaka\",\"LastName\":\"Abraham\",\"NetID\":\"mmuppa\"}," +
//                "{\"FirstName\":\"Muhammad Aurangzeb\",\"LastName\":\"Ahmad\",\"NetID\":\"maahmad\"}]";
//
//        List<Professor> expectedList = new ArrayList<Professor>();
//        expectedList.add(new Professor("Menaka", "Abraham", "mmuppa"));
//        expectedList.add(new Professor("Muhammad Aurangzeb", "Ahmad", "maahmad"));
//
//        try{
//            List<Professor> genList = Professor.parseRatingJSON(input);
//
//            assertEquals(expectedList, genList);
//
//        } catch (JSONException e){
//
//        }
//    }

}
