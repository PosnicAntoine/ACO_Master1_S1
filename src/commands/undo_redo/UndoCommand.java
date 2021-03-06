package commands.undo_redo;

import commands.Command;
import undo_redo.Conversation;

public class UndoCommand implements Command {

	private Conversation<?, ?, ?> conversation;
	
	public UndoCommand(Conversation<?, ?, ?> conversation) {
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		this.conversation.undo();
	}
}
