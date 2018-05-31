package client;

import commands.macro.*;
import commands.undo_redo.*;
import invoker.*;
import memento.*;
import receiver.Moteur;
import receiver.MoteurImpl;
import undo_redo.compensation.*;

/**
 * @author VinYarD
 * 
 *         <p>
 * 		Crée un nouveau client, ne comporte qu'un constructeur sans
 *         paramètre.
 *         </p>
 *         <p>
 * 		Utilise comme sortie par défaut System.out et comme entrée par défaut
 *         System.in.
 *         </p>
 *         <p>
 * 		Ce client utilise les commandes :
 *         </p>
 *         <ul>
 *         <li>Coller (Undo/Redo - Macro)</li>
 *         <li>Copier (Macro)</li>
 *         <li>Couper (Undo/Redo - Macro)</li>
 *         <li>Insérer (Undo/Redo - Macro)</li>
 *         <li>Supprimer (Undo/Redo - Macro)</li>
 * 
 *         <li>Placer le curseur (Macro)</li>
 *         <li>Placer la selection (Macro)</li>
 *         <li>Déplacer le curseur (Macro)</li>
 *         <li>Déplacer la sélection (Macro)</li>
 *         <li>Tout sélectionner (Macro)</li>
 * 
 *         <li>Undo</li>
 *         <li>Redo</li>
 * 
 *         <li>Commencer l'enregistrement d'une macro</li>
 *         <li>Finir l'enregistrement d'une macro</li>
 *         <li>Jouer une macro (Undo/Redo - Macro)</li>
 *         </ul>
 */
public class Client {

	public static void main(String[] args) {
		new Client();
	}

	/**
	 * construit un client par défaut avec toute les fonctionnalités de la V3.
	 */
	@SuppressWarnings("deprecation")
	public Client() {
		Moteur engine = new MoteurImpl();
		Invoker invoker = new IHM(System.in); // Typé IHM à cause de l'observer
		((MoteurImpl) engine).addObserver((IHM) invoker);

		// /*
		// * V1
		// */
		// // com
		// invoker.addCommand("V", new PasteCommand(engine));
		// invoker.addCommand("C", new CopyCommand(engine));
		// invoker.addCommand("X", new CutCommand(engine));
		// invoker.addCommand("I", new InsertCommand(engine, invoker));
		// invoker.addCommand("D", new DeleteCommand(engine));
		//
		// // selection
		// invoker.addCommand("K", new CursorCommand(engine, invoker));
		// invoker.addCommand("W", new SelectionCommand(engine, invoker));
		// invoker.addCommand("MK", new MoveCursorCommand(engine, invoker));
		// invoker.addCommand("MW", new MoveSelectionCommand(engine, invoker));
		// invoker.addCommand("A", new SelectAllCommand(engine));

		// V2

		// Gardian gardian = new Gardian();
		// /*
		// * Com
		// */
		// invoker.addCommand("V", new PasteMemento(engine, gardian));
		// invoker.addCommand("C", new CopyMemento(engine, gardian));
		// invoker.addCommand("X", new CutMemento(engine, gardian));
		// invoker.addCommand("I", new InsertMemento(engine, invoker, gardian));
		// invoker.addCommand("D", new DeleteMemento(engine, gardian));
		//
		// /*
		// * Selection
		// */
		// invoker.addCommand("SC", new CursorMemento(engine, invoker, gardian));
		// invoker.addCommand("SS", new SelectionMemento(engine, invoker, gardian));
		// invoker.addCommand("MC", new MoveCursorMemento(engine, invoker, gardian));
		// invoker.addCommand("MS", new MoveSelectionMemento(engine, invoker, gardian));
		// invoker.addCommand("A", new SelectAllMemento(engine, gardian));
		//
		// /*
		// * Macro
		// */
		// invoker.addCommand("R", new StartRecordCommand(gardian));
		// invoker.addCommand("Q", new StopRecordCommand(gardian));
		// invoker.addCommand("P", new PlayBackCommand(gardian));

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
