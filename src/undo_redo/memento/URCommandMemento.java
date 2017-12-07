package undo_redo.memento;

import commands.Command;
import receiver.Moteur;

public class URCommandMemento implements URCommandMementoable {

	private URMementoableConversation conversation;
	private Command command;
	private Moteur moteur;
	
	public URCommandMemento(Command command, Moteur moteur, URMementoableConversation conversation) {
		this.command = command;
		this.conversation = conversation;
		this.moteur = moteur;
	}

	@Override
	public void undo(undo_redo.memento.URCommandMementoable.Memento memento) {
		URCommandMementoable.State state = memento.getState(); 
		this.moteur.setBuffer(state.getBefore());
	}

	@Override
	public void redo(undo_redo.memento.URCommandMementoable.Memento memento) {
		URCommandMementoable.State state = memento.getState(); 
		this.moteur.setBuffer(state.getAfter());
	}

	@Override
	public void execute() {
		String before = this.moteur.getBuffer();
		this.command.execute();
		String after = this.moteur.getBuffer();
		this.conversation.register(new URCommandMementoable.Memento(this, new URCommandMementoable.State(before, after)));
	}
}
