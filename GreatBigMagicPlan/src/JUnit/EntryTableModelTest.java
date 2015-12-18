package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Model.Entry;
import Model.EntryTableModel;

/**
 * Test class for {@link Model.EntryTableModel}.
 * @author Melinda Robertson
 * @version 20151217
 */
public class EntryTableModelTest {
	/**
	 * Test table model.
	 */
	private EntryTableModel model;
	/**
	 * Test entry.
	 */
	private Entry test;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		model = new EntryTableModel();
		test = new Entry("title","type","desc","content");
		model.add(test);
	}

	/**
	 * Test method for {@link Model.EntryTableModel#add(Model.Entry)}.
	 */
	@Test
	public void testAdd() {
		model.add(new Entry("a","type","desc","content"));
		System.out.println(model.getValueAt(1, 0));
		assertEquals("Entry not added correctly.","a",model.getValueAt(1,0));
		model.remove("a");
	}

	/**
	 * Test method for {@link Model.EntryTableModel#addInOrder(Model.Entry)}.
	 */
	@Test
	public void testAddInOrder() {
		model.addInOrder(new Entry("s","type","desc","content"));
		model.addInOrder(new Entry("t","type","desc","content"));
		model.addInOrder(new Entry("a","type","desc","content"));
		model.addInOrder(new Entry("m","type","desc","content"));
		
		assertTrue(((String) model.getValueAt(0, 0)).compareTo(
				((String) model.getValueAt(0, 1))) < 0);
		model.remove("a");
		model.remove("s");
		model.remove("t");
		model.remove("m");
	}

	/**
	 * Test method for {@link Model.EntryTableModel#remove(Model.Entry)}.
	 */
	@Test
	public void testRemoveEntry() {
		model.remove(test);
		assertTrue(model.get("title") == null);
		model.add(test);
	}

	/**
	 * Test method for {@link Model.EntryTableModel#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(!model.isEmpty());
		model.remove(test);
		assertTrue(model.isEmpty());
		model.add(test);
	}

	/**
	 * Test method for {@link Model.EntryTableModel#remove(java.lang.String)}.
	 */
	@Test
	public void testRemoveString() {
		model.remove("title");
		assertTrue(model.get("title") == null);
		model.add(test);
	}

	/**
	 * Test method for {@link Model.EntryTableModel#contains(Model.Entry)}.
	 */
	@Test
	public void testContainsEntry() {
		assertTrue(model.contains(test));
	}

	/**
	 * Test method for {@link Model.EntryTableModel#contains(java.lang.String)}.
	 */
	@Test
	public void testContainsString() {
		assertTrue(model.contains("title"));
	}

	/**
	 * Test method for {@link Model.EntryTableModel#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		assertEquals("testGet() Entry does not exist.", test, model.get("title"));
	}

	/**
	 * Test method for {@link Model.EntryTableModel#getColumnCount()}.
	 */
	@Test
	public void testGetColumnCount() {
		assertEquals("testGetColumnCount() Incorrect number of columns.",
				3, model.getColumnCount());
	}

	/**
	 * Test method for {@link Model.EntryTableModel#getColumnName(int)}.
	 */
	@Test
	public void testGetColumnNameInt() {
		assertEquals("testGetColumnNameInt() Incorrect column name.",
				"Title",model.getColumnName(0));
		assertEquals("testGetColumnNameInt() Incorrect column name.",
				"Type", model.getColumnName(1));
		assertEquals("testGetColumnNameInt() Incorrect column name.",
				"Description", model.getColumnName(2));
	}

	/**
	 * Test method for {@link Model.EntryTableModel#getRowCount()}.
	 */
	@Test
	public void testGetRowCount() {
		assertEquals("testGetRowCount() Incorrect number of rows,",
				1, model.getRowCount());
	}

	/**
	 * Test method for {@link Model.EntryTableModel#getValueAt(int, int)}.
	 */
	@Test
	public void testGetValueAt() {
		assertEquals("testGetValueAt() Wrong value.", test.getTitle(),
				model.getValueAt(0, 0));
	}

}
