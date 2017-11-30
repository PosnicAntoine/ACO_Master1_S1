/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commands.macro.PlayBackCommand;
import commands.macro.StartRecordCommand;
import commands.macro.StopRecordCommand;
import memento.CopyMemento;
import memento.CursorMemento;
import memento.CutMemento;
import memento.DeleteMemento;
import memento.Gardian;
import memento.InsertMemento;
import memento.MoveCursorMemento;
import memento.MoveSelectionMemento;
import memento.PasteMemento;
import memento.SelectAllMemento;
import memento.SelectionMemento;
import receiver.Moteur;
import receiver.MoteurImpl;

/**
 * @author VinYarD
 *
 */
class CommandTest_v2 {

	private Moteur engine;
	
	/*
	 * Com
	 */
	private PasteMemento paste;
	private CopyMemento copy;
	private CutMemento cut;
	private InsertMemento insert;
	private DeleteMemento delete;
	
	/*
	 * Selection
	 */
	private CursorMemento cursor;
	private SelectionMemento selection;
	private MoveCursorMemento moveCursor;
	private MoveSelectionMemento moveSelection;
	private SelectAllMemento selectAll;
	
	/*
	 * Macro
	 */
	private StartRecordCommand start;
	private StopRecordCommand stop;
	private PlayBackCommand play;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		 Gardian gardian = new Gardian();
		
		this.engine = new MoteurImpl();
		
		/*
		 * Com
		 */
		this.paste = new PasteMemento(this.engine, gardian);
		this.copy = new CopyMemento(this.engine, gardian);
		this.cut = new CutMemento(this.engine, gardian);
		this.insert = new InsertMemento(this.engine, null, gardian);
		this.delete = new DeleteMemento(this.engine, gardian);
		
		/*
		 * Selection
		 */
		this.cursor = new CursorMemento(this.engine, null, gardian);
		this.selection = new SelectionMemento(this.engine, null, gardian);
		this.moveCursor = new MoveCursorMemento(this.engine, null, gardian);
		this.moveSelection = new MoveSelectionMemento(this.engine, null, gardian);
		this.selectAll = new SelectAllMemento(this.engine, gardian);
		
		/*
		 * Macro
		 */
		this.start = new StartRecordCommand(gardian);
		this.stop = new StopRecordCommand(gardian);
		this.play = new PlayBackCommand(gardian);
	}
	
	/**
	 * Test command for {@link memento.MoveCursorMemento }.
	 */
	
	@Test
	void testMoveCursorMementoCommand() {
		String buf = "The default action for malformed-input and unmappable-character errors is to report them.";
		
		this.insert.execute(buf);
		assertEquals(this.engine.getDot(), buf.length());
		
		this.start.execute();
		for(int i = 0; i < 10; i++) {
			this.moveCursor.execute(-1);
		}
		this.stop.execute();
		
		assertEquals(this.engine.getDot(), buf.length() - 10);
		this.play.execute();
		assertEquals(this.engine.getDot(), buf.length() - 20);
		this.play.execute();
		assertEquals(this.engine.getDot(), buf.length() - 30);
		
		this.cursor.execute(0);
		this.play.execute();
		assertEquals(this.engine.getDot(), 0);
		this.play.execute();
		assertEquals(this.engine.getDot(), 0);
		
		this.start.execute();
		for(int i = 0; i < buf.length() + 1; i++) {
			this.insert.execute("|");
			this.moveCursor.execute(1);
		}
		this.stop.execute();
		
		{
			String bufsep = "";
			for(Character c : buf.toCharArray()) {
				bufsep += "|" + c;
			}
			bufsep += "|";
			
			assertEquals(this.engine.getBuffer(), bufsep);
			
			this.selectAll.execute();
			this.delete.execute();
			
			this.insert.execute(buf);
			
			this.cursor.execute(0);
			this.play.execute();
			
			assertEquals(this.engine.getBuffer(), bufsep);
			
			for(int i = 0; i < buf.length() + 1; i++) {
				bufsep += "|";
			}
			
			this.play.execute();
			assertEquals(this.engine.getBuffer(), bufsep);
		}
	}
	
	/**
	 * Test command for {@link memento.MoveSelectionMemento }.
	 */
	
	@Test
	void testMoveSelectionMementoCommand() {
		String buf = "Instances of this class are not safe for use by multiple concurrent threads.";
		
		this.start.execute();
		for(int i = 0; i < 5; i++) {
			this.moveSelection.execute(-2);
		}
		this.stop.execute();
		
		assertTrue(this.engine.getBuffer().isEmpty());
		assertTrue(this.engine.getSelection().isEmpty());
		
		this.play.execute();
		
		assertTrue(this.engine.getBuffer().isEmpty());
		assertTrue(this.engine.getSelection().isEmpty());
		
		this.insert.execute(buf);
		assertTrue(this.engine.getSelection().isEmpty());
		assertEquals(this.engine.getDot(), buf.length());
		
		this.play.execute();
		assertEquals(this.engine.getSelection().length(), 5 * 2);
		assertEquals(this.engine.getDot(), buf.length() - 5 * 2);
		assertEquals(this.engine.getMark(), buf.length());
	}
	
	/**
	 * Test command for {@link memento.InsertionMemento }.
	 */
	
	@Test
	void testInsertionMementoCommand() {
		/*
		 * Before will not be repeated by play command
		 */
		String before = "Test";
		this.insert.execute(before);
		
		this.start.execute();
		String buf = "123";
		
		for(Character c : buf.toCharArray()) {
			this.insert.execute(Character.toString(c));
		}
		
		this.stop.execute();
		
		assertEquals(this.engine.getBuffer(), before + buf);
		
		
		this.play.execute();
		
		assertEquals(this.engine.getBuffer(), before + buf + buf);
		
		this.cursor.execute(0);
		
		
		this.play.execute();
		assertEquals(this.engine.getBuffer(), buf + before + buf + buf);
		
		this.start.execute();
		this.cursor.execute(0);
		this.selection.execute(this.engine.getBuffer().length());
		this.delete.execute();
		
		for(Character c : buf.toCharArray()) {
			this.cursor.execute(0);
			this.insert.execute(Character.toString(c));
			
		}
		
		this.cursor.execute(this.engine.getBuffer().length());
		this.stop.execute();
		
		String bufinv = "";
		for(Character c : buf.toCharArray()) {
			bufinv = c + bufinv;
		}
		
		assertEquals(this.engine.getBuffer(), bufinv);
		this.play.execute();
		assertEquals(this.engine.getBuffer(), bufinv);
		this.play.execute();
		this.play.execute();
		assertEquals(this.engine.getBuffer(), bufinv);
	}
	
	/**
	 * Test command for {@link memento.DeleteMemento }.
	 */
	
	@Test
	void testDeleteMementoCommand() {
		String buf = "The AudioPermission class represents access rights to the audio system resources. An AudioPermission contains a target name but no actions list; you either have the named permission or you don't.";
		
		this.insert.execute(buf);
		assertEquals(this.engine.getBuffer(), buf);
		this.start.execute();
		for(int i = 0; i < buf.length() * 2; i++) {
			this.delete.execute();
		}
		assertTrue(this.engine.getBuffer().isEmpty());
		this.stop.execute();
		this.play.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		this.insert.execute(buf);
		assertEquals(this.engine.getBuffer(), buf);
		this.play.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		this.insert.execute(buf + buf );
		assertEquals(this.engine.getBuffer().length(), buf.length() * 2);
		this.play.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		this.insert.execute("X" + buf + buf);
		assertEquals(this.engine.getBuffer().length(), buf.length() * 2 + 1);
		this.play.execute();
		assertEquals(this.engine.getBuffer().length(), 1);
		assertEquals(this.engine.getBuffer(), "X");
		
		this.start.execute();
		this.selectAll.execute();
		this.delete.execute();
		this.stop.execute();
		
		this.insert.execute(buf + buf + buf + buf);
		assertEquals(this.engine.getBuffer().length(), buf.length() * 4);
		this.play.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		this.play.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		
		this.start.execute();
		this.delete.execute();
		this.delete.execute();
		this.stop.execute();
		
		this.insert.execute(buf);
		this.cursor.execute(1);
		this.selection.execute(buf.length());
		assertEquals(this.engine.getSelection().length(), this.engine.getBuffer().length() - 1);
		this.play.execute();
		assertTrue(this.engine.getSelection().isEmpty());
		assertTrue(this.engine.getBuffer().isEmpty());
		
		this.insert.execute(buf);
		assertEquals(this.engine.getDot(), buf.length());
		this.selection.execute(4);
		assertEquals(this.engine.getSelection().length(), this.engine.getBuffer().length() - 4);
		this.play.execute();
		assertEquals(this.engine.getBuffer().length(), 3);
		assertEquals(this.engine.getBuffer(), buf.substring(0, 3));
		assertTrue(this.engine.getSelection().isEmpty());
		this.play.execute(); 
		assertTrue(this.engine.getSelection().isEmpty());
		assertEquals(this.engine.getBuffer().length(), 1);
		assertEquals(this.engine.getBuffer(), buf.substring(0, 1));
		this.play.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		assertTrue(this.engine.getBuffer().isEmpty());
	}
	
	/**
	 * Test command for {@link commands.macro.PlayCommand }.
	 */
	
	@Test
	void testPlayCommand() {
		String buf = "The BindingIterator interface allows a client to iterate through the bindings using the next_one or next_n operations. The bindings iterator is obtained by using the list method on the NamingContext.";
		
		assertThrows(RuntimeException.class, () -> { this.play.execute(); });
		
		this.insert.execute(buf);
		
		assertThrows(RuntimeException.class, () -> { this.play.execute(); });
		
		this.start.execute();
		this.stop.execute();
		
		assertThrows(RuntimeException.class, () -> { this.play.execute(); });
		
		this.start.execute();
		assertThrows(RuntimeException.class, () -> { this.play.execute(); });
		this.stop.execute();
		
		this.start.execute();
		this.cursor.execute(10);
		assertEquals(this.engine.getDot(), 10);
		this.stop.execute();
		
		this.play.execute();
		assertEquals(this.engine.getDot(), 10);
		this.cursor.execute(27);
		assertEquals(this.engine.getDot(), 27);
		this.play.execute();
		assertEquals(this.engine.getDot(), 10);
		
		
		this.start.execute();
		this.stop.execute();
		assertThrows(RuntimeException.class, () -> { this.play.execute(); });
		
		this.start.execute();
		assertThrows(RuntimeException.class, () -> { this.play.execute(); });
		this.stop.execute();
	}
	
	/**
	 * Test command for {@link commands.macro.StopCommand }.
	 */
	
	@Test
	void testStopCommand() {
		String buf = "Certificate is not yet valid exception. This is thrown whenever the current Date or the specified Date is before the notBefore date/time in the Certificate validity period.";
		
		this.insert.execute(buf);
		
		assertThrows(RuntimeException.class, () -> { this.stop.execute(); });
		
		this.start.execute();
		this.delete.execute();
		this.delete.execute();
		this.stop.execute();
		
		assertEquals(this.engine.getBuffer(), buf.substring(0, buf.length() - 2));
		
		this.delete.execute();
		
		assertEquals(this.engine.getBuffer(), buf.substring(0, buf.length() - 3));
		
		this.play.execute();
		this.play.execute();
		
		assertEquals(this.engine.getBuffer(), buf.substring(0, buf.length() - 7));
		
		assertThrows(RuntimeException.class, () -> { this.stop.execute(); });
	}
	
	/**
	 * Test command for {@link commands.macro.PlayCommand }.
	 */
	
	@Test
	void testSartCommand() {
		this.start.execute();
		assertThrows(RuntimeException.class, () -> { this.start.execute(); });
		this.stop.execute();
		this.start.execute();
		assertThrows(RuntimeException.class, () -> { this.start.execute(); });
		this.stop.execute();
	}
	
	/**
	 * Test command for {@link memento.CopyMemento }.
	 */
	
	@Test
	void testCopyMementoCommand() {
		String buf = "How an encoding error is handled depends upon the action requested for that type of error, which is described by an instance of the CodingErrorAction class.";
		
		this.insert.execute(buf);
		
		assertEquals(buf, this.engine.getBuffer());
		assertTrue(this.engine.getSelection().isEmpty());
		
		this.start.execute();
		this.copy.execute();
		this.paste.execute();
		this.paste.execute();
		this.stop.execute();
		
		assertEquals(buf, this.engine.getBuffer());
		this.play.execute();
		assertEquals(buf, this.engine.getBuffer());
		
		
		this.selectAll.execute();
		this.play.execute();
		assertEquals(buf + buf, this.engine.getBuffer());
		this.insert.execute("X");
		this.moveSelection.execute(-1);
		this.play.execute();
		assertEquals(buf + buf + "XX", this.engine.getBuffer());
		
		this.selectAll.execute();
		this.insert.execute(buf);
		
		this.start.execute();
		this.selectAll.execute();
		this.copy.execute();
		this.paste.execute();
		this.paste.execute();
		this.selectAll.execute();
		this.copy.execute();
		this.paste.execute();
		this.paste.execute();
		this.stop.execute();
		
		assertEquals(this.engine.getBuffer(), buf + buf + buf + buf);
		
		this.play.execute();
		
		assertEquals(this.engine.getBuffer().length(), buf.length() * 4 * 4);
		
		this.play.execute();
		assertEquals(this.engine.getBuffer().length(), buf.length() * 4 * 4 * 4);
		
		this.selectAll.execute();
		this.delete.execute();
		
		assertTrue(this.engine.getBuffer().isEmpty());
		this.play.execute();
		assertEquals(this.engine.getBuffer().length(), buf.length() * 4 * 4 * 4 * 2);
		
		this.insert.execute(buf);
		
		this.play.execute();
		
		this.selectAll.execute();
		this.insert.execute(".");
		
		this.play.execute();
		
		assertEquals(this.engine.getBuffer().length(), 4);
		
		this.play.execute();
		
		assertEquals(this.engine.getBuffer().length(), 4 * 4);
	}
	
	/**
	 * Test command for {@link memento.CutMemento }.
	 */
	
	@Test
	void testCutMementoCommand() {
		String buf = "There are two general types of encoding errors.";
		
		this.insert.execute(buf);
		
		this.cursor.execute(0);
		this.start.execute();
		
		for(int i = 0; i < buf.length() ; i++) {
			this.moveSelection.execute(1);
			this.cut.execute();
			assertEquals(this.engine.getBuffer(), buf.substring(i + 1));
		}
		
		assertTrue(this.engine.getBuffer().isEmpty());
		
		this.stop.execute();
		this.insert.execute(buf + buf);
		this.cursor.execute(buf.length());
		this.play.execute();
		assertEquals(this.engine.getBuffer(), buf);
		this.play.execute();
		assertEquals(this.engine.getBuffer(), buf);
		this.moveCursor.execute(- buf.length());
		this.play.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		
		this.insert.execute(buf);
		this.cursor.execute(0);
		this.start.execute();
		this.moveCursor.execute(0);
		
		for(int i = 0; i < buf.length(); i ++) {
			this.moveSelection.execute(1);
			this.cut.execute();
			this.moveCursor.execute(buf.length() - i - 1);
			this.paste.execute();
			this.moveCursor.execute(-buf.length());
		}
		this.stop.execute();
		
		{
			String bufreverse = "";
			for(Character c : buf.toCharArray()) {
				bufreverse = c + bufreverse;
			}
			assertEquals(this.engine.getBuffer(), bufreverse);
			this.cursor.execute(0);
			
			this.play.execute();
			
			assertEquals(this.engine.getBuffer(), buf);
			
			this.play.execute();
			
			assertEquals(this.engine.getBuffer(), bufreverse);
		}
	}
}
