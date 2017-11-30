package client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import commands.macro.StartRecordCommand;
import commands.macro.StopRecordCommand;
import commands.selection.CursorCommand;
import commands.undo_redo.RedoCommand;
import commands.undo_redo.UndoCommand;
import invoker.IHM;
import invoker.Invoker;
import memento.CopyMemento;
import memento.Gardian;
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
		// V1
		// ihm.addCommand("V", new PasteCommand(m));
		// ihm.addCommand("C", new CopyCommand(m));
		// ihm.addCommand("X", new CutCommand(m));
		// ihm.addCommand("I", new InsertCommand(m, ihm));
		// ihm.addCommand("S", new SelectionCommand(m, ihm));
		// ihm.addCommand("D", new DeleteCommand(m));

		// V2
		// Gardian gardian = new Gardian();
		// invoker.addCommand("V", new PasteMemento(engine, gardian));
		// invoker.addCommand("C", new CopyMemento(engine, gardian));
		// invoker.addCommand("X", new CutMemento(engine, gardian));
		// invoker.addCommand("I", new InsertMemento(engine, invoker, gardian));
		// invoker.addCommand("S", new SelectionCommand(engine, invoker));
		// invoker.addCommand("D", new DeleteMemento(engine, gardian));
		//
		// invoker.addCommand("R", new StartRecordCommand(gardian));
		// invoker.addCommand("Q", new StopRecordCommand(gardian));
		// invoker.addCommand("P", new PlayBackCommand(gardian));

		// v3
		CompensableConversation conversation = new CompensableConversation();
		Gardian gardian = new Gardian();
		invoker.addCommand("V", new PasteCompensable(engine, gardian, conversation));
		invoker.addCommand("C", new CopyMemento(engine, gardian)); // nothing to undo or redo here
		invoker.addCommand("X", new CutCompensable(engine, gardian, conversation));
		invoker.addCommand("I", new InsertCompensable(engine, invoker, gardian, conversation));
		invoker.addCommand("S", new CursorCommand(engine, invoker));
		invoker.addCommand("D", new DeleteCompensable(engine, gardian, conversation));

		invoker.addCommand("R", new StartRecordCommand(gardian));
		invoker.addCommand("Q", new StopRecordCommand(gardian));
		invoker.addCommand("P", new PlayBackCompensable(gardian, engine, conversation));

		invoker.addCommand("Z", new UndoCommand(conversation));
		invoker.addCommand("Y", new RedoCommand(conversation));

		((IHM) invoker).beginLoop();
		
	}

}
