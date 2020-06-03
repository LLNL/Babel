package gov.llnl.babel.ast;

import junit.framework.TestCase;

/**
 * A JUnit test for <code>Version</code>.
 */

public class VersionTest extends TestCase {

	protected Version v40;

	protected Version v4000;

	protected Version v38;

	protected Version v4111;

	protected Version v4121;

	protected Version v4001;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(VersionTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		v40 = new Version("4.0");
		v4000 = new Version("4.0.0.0");
		v38 = new Version("3.8");
		v4111 = new Version("4.1.1.1");
		v4121 = new Version("4.1.2.1");
		v4001 = new Version("4.0.0.1");
	}

	public final void testCompareTo_0() {
		assertTrue(v40.compareTo(v4000) == 0);
	}

	public final void testCompareTo_1() {
		assertTrue(v40.compareTo(v38) > 0);
	}

	public final void testCompareTo_2() {
		assertTrue(v40.compareTo(v4111) < 0);
	}

	public final void testCompareTo_3() {
		assertTrue(v4000.compareTo(v38) > 0);
	}

	public final void testCompareTo_4() {
		assertTrue(v4000.compareTo(v4111) < 0);
	}

	public final void testEqualsObject() {
		assertTrue(v40.equals(v4000));
	}

	public final void testToString_0() {
		assertEquals(v40.toString(), "4.0");
	}

	public final void testToString_1() {
		assertEquals(v4000.toString(), "4.0.0.0");
	}

	public final void testToString_2() {
		assertEquals(v38.toString(), "3.8");
	}

	public final void testToString_3() {
		assertEquals(v4111.toString(), "4.1.1.1");
	}

	public final void testToString_4() {
		assertEquals(v4121.toString(), "4.1.2.1");
	}

	public final void testToString_5() {
		assertEquals(v4001.toString(), "4.0.0.1");
	}
}
