package JUnit;

import org.junit.Before;
import org.junit.Test;

import GUI.*;
import Model.Bank;
import Model.Editor;
import Model.Entry;


/**
 * Tests of the AboutFrame class.
 * 
 * @author Paul Gray
 * @version November 2015
 */
public class Tester {
    
    // The test fixture. The Objects I will use in the tests.
    /** An AboutFrame to use in the tests. */
    private Editor myEditor;
    private Entry myEntry;

    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
        myEditor = new Editor();
        myEntry = new Entry("Title", "Type", "Description", "Contents");
    }

    /**
     * Test method for... Something?.
     */
    @Test
    public void test() {
    	myEditor.add(myEntry);
    	
    	
    }

}
