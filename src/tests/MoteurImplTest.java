/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import receiver.Moteur;
import receiver.MoteurImpl;

/**
 * @author VinYarD
 *
 */
class MoteurImplTest {

	private Moteur engine;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		this.engine = new MoteurImpl();
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#getClipboard()}.
	 * 
	 * @throws IllegalSelectionException
	 */

	@Test
	void testGetClipboard() {
		String buf = "Hello World !";
		this.engine.inserer(buf);
		assertEquals(this.engine.getBuffer(), buf);

		this.engine.setDot(0).moveDot(buf.length());
		;

		assertEquals(this.engine.getSelection(), buf);

		this.engine.copier();
		assertEquals(this.engine.getSelection(), this.engine.getClipboard());
		this.engine.coller();
		assertEquals(this.engine.getBuffer(), buf);
		this.engine.coller();
		assertEquals(this.engine.getBuffer(), buf + buf);
		this.engine.copier();
		assertEquals(this.engine.getClipboard(), buf);
		this.engine.setDot(0).moveDot(this.engine.getBuffer().length());
		this.engine.couper();
		assertEquals(this.engine.getBuffer(), "");
		assertEquals(this.engine.getClipboard(), buf + buf);

		buf = "assertEquals(this.engine.getBuffer(), buf + buf);";
		this.engine.inserer(buf);
		this.engine.setDot(0);
		for (int i = 1; i < buf.length(); i++) {
			this.engine.moveDot(i);
			this.engine.copier();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			assertEquals(this.engine.getClipboard(), buf.substring(0, i));
		}

		this.engine.setDot(0);
		for (int i = buf.length(); i > 0; i--) {
			this.engine.moveDot(i);
			this.engine.copier();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			assertEquals(this.engine.getClipboard(), buf.substring(0, i));
		}

		for (int i = 0; i < buf.length() - 1; i++) {
			this.engine.setDot(i).moveDot(i + 1);
			this.engine.copier();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			assertEquals(this.engine.getClipboard(), buf.substring(i, i + 1));
		}
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#getBuffer()}.
	 */
	@Test
	void testGetBuffer() {
		StringBuffer buf = new StringBuffer(" Test method for {@link receiver.MoteurImpl#getBuffer()}");
		for (int i = 0; i < buf.length(); i++) {
			assertEquals(this.engine.getBuffer(), buf.substring(0, i));
			this.engine.inserer(Character.toString(buf.charAt(i)));
		}
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#getSelection()}.
	 * 
	 * @throws IllegalSelectionException
	 */
	@Test
	void testGetSelection() {
		String buf = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...";
		this.engine.inserer(buf);
		this.engine.setDot(0).moveDot(buf.length());
		assertEquals(buf, this.engine.getSelection());
		this.engine.setDot(0);
		for (int i = 0; i < buf.length(); i++) {
			this.engine.moveDot(i);
			assertEquals(buf.substring(0, i), this.engine.getSelection());
		}
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#MoteurImpl()}.
	 */
	@Test
	void testMoteurImpl() {
		assertEquals(this.engine.getDot(), 0);
		assertEquals(this.engine.getMark(), 0);
		assertEquals(this.engine.getSelection(), "");
		assertEquals(this.engine.getBuffer(), "");
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#setBuffer(java.lang.String)}.
	 */
	@Test
	void testSetBuffer() {
		this.engine.setBuffer("");
		assertEquals(this.engine.getBuffer(), "");
		String buf = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.";
		this.engine.setBuffer(buf);
		assertEquals(this.engine.getBuffer(), buf);

		this.engine.setBuffer("");
		assertTrue(this.engine.getBuffer().isEmpty());
		buf = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.";
		this.engine.setBuffer(buf);
		assertEquals(buf, this.engine.getBuffer());
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#copier()}.
	 * 
	 * @throws IllegalSelectionException
	 */
	@Test
	void testCopier() {
		String buf = "Et harum quidem rerum facilis est et expedita distinctio.";
		this.engine.inserer(buf);

		this.engine.setDot(0);
		for (int i = 1; i < buf.length(); i++) {
			this.engine.moveDot(i);
			this.engine.copier();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			assertEquals(this.engine.getClipboard(), buf.substring(0, i));
		}

		this.engine.setDot(0);
		for (int i = buf.length(); i > 0; i--) {
			this.engine.moveDot(i);
			this.engine.copier();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			assertEquals(this.engine.getClipboard(), buf.substring(0, i));
		}

		for (int i = 0; i < buf.length() - 1; i++) {
			this.engine.setDot(i).moveDot(i + 1);
			this.engine.copier();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			assertEquals(this.engine.getClipboard(), buf.substring(i, i + 1));
		}
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#delete()}.
	 * 
	 * @throws IllegalSelectionException
	 */
	@Test
	void testDelete() {
		String buf = "Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.";
		this.engine.inserer(buf);
		this.engine.setDot(0);
		for (int i = 0; i < buf.length(); i++) {
			this.engine.moveDot(1);
			this.engine.delete();
			assertEquals(this.engine.getBuffer().length(), buf.length() - i - 1);
		}

		assertEquals(this.engine.getBuffer(), "");

		this.engine.inserer(buf);
		this.engine.setDot(0);
		while (buf.length() > 0) {
			this.engine.moveDot(1);
			this.engine.delete();
			buf = buf.substring(1);
			assertEquals(this.engine.getBuffer(), buf);
		}

		assertEquals(this.engine.getBuffer(), "");
		assertEquals(buf, "");
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#couper()}.
	 * 
	 * @throws IllegalSelectionException
	 */
	@Test
	void testCouper() {
		String buf = "Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.";
		this.engine.inserer(buf);

		this.engine.setDot(0);
		for (int i = 0; i < buf.length(); i++) {
			this.engine.moveDot(1);
			this.engine.couper();
			assertEquals(this.engine.getBuffer().length(), buf.length() - i - 1);
			assertEquals(this.engine.getClipboard(), buf.substring(i, i + 1));
		}

		assertEquals(this.engine.getBuffer(), "");

		this.engine.setBuffer(buf);
		for (int i = 0; i < buf.length(); i++) {
			this.engine.setDot(i).moveDot(i + 1);
			this.engine.couper();
			assertEquals(this.engine.getClipboard(), buf.substring(i, i + 1));
			assertEquals(this.engine.getBuffer(), buf.substring(0, i) + buf.substring(i + 1));
			this.engine.coller();
			assertEquals(this.engine.getBuffer(), buf);
		}

		/*
		 * On coupe tout les caractere 1 par 1 et on les colle a la fin, on doit
		 * retrouver la meme chaine de caractère a la fin
		 */
		this.engine.setBuffer(buf);
		for (int i = 0; i < buf.length(); i++) {
			this.engine.setDot(0).moveDot(1);
			this.engine.couper();
			this.engine.setDot(buf.length() - 1); // TODO -1 ??
			this.engine.coller();
		}

		assertEquals(this.engine.getBuffer(), buf);

	}

	/**
	 * Test method for {@link receiver.MoteurImpl#coller()}.
	 * 
	 * @throws IllegalSelectionException
	 */
	@Test
	void testColler() {
		String buf = "Hello World !";

		this.engine.inserer(buf);
		assertEquals(this.engine.getBuffer(), buf);

		this.engine.setDot(0).moveDot(buf.length());

		assertEquals(this.engine.getSelection(), buf);

		this.engine.copier();
		assertEquals(this.engine.getSelection(), buf);

		this.engine.coller();
		assertEquals(this.engine.getBuffer(), buf);
		assertEquals(this.engine.getClipboard(), buf);

		this.engine.coller();
		assertEquals(this.engine.getBuffer(), buf + buf);
		assertEquals(this.engine.getClipboard(), buf);

		this.engine.copier();
		assertEquals(this.engine.getClipboard(), buf);
		this.engine.setDot(0).moveDot(this.engine.getBuffer().length());
		assertEquals(this.engine.getSelection(), this.engine.getBuffer());
		this.engine.couper();
		assertEquals(this.engine.getSelection(), "");
		assertEquals(this.engine.getBuffer(), "");
		assertEquals(this.engine.getClipboard(), buf + buf);
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#inserer(java.lang.String)}.
	 */
	@Test
	void testInserer() {
		String buf = " Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur?";
		this.engine.inserer(buf);
		assertEquals(this.engine.getBuffer(), buf);
		assertEquals(this.engine.getDot(), buf.length());
		assertEquals(this.engine.getMark(), buf.length());
		assertEquals(this.engine.getClipboard(), "");
		assertEquals(this.engine.getSelection(), "");
		for (Character c : buf.toCharArray()) {
			this.engine.inserer(Character.toString(c));
		}
		assertEquals(this.engine.getBuffer(), buf + buf);

		this.engine.setDot(0).moveDot(buf.length() * 2);

		buf = "replace";
		this.engine.inserer(buf);
		assertEquals(this.engine.getBuffer(), buf);
		this.engine.inserer(" ");
		this.engine.inserer(buf);
		assertEquals(this.engine.getBuffer(), buf + " " + buf);
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#selectionner(int, int)}.
	 * 
	 * @throws IllegalSelectionException
	 */

	@Test()
	void testSelectionner() {

		for (int begin = -1; begin < 1; begin++) {
			this.engine.setDot(begin);
			if (begin != 0) {
				assertThrows(java.lang.StringIndexOutOfBoundsException.class, () -> {
					this.engine.inserer(".");
				});
			} else {
				assertTrue(this.engine.getSelection().isEmpty());
			}
			for (int end = -1; end < 1; end++) {
				this.engine.moveDot(end);
				if (end != 0 || begin != 0) {
					assertThrows(java.lang.StringIndexOutOfBoundsException.class, () -> {
						this.engine.getSelection();
					});
				} else {
					assertTrue(this.engine.getSelection().isEmpty());
				}
			}
		}

		String buf = "Hello World !";
		this.engine.setDot(0);
		this.engine.inserer(buf);

		for (int i = 0; i < buf.length(); i++) {
			this.engine.setDot(i);
			assertTrue(this.engine.getSelection().isEmpty());
		}

		this.engine.setDot(-1);
		assertThrows(java.lang.StringIndexOutOfBoundsException.class, () -> {
			this.engine.inserer(".");
		});
 
		this.engine.setDot(buf.length() + 1);
		assertThrows(java.lang.StringIndexOutOfBoundsException.class, () -> {
			this.engine.inserer(".");
		});

		this.engine.setDot(0).moveDot(buf.length());
		assertEquals(this.engine.getSelection(), buf);

		this.engine.setDot(1).moveDot(0);
		assertEquals(this.engine.getSelection(), buf.substring(0, 1));

		this.engine.setDot(buf.length()).moveDot(0);
		assertEquals(this.engine.getSelection(), buf);

		this.engine.setDot(0).moveDot(buf.length() + 1);
		assertThrows(java.lang.StringIndexOutOfBoundsException.class, () -> {
			this.engine.getSelection();
		});
	}

	/**
	 * 
	 * 
	 * /** Test method for {@link receiver.MoteurImpl#getDot()}.
	 */
	@Test
	void testDot() {
		String buf = "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.";
		for (int i = 0; i < buf.length(); i++) {
			assertEquals(this.engine.getDot(), i);
			this.engine.inserer(Character.toString(buf.charAt(i)));
			assertEquals(this.engine.getDot(), i + 1);
		}
	}

	/**
	 * Test method for {@link receiver.MoteurImpl#getEndSelection()}.
	 * 
	 * @throws IllegalSelectionException
	 */
	@Test
	void testGetEndSelection() {
		String buf = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.";
		for (int i = 0; i < buf.length(); i++) {
			this.engine.inserer(Character.toString(buf.charAt(i)));
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertEquals(this.engine.getMark(), i + 1);
		}

		this.engine.setDot(0);
		for (int i = 0; i < buf.length(); i++) {
			this.engine.moveDot(i);
			assertEquals(this.engine.getDot(), i);
			assertEquals(this.engine.getMark(), 0);
			assertEquals(this.engine.getSelection(), buf.substring(0, i));
		}
		this.engine.delete();
		assertEquals(this.engine.getMark(), 0);
	}
}
