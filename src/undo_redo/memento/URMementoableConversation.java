package undo_redo.memento;

import undo_redo.AbstractConversation;

public class URMementoableConversation extends AbstractConversation<URCommandMementoable.Memento, URCommandMementoable, URCommandMementoable.State> {

	public void register(URCommandMementoable.Memento memento) {
		this.undos.push(memento);
		this.redos.clear();
	}

	public void undo() {
		if(this.undos.isEmpty()) return;
		URCommandMementoable.Memento c = this.undos.pop();
		c.getSource().undo(c);
		this.redos.push(c);
	}

	public void redo() {
		if(this.redos.isEmpty()) return;
		URCommandMementoable.Memento c = this.redos.pop();
		c.getSource().redo(c);
		this.undos.push(c);
	}
}
