package client;

import commands.macro.StartRecordCommand;
import commands.macro.StopRecordCommand;
import commands.undo_redo.RedoCommand;
import commands.undo_redo.UndoCommand;
import invoker.IHM;
import invoker.Invoker;
import memento.CopyMemento;
import memento.CursorMemento;
import memento.Gardian;
import memento.MoveCursorMemento;
import memento.MoveSelectionMemento;
import memento.SelectAllMemento;
import memento.SelectionMemento;
import receiver.Moteur;
import receiver.MoteurImpl;
import undo_redo.compensation.CompensableConversation;
import undo_redo.compensation.CutCompensable;
import undo_redo.compensation.DeleteCompensable;
import undo_redo.compensation.InsertCompensable;
import undo_redo.compensation.PasteCompensable;
import undo_redo.compensation.PlayBackCompensable;

public class Client {

	public static void main(String[] args) {
		new Client();
	}

	@SuppressWarnings("deprecation")
	public Client() {
		Moteur engine = new MoteurImpl();
		Invoker invoker = new IHM(System.in); // Typé IHM à cause de l'observer
		((MoteurImpl) engine).addObserver((IHM) invoker);
		
		/*
		 * V1
		 */
//		// com
//		invoker.addCommand("V", new PasteCommand(engine));
//		invoker.addCommand("C", new CopyCommand(engine));
//		invoker.addCommand("X", new CutCommand(engine));
//		invoker.addCommand("I", new InsertCommand(engine, invoker));
//		invoker.addCommand("D", new DeleteCommand(engine));
//
//		// selection
//		invoker.addCommand("K", new CursorCommand(engine, invoker));
//		invoker.addCommand("W", new SelectionCommand(engine, invoker));
//		invoker.addCommand("MK", new MoveCursorCommand(engine, invoker));
//		invoker.addCommand("MW", new MoveSelectionCommand(engine, invoker));
//		invoker.addCommand("A", new SelectAllCommand(engine));

		// V2
		
//		Gardian gardian = new Gardian();
//		/*
//		 * Com
//		 */
//		invoker.addCommand("V", new PasteMemento(engine, gardian));
//		invoker.addCommand("C", new CopyMemento(engine, gardian));
//		invoker.addCommand("X", new CutMemento(engine, gardian));
//		invoker.addCommand("I", new InsertMemento(engine, invoker, gardian));
//		invoker.addCommand("D", new DeleteMemento(engine, gardian));
//		
//		/*
//		 * Selection
//		 */
//		invoker.addCommand("SC", new CursorMemento(engine, invoker, gardian));
//		invoker.addCommand("SS", new SelectionMemento(engine, invoker, gardian));
//		invoker.addCommand("MC", new MoveCursorMemento(engine, invoker, gardian));
//		invoker.addCommand("MS", new MoveSelectionMemento(engine, invoker, gardian));
//		invoker.addCommand("A", new SelectAllMemento(engine, gardian));
//		
//		/*
//		 * Macro
//		 */
//		invoker.addCommand("R", new StartRecordCommand(gardian));
//		invoker.addCommand("Q", new StopRecordCommand(gardian));
//		invoker.addCommand("P", new PlayBackCommand(gardian));

		// v3
		CompensableConversation conversation = new CompensableConversation();
		Gardian gardian = new Gardian();
		/*
		 * Com
		 */
		invoker.addCommand("V", new PasteCompensable(engine, gardian, conversation));
		invoker.addCommand("C", new CopyMemento(engine, gardian)); // nothing to undo or redo here
		invoker.addCommand("X", new CutCompensable(engine, gardian, conversation));
		invoker.addCommand("I", new InsertCompensable(engine, invoker, gardian, conversation));
		invoker.addCommand("D", new DeleteCompensable(engine, gardian, conversation));

		/*
		 * Selection
		 */
		invoker.addCommand("SC", new CursorMemento(engine, invoker, gardian));
		invoker.addCommand("SS", new SelectionMemento(engine, invoker, gardian));
		invoker.addCommand("MC", new MoveCursorMemento(engine, invoker, gardian));
		invoker.addCommand("MS", new MoveSelectionMemento(engine, invoker, gardian));
		invoker.addCommand("A", new SelectAllMemento(engine, gardian));
		
		/*
		 * Macro
		 */
		invoker.addCommand("R", new StartRecordCommand(gardian));
		invoker.addCommand("Q", new StopRecordCommand(gardian));
		invoker.addCommand("P", new PlayBackCompensable(gardian, engine, conversation));

		/*
		 * Undo/redo
		 */
		invoker.addCommand("Z", new UndoCommand(conversation));
		invoker.addCommand("Y", new RedoCommand(conversation));

		((IHM) invoker).beginLoop();

	}

}
