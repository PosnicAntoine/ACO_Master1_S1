/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commands.Command;
import commands.com.CopyCommand;
import commands.com.CutCommand;
import commands.com.DeleteCommand;
import commands.com.InsertCommand;
import commands.com.PasteCommand;
import commands.selection.CursorCommand;
import commands.selection.MoveCursorCommand;
import commands.selection.MoveSelectionCommand;
import commands.selection.SelectAllCommand;
import commands.selection.SelectionCommand;
import invoker.Invoker;
import receiver.Moteur;
import receiver.MoteurImpl;

/**
 * @author VinYarD
 *
 */
class CommandTest_v1 {

	private Moteur engine;

	// com
	private PasteCommand paste;
	private CopyCommand copy;
	private CutCommand cut;
	private InsertCommand insert;
	private DeleteCommand delete;

	// selection
	private CursorCommand cursor;
	private SelectionCommand selection;
	private MoveCursorCommand moveCursor;
	private MoveSelectionCommand moveSelection;
	private SelectAllCommand selectAll;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception { 
		Invoker inv = new Invoker() {

			@Override
			public void addCommand(String keyword, Command cmd) {
			}

			@Override
			public String askInsertion() {
				throw new RuntimeException();
			}

			@Override
			public int askValue() {
				throw new RuntimeException();
			}

		};

		this.engine = new MoteurImpl();

		// com
		this.paste = new PasteCommand(this.engine);
		this.copy = new CopyCommand(this.engine);
		this.cut = new CutCommand(this.engine);
		this.insert = new InsertCommand(this.engine, inv);
		this.delete = new DeleteCommand(this.engine);

		// selection
		this.cursor = new CursorCommand(this.engine, inv);
		this.selection = new SelectionCommand(this.engine, inv);
		this.moveCursor = new MoveCursorCommand(this.engine, inv);
		this.moveSelection = new MoveSelectionCommand(this.engine, inv);
		this.selectAll = new SelectAllCommand(this.engine);
	}

	/**
	 * Test command for {@link commands.selection.CursorCommand}.
	 */

	@Test
	void testCursorCommand() {
		String buf = "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat";
		this.engine.setBuffer(buf);
		/*
		 * initialisation à 0
		 */
		assertEquals(this.engine.getDot(), 0);
		assertTrue(this.engine.getSelection().isEmpty());

		/*
		 * si execute lève une exception, rien ne change sur le moteur !
		 */
		String s = "";
		s.substring(0, 0);
		this.cursor.execute();
		assertEquals(this.engine.getDot(), 0);
		assertTrue(this.engine.getSelection().isEmpty());
		this.cursor.execute(buf.length());
		assertEquals(this.engine.getDot(), buf.length());
		assertTrue(this.engine.getSelection().isEmpty());
		this.cursor.execute();
		assertEquals(this.engine.getDot(), buf.length());
		assertTrue(this.engine.getSelection().isEmpty());

		this.cursor.execute(0);
		this.selection.execute(buf.length());
		assertEquals(this.engine.getSelection(), buf);
		assertEquals(this.engine.getBuffer(), buf);
		assertTrue(this.engine.getClipboard().isEmpty());
		assertEquals(this.engine.getDot(), buf.length());
		assertEquals(this.engine.getMark(), 0);

		this.cursor.execute(buf.length());
		this.selection.execute(0);
		assertEquals(this.engine.getSelection(), buf);
		assertEquals(this.engine.getBuffer(), buf);
		assertTrue(this.engine.getClipboard().isEmpty());
		assertEquals(this.engine.getDot(), 0);
		assertEquals(this.engine.getMark(), buf.length());

		for (int i = 0; i < buf.length(); i++) {
			this.cursor.execute(i);
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getBuffer(), buf);
			assertTrue(this.engine.getClipboard().isEmpty());
			assertEquals(this.engine.getDot(), this.engine.getMark());
		}

		this.engine.setBuffer("");

		for (int dot = -2; dot < 2; dot++) {
			this.cursor.execute(dot);
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getDot(), 0);
		}

		{
			int i = 0;
			for (Character c : buf.toCharArray()) {
				this.insert.execute(Character.toString(c));
				assertEquals(this.engine.getDot(), ++i);
				assertEquals(this.engine.getMark(), this.engine.getDot());
			}
		}
		assertEquals(this.engine.getBuffer(), buf);
	}
	
	/**
	 * Test command for {@link commands.selection.MoveCursorCommand}.
	 */

	@Test
	void testMoveCursorCommand() {
		String buf = "Unchecked exception thrown when a relative get operation reaches the source buffer's limit.";
		
		this.insert.execute(buf);
		assertEquals(this.engine.getDot(), buf.length());
		for(int i = buf.length(); i > 0; i--) {
			assertEquals(this.engine.getDot(), i);
			this.moveCursor.execute(-1);
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getBuffer(), buf);
		}
		
		assertEquals(this.engine.getDot(), 0);
		
		for(int i = 0; i < buf.length(); i++) {
			assertEquals(this.engine.getDot(), i);
			this.moveCursor.execute(1);
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getBuffer(), buf);
		}
		
		assertEquals(this.engine.getDot(), buf.length());
		
		
		this.moveCursor.execute(buf.length() * 2);
		
		assertEquals(this.engine.getDot(), buf.length());
		
		this.moveCursor.execute(buf.length() * -2);
		
		assertEquals(this.engine.getDot(), 0);
		
		this.selectAll.execute();
		assertFalse(this.engine.getSelection().isEmpty());
		this.moveCursor.execute(-1);
		assertTrue(this.engine.getSelection().isEmpty());
		assertEquals(this.engine.getDot(), buf.length() - 1);
		
		this.selectAll.execute();
		assertFalse(this.engine.getSelection().isEmpty());
		this.moveCursor.execute(1);
		assertTrue(this.engine.getSelection().isEmpty());
		assertEquals(this.engine.getDot(), buf.length());
		
		this.cursor.execute(0);
		assertEquals(this.engine.getDot(), 0);
		
		this.moveCursor.execute();
		
		assertEquals(this.engine.getDot(), 0);
	}
	
	/**
	 * Test command for {@link commands.selection.SelectionCommand}.
	 */

	@Test
	void testSelectionCommand() {
		String buf = "For further API reference and developer documentation, see Java SE Documentation. That documentation contains more detailed, developer-targeted descriptions, with conceptual overviews, definitions of terms, workarounds, and working code examples.";
		this.insert.execute(buf);
		
		assertEquals(this.engine.getDot(), buf.length());
		
		this.selection.execute(0);
		
		assertEquals(this.engine.getSelection(), buf);
		assertEquals(this.engine.getDot(), 0);
		assertEquals(this.engine.getMark(), buf.length());
		
		this.selection.execute(-1);
		
		assertEquals(this.engine.getSelection(), buf);
		assertEquals(this.engine.getDot(), 0);
		assertEquals(this.engine.getMark(), buf.length());
		
		for(int i = 0; i < buf.length(); i++) {
			this.selection.execute(i);
			assertEquals(this.engine.getDot(), i);
			assertEquals(this.engine.getMark(), buf.length());
			assertEquals(this.engine.getSelection(), buf.substring(i));
		}
		
		for(int i = 0; i < buf.length(); i++) {
			this.cursor.execute(i);
			assertEquals(this.engine.getMark(), this.engine.getDot());
			assertTrue(this.engine.getSelection().isEmpty());
			this.selection.execute(i);
			assertEquals(this.engine.getDot(), i);
			assertEquals(this.engine.getMark(), this.engine.getDot());
			assertTrue(this.engine.getSelection().isEmpty());
		}
		
		this.cursor.execute(0);
		for(int i = 0; i < buf.length(); i++) {
			this.moveCursor.execute(-1);
			assertTrue(this.engine.getSelection().isEmpty());
			this.selection.execute(i);
			assertEquals(this.engine.getDot(), i);
			assertEquals(this.engine.getSelection(), buf.substring(Math.max(0, i - 2), i));
		}
		
		this.cursor.execute(0);
		for(int i = 0; i < buf.length(); i++) {
			this.moveCursor.execute(0);
			assertTrue(this.engine.getSelection().isEmpty());
			this.selection.execute(i);
			assertEquals(this.engine.getDot(), i);
			assertEquals(this.engine.getSelection(), buf.substring(Math.max(0, i - 1), i));
		}
		
		this.cursor.execute(0);
		for(int i = 1; i < buf.length() ; i++) {
			this.moveCursor.execute(0);
			assertTrue(this.engine.getSelection().isEmpty());
			this.selection.execute(i);
			assertEquals(this.engine.getDot(), i);
			assertEquals(this.engine.getSelection(), buf.substring(i - 1, i ));
		}
		
		this.cursor.execute(0);
		for(int i = 1; i < buf.length() ; i++) {
			this.moveCursor.execute(1);
			assertTrue(this.engine.getSelection().isEmpty());
			this.selection.execute(i);
			assertEquals(this.engine.getDot(), i);
			assertTrue(this.engine.getSelection().isEmpty());
		}
	}
	
	/**
	 * Test command for {@link commands.selection.MoveSelectionCommand}.
	 */

	@Test
	void testMoveSelectionCommand() {
		String buf = "Activation makes use of special identifiers to denote remote objects that can be activated over time. An activation identifier (an instance of the class ActivationID) contains several pieces of information needed for activating an object:";
		this.insert.execute(buf);
		assertEquals(this.engine.getDot(), buf.length());
		for(int i = 0; i < buf.length(); i++) {
			assertEquals(this.engine.getSelection(), buf.substring(buf.length() - i, buf.length()));
			assertEquals(this.engine.getDot(), buf.length() - i);
			assertEquals(this.engine.getMark(), buf.length());
			this.moveSelection.execute(- 1);
		}
		
		assertEquals(this.engine.getSelection(), buf);
		this.moveCursor.execute(buf.length());
		assertTrue(this.engine.getSelection().isEmpty());
		assertEquals(this.engine.getDot(), buf.length());
		
		for(int i = 0; i < buf.length(); i++) {
			assertTrue(this.engine.getSelection().isEmpty());
			this.moveSelection.execute(- 1);
			this.moveCursor.execute(-12);
		}
		
		for(int i = 0; i < buf.length(); i++) {
			assertTrue(this.engine.getSelection().isEmpty());
			this.moveSelection.execute(+ 1);
			this.moveCursor.execute(0);
		}
		
		this.cursor.execute(0);
		
		for(int i = 0; i < buf.length(); i++) {
			this.moveSelection.execute(1);
			assertEquals(this.engine.getSelection(), Character.toString(buf.charAt(i)));
			this.insert.execute(Character.toString(buf.charAt(i)));
		}
		
		assertEquals(this.engine.getBuffer(), buf);
		
		this.cursor.execute(0);
		
		for(int i = 1; i < buf.length(); i++) {
			this.cursor.execute(i);
			this.moveSelection.execute(-1);
			assertEquals(this.engine.getSelection(), Character.toString(buf.charAt(i - 1)));
			this.insert.execute(Character.toString(buf.charAt(i - 1)));
		}
		
		assertEquals(this.engine.getBuffer(), buf);
	}
	
	/**
	 * Test command for {@link commands.selection.SelectAllCommand}.
	 */

	@Test
	void testSelectAllCommand() {
		String buf = "org/omg/CosNaming/NamingContextPackage/AlreadyBoundHolder.java . Generated by the IDL-to-Java compiler (portable), version \"3.2\" from ../../../../src/share/classes/org/omg/CosNaming/nameservice.idl Sunday, October 8, 2017 11:56:57 PM PDT";
		
		this.insert.execute(buf);
		assertEquals(this.engine.getBuffer(), buf);
		assertTrue(this.engine.getSelection().isEmpty());
		
		this.selectAll.execute();
		assertEquals(this.engine.getBuffer(), buf);
		assertEquals(this.engine.getSelection(), buf);
		
		assertEquals(Math.abs(this.engine.getDot() - this.engine.getMark()), buf.length());
		assertEquals(this.engine.getMark(), 0);
		assertEquals(this.engine.getDot(), buf.length());
		
		this.insert.execute(buf);
		
		assertEquals(this.engine.getBuffer(), buf);
		
		this.insert.execute(buf + "aleatoire .. ..  . . ");
		
		this.selectAll.execute();
		this.delete.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
	}

	/**
	 * Test command for {@link commands.com.InsertCommand}.
	 */

	@Test
	void testInsertCommand() {
		this.insert.execute("");
		assertEquals(this.engine.getBuffer(), "");

		String buf = "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat";
		int ci = 0;
		for (Character c : buf.toCharArray()) {
			this.insert.execute(Character.toString(c));
			assertEquals(this.engine.getBuffer(), buf.substring(0, ++ci));
		}

		assertEquals(this.engine.getBuffer(), buf);

		this.engine.setBuffer("");

		for (int i = buf.length(); i > 0; i--) {
			this.cursor.execute(0);
			this.insert.execute(Character.toString(buf.charAt(i - 1)));
		}

		assertEquals(this.engine.getBuffer(), buf);

		{
			int orig = buf.length() / 2;
			this.cursor.execute(orig);

			String buf1 = "Sets the caret position to some position. This causes the mark to become the same as the dot, effectively setting the selection range to zero.";

			this.insert.execute(buf1);

			this.selection.execute(orig);

			assertEquals(this.engine.getSelection(), buf1);
			
			this.insert.execute(buf1);
			
			this.selection.execute(orig);
			
			assertEquals(this.engine.getSelection(), buf1);
			
			this.selection.execute();
			
			assertEquals(this.engine.getSelection(), buf1);
		}
		
		{
			String buf2 = "REPLACE Moves the caret position (dot) to some other position, leaving behind the mark. This is useful for making selections. REPLACE";
			
			this.selectAll.execute();
			
			this.insert.execute(buf2);
			
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getBuffer(), buf2);
			
			String buf3 = "If the parameter is negative or beyond the length of the document, the caret is placed at the beginning or at the end, respectively.";
			this.insert.execute(buf3);
			
			assertEquals(this.engine.getBuffer(), buf2 + buf3);
			
			this.selectAll.execute();
			
			this.insert.execute();
			
			assertEquals(this.engine.getBuffer(), buf2 + buf3);
			
			this.insert.execute("");
			
			assertTrue(this.engine.getBuffer().isEmpty());
			
			
			this.insert.execute();
		}
	}

	/**
	 * Test command for {@link commands.com.DeleteCommand}.
	 */

	@Test
	void testDeleteCommand() {
		String buf = "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue;";
		this.insert.execute(buf);
		
		this.cursor.execute(0);
		for(int i = 0; i < buf.length(); i++) {
			this.moveSelection.execute(1);
			assertEquals(this.engine.getSelection(), buf.substring(i, i + 1));
			assertEquals(this.engine.getBuffer(), buf.substring(i));
			this.delete.execute();
			assertEquals(this.engine.getBuffer(), buf.substring(i + 1));
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertEquals(this.engine.getDot(), 0);
			assertTrue(this.engine.getClipboard().isEmpty());
		}

		assertEquals(this.engine.getBuffer(), "");

		this.engine.setBuffer(buf);

		this.cursor.execute(buf.length());
		for (int i = buf.length() ; i > 0; i--) {
			this.moveCursor.execute(-1);
			this.moveSelection.execute(1);
			assertEquals(this.engine.getSelection(), buf.substring(i - 1, i));
			this.delete.execute();
			assertEquals(this.engine.getBuffer(), buf.substring(0, i - 1));
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getDot(), i - 1);
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertTrue(this.engine.getClipboard().isEmpty());
		}

		assertTrue(this.engine.getBuffer().isEmpty());
		
		this.insert.execute(buf);
		assertEquals(this.engine.getDot(), buf.length());
		for(int i = 0; i < buf.length(); i++) {
			assertEquals(this.engine.getBuffer(), buf.substring(0, buf.length() - i));
			this.delete.execute();
		}
		
		assertTrue(this.engine.getBuffer().isEmpty());
	}

	/**
	 * Test command for {@link commands.com.CutCommand} and
	 * {@link commands.com.PasteCommand}.
	 */

	@Test
	void testCutPasteCommand() {
		String buf = "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue;";
		this.insert.execute(buf);

		this.cursor.execute(0);
		for (int i = 0; i < buf.length(); i++) {
			this.selection.execute(1);
			assertFalse(this.engine.getSelection().isEmpty());
			String selection = this.engine.getSelection();
			this.cut.execute();
			assertEquals(this.engine.getClipboard(), selection);
			assertEquals(this.engine.getBuffer(), buf.substring(i + 1));
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getDot(), 0);
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertEquals(this.engine.getClipboard(), buf.substring(i, i + 1));
		}

		assertTrue(this.engine.getBuffer().isEmpty());

		this.insert.execute(buf);

		for (int i = buf.length(); i > 0; i--) {
			this.moveSelection.execute(- 1);
			assertEquals(this.engine.getDot(), i - 1);
			assertEquals(this.engine.getMark(), i);
			this.cut.execute();
			assertEquals(this.engine.getBuffer(), buf.substring(0, i - 1));
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getDot(), i - 1);
			assertEquals(this.engine.getClipboard(), buf.substring(i - 1, i));
		}

		assertTrue(this.engine.getBuffer().isEmpty());

		this.insert.execute(buf);
		for (int i = 0; i < buf.length(); i++) {
			this.cursor.execute(0);
			this.selection.execute(1);
			this.cut.execute();
			this.cursor.execute(buf.length() - 1);
			this.paste.execute();
		}

		assertEquals(this.engine.getBuffer(), buf);

		this.engine.setBuffer(buf);
		this.selectAll.execute();
		this.cut.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		assertEquals(this.engine.getClipboard(), buf);
		this.paste.execute();
		assertEquals(this.engine.getBuffer(), buf);
		assertEquals(this.engine.getClipboard(), buf);

		this.paste.execute();

		assertEquals(this.engine.getBuffer(), buf + buf);
		assertEquals(this.engine.getClipboard(), buf);

		this.selectAll.execute();
		this.cut.execute();
		assertEquals(this.engine.getBuffer(), "");
		assertEquals(this.engine.getClipboard(), buf + buf);
		this.paste.execute();
		assertEquals(this.engine.getBuffer(), buf + buf);
		assertEquals(this.engine.getClipboard(), buf + buf);
		this.selectAll.execute();
		this.paste.execute();
		this.paste.execute();
		assertEquals(this.engine.getBuffer(), buf + buf + buf + buf);
		assertEquals(this.engine.getClipboard(), buf + buf);
	}

	/**
	 * Test command for {@link commands.com.CopyCommand} and
	 * {@link commands.com.PasteCommand}.
	 */

	@Test
	void testCopyPasteCommand() {
		String buf = "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue;";
		this.insert.execute(buf);

		/*
		 * Copy all the character one by one of the buf and add theses to the end the
		 * result must be equals to buf concatened with buf itself.
		 */
		this.cursor.execute(0);
		for (int i = 0; i < buf.length(); i++) {
			this.cursor.execute(i);
			this.moveSelection.execute(1);
			this.copy.execute();
			this.moveCursor.execute(buf.length());
			this.paste.execute();
			assertEquals(this.engine.getBuffer(), buf + buf.substring(0, i + 1));
			assertEquals(this.engine.getSelection(), "");
			assertEquals(this.engine.getDot(), buf.length() + i + this.engine.getClipboard().length());
			assertEquals(this.engine.getDot(), this.engine.getBuffer().length());
			assertEquals(this.engine.getDot(), this.engine.getMark());
			assertEquals(this.engine.getClipboard(), buf.substring(i, i + 1));
		}

		assertEquals(this.engine.getBuffer(), buf + buf);

		/*
		 * Select, copy and past all the character one by one of the buf the result must
		 * be equals of buf, because the paste command will replace the selection
		 */
		this.engine.setBuffer(buf);
		this.cursor.execute(0);
		for (int i = 0; i < buf.length(); i++) {
			this.moveSelection.execute(1);
			this.copy.execute();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			this.paste.execute();
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getDot(), i + 1);
		}

		assertEquals(this.engine.getBuffer(), buf);

		/*
		 * Copy all the character of the buf one by one and paste it twice The result is
		 * equivalent to double all the character of the buf
		 */

		this.engine.setBuffer(buf);
		this.cursor.execute(0);
		for (int i = 0; i < buf.length() * 2; i += 2) {
			this.moveSelection.execute(1);
			this.copy.execute();
			assertEquals(this.engine.getSelection(), this.engine.getClipboard());
			this.paste.execute();
			this.paste.execute();
			assertTrue(this.engine.getSelection().isEmpty());
			assertEquals(this.engine.getDot(), i + 2);
		}

		String dbuf = "";
		for (Character c : buf.toCharArray()) {
			dbuf += Character.toString(c) + Character.toString(c);
		}
		assertEquals(this.engine.getBuffer(), dbuf);

		this.engine.setBuffer(buf);
		this.selectAll.execute();
		this.copy.execute();
		assertEquals(this.engine.getBuffer(), buf);
		assertEquals(this.engine.getSelection(), this.engine.getClipboard());
		this.cursor.execute(0);
		this.paste.execute();
		assertEquals(this.engine.getClipboard(), buf);
		assertEquals(this.engine.getBuffer(), buf + buf);
		this.cursor.execute(buf.length() * 2);
		this.paste.execute();
		assertEquals(this.engine.getClipboard(), buf);
		assertEquals(this.engine.getBuffer(), buf + buf + buf);
		this.selectAll.execute();
		this.paste.execute();
		assertEquals(this.engine.getBuffer(), buf);

		this.cursor.execute(buf.length());
		this.copy.execute();
		assertEquals(this.engine.getClipboard(), buf);
		this.paste.execute();
		assertEquals(this.engine.getBuffer(), buf + buf);
	}
}
