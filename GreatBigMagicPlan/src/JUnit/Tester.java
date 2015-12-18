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
    	assertEquals(myEntry.getTitle(), myEditor.myCurrentEntry.getTitle());
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

    /**
     * Test method for setting the current entry to null and seeing if it will return null. 
     * @author Matthew Cles
     */
    @Test
    public void testNullEntry(){
        String s = null;
        myEditor.setCurrentEntry(s);
        assertEquals(null, myEditor.getCurrentEntry());
    }

    /**
     * Test method for editing the current entry. 
     * @author Matthew Cles
     */
    @Test
    public void testEditEntry(){
        String newTitle = "Did it work?";
        String newType = "Test";
        String newDesc = "I think so...";
        String newContent = "Yup, yup it did";
        String oldTitle = myEntry.getTitle();
        String oldType = myEntry.getType();
        String oldDesc = myEntry.getDescription();
        String oldContent = myEntry.getContent();
        myEditor.add(myEntry);
        myEditor.setCurrentEntry(myEntry.getTitle());
        myEditor.changeEntry(newTitle, newType, newDesc, newContent);
        myEditor.setCurrentEntry(newTitle);
        assertEquals(myEditor.getCurrentEntry().getTitle(), newTitle);
        assertEquals(myEditor.getCurrentEntry().getType(), newType);
        assertEquals(myEditor.getCurrentEntry().getDescription(), newDesc);
        assertEquals(myEditor.getCurrentEntry().getContent(), newContent);
        myEditor.changeEntry(oldTitle, oldType, oldDesc, oldContent);
        myEditor.remove();
    }

    /**
     * Test method for editing the current entry when null. 
     * @author Matthew Cles
     */
    @Test(expected = NullPointerException.class)
    public void testEditNullEntry(){
        String s = null;
        String newTitle = "Did it work?";
        String newType = "Test";
        String newDesc = "I think so...";
        String newContent = "Yup, yup it did";
        myEditor.setCurrentEntry(s);
        myEditor.changeEntry(newTitle, newType, newDesc, newContent);
    }
}
