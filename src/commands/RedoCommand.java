package commands;

import Compensation.Conversation;

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
