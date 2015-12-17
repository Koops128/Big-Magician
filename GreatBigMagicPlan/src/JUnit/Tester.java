package JUnit;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

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
     * Test method for getting the currently selected entry. 
     */
    @Test
    public void testGetCurrentEntry(){
    	myEditor.myCurrentEntry = myEntry;	
    	assertEquals(myEntry, myEditor.getCurrentEntry());
    }
    
    /**
     * Test method for setting the currently selected entry. 
     */
    @Test
    public void testSetCurrentEntry(){
    	myEditor.add(myEntry);
    	myEditor.setCurrentEntry(myEntry.getTitle());	
    	assertEquals(myEntry, myEditor.myCurrentEntry);
    }

    /**
     * Test method for removing an entry from the bank. 
     */
    @Test
    public void testRemove(){
    	myEditor.add(myEntry);
    	myEditor.setCurrentEntry(myEntry.getTitle());
    	myEditor.remove();
    	
    	assertEquals(null, myEditor.getCurrentEntry());
    }

}
