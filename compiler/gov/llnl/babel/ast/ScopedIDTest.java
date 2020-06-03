package gov.llnl.babel.ast;

import junit.framework.TestCase;

/**
 * A JUnit test for <code>ScopedID</code>.
 */
public class ScopedIDTest extends TestCase {

	protected ScopedID test_this;

	protected ScopedID test_that;

	protected ScopedID test_this_too;

	protected ScopedID test;

	protected ScopedID _test_this;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ScopedIDTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		test_this = new ScopedID("test.this");
		test_that = new ScopedID("test.that");
		test_this_too = new ScopedID("test.this.too");
		test = new ScopedID("test");
		_test_this = new ScopedID(".test.this");
	}

	public final void testCompareTo_0() {
		assertTrue(test_this.compareTo(test_that) > 0);
	}

	public final void testCompareTo_1() {
		assertTrue(test_this.compareTo(test_this_too) < 0);
	}

	public final void testCompareTo_2() {
		assertTrue(test_this.compareTo(test) > 0);
	}

	public final void testCompareTo_3() {
		assertTrue(test_this.compareTo(_test_this) > 0);
	}

	public final void testEqualsObject() {
		assertTrue(test_this.equals(new ScopedID("test.this.")));
	}

	public final void testToString_0() {
		assertEquals(test_this.toString(), "test.this");
	}

	public final void testToString_1() {
		assertEquals(test_that.toString(), "test.that");
	}

	public final void testToString_2() {
		assertEquals(test_this_too.toString(), "test.this.too");
	}

	public final void testToString_3() {
		assertEquals(_test_this.toString(), ".test.this");
	}
}
