package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commands.macro.StartRecordCommand;
import commands.macro.StopRecordCommand;
import memento.CopyMemento;
import memento.CursorMemento;
import memento.Gardian;
import memento.MoveCursorMemento;
import memento.MoveSelectionMemento;
import memento.RedoMemento;
import memento.SelectAllMemento;
import memento.SelectionMemento;
import memento.UndoMemento;
import receiver.Moteur;
import receiver.MoteurImpl;
import undo_redo.compensation.CompensableConversation;
import undo_redo.compensation.CutCompensable;
import undo_redo.compensation.DeleteCompensable;
import undo_redo.compensation.InsertCompensable;
import undo_redo.compensation.PasteCompensable;
import undo_redo.compensation.PlayBackCompensable;

/**
 * @author VinYarD
 *
 */
class CommandTest_v3 {

	private Moteur engine;

	/*
	 * Com
	 */
	private CutCompensable cut;
	private DeleteCompensable delete;
	private CopyMemento copy;
	private PasteCompensable paste;
	private InsertCompensable insert;

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
	private PlayBackCompensable play;
	
	/*
	 * undo redo
	 */
	private UndoMemento undo;
	private RedoMemento redo;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Gardian gardian = new Gardian();
		CompensableConversation conversation = new CompensableConversation();
		this.engine = new MoteurImpl();

		/*
		 * Com
		 */
		this.paste = new PasteCompensable(this.engine, gardian, conversation);
		this.copy = new CopyMemento(this.engine, gardian);
		this.cut = new CutCompensable(this.engine, gardian, conversation);
		this.insert = new InsertCompensable(this.engine, null, gardian, conversation);
		this.delete = new DeleteCompensable(this.engine, gardian, conversation);

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
		this.play = new PlayBackCompensable(gardian, this.engine, conversation);
		
		this.undo = new UndoMemento(conversation, gardian);
		this.redo = new RedoMemento(conversation, gardian);
	}

	/**
	 * Test command for {@link undo_redo.compensation.InsertCompensable }.
	 */

	@Test
	void testInsertCompensableCommand() {
		String buf = "Names of packages that you want to document, separated by spaces, for example java.lang java.lang.reflect java.awt. If you want to also document the subpackages, use the -subpackages option to specify the packages.";

		for (Character c : buf.toCharArray()) {
			this.insert.execute(Character.toString(c));
		}
		assertEquals(this.engine.getBuffer(), buf);
		for(int i = 0; i < buf.length() - 1; i++) {
			this.undo.execute();
		}
		assertEquals(this.engine.getBuffer(), buf.substring(0, 1));
		this.undo.execute();
		assertTrue(this.engine.getBuffer().isEmpty());
		
		this.undo.execute();
		this.undo.execute();
		this.undo.execute();
		
		assertTrue(this.engine.getBuffer().isEmpty());
		
		for(int i = 0; i < buf.length() - 10; i++) {
			this.redo.execute();
		}
		
		assertEquals(this.engine.getBuffer(), buf.substring(0, buf.length() - 10));
		
		for(int i = 0; i < 10; i++) {
			this.redo.execute();
		}
		
		assertEquals(this.engine.getBuffer(), buf);
		
		for(int i = 0; i < 20; i++) {
			this.undo.execute();
		}
		
		this.insert.execute("X");
		assertEquals(this.engine.getBuffer(), buf.substring(0, buf.length() - 20) + "X");
		{
		String bufTmp = this.engine.getBuffer();
		for(int i = 0; i < 5; i++) {
			this.redo.execute();
			assertEquals(this.engine.getBuffer(), bufTmp);
		}
		}
	}
	
	/**
	 * Test command for {@link undo_redo.compensation.PlayBackCompensable }.
	 */

	@Test
	void testPlayBackCompensableCommand() {
		
		String buf = "The javadoc command parses the declarations and documentation comments in a set of Java source files and produces a corresponding set of HTML pages that describe (by default) the public and protected classes, nested classes (but not anonymous inner classes), interfaces, constructors, methods, and fields.";
		
		this.start.execute();
		
		this.insert.execute(buf);
		
		this.selectAll.execute();
		this.copy.execute();
		this.moveCursor.execute(0);
		
		this.paste.execute();
		
		this.stop.execute();
		
		assertEquals(this.engine.getBuffer(), buf  + buf);
		
		this.engine.moveDot(0);
		
		this.play.execute();
		
		
		assertEquals(this.engine.getBuffer(), buf + buf + buf + buf);
		
		this.undo.execute();
		
		assertEquals(this.engine.getBuffer(), buf + buf);
		
this.undo.execute();
		
		assertEquals(this.engine.getBuffer(), 0);
		
	}
}
