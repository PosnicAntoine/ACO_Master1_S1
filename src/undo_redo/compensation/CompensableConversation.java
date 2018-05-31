package undo_redo.compensation;

import undo_redo.AbstractConversation;

public class CompensableConversation extends AbstractConversation<CompensableCommand.Memento, CompensableCommand, Object> {

	public void register(CompensableCommand.Memento memento) {
		this.undos.push(memento);
		this.redos.clear();
	}
	
	public void undo() {
		if(this.undos.isEmpty()) return;
		CompensableCommand.Memento c = this.undos.pop();
		c.getSource().compensate(c);
		this.redos.push(c);
	}

	public void redo() {
		if(this.redos.isEmpty()) return;
		CompensableCommand.Memento c = this.redos.pop();
		c.getSource().execute(c);
		this.undos.push(c);
	}
}
