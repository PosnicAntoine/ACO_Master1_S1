package commands.undo_redo;

import commands.Command;
import undo_redo.Conversation;

public class RedoCommand implements Command {

	private Conversation<?, ?, ?> conversation;
	
	public RedoCommand(Conversation<?, ?, ?> conversation) {
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		this.conversation.redo();
	}
}
