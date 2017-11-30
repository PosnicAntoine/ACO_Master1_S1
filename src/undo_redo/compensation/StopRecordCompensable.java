package undo_redo.compensation;

import commands.macro.StartRecordCommand;
import memento.Gardian;

public class StopRecordCompensable extends StartRecordCommand implements CompensableCommand {

	private CompensableConversation conversation;
	
	public StopRecordCompensable(Gardian gardian, CompensableConversation conversation) {
		super(gardian);
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		super.execute();
		this.conversation.register(new CompensableCommand.Memento(this, null));
	}

	@Override
	public void compensate(Memento memento) {
		this.gardian.startRecord();
	}

	@Override
	public void execute(Memento memento) {
		this.gardian.stopRecord();
	}
}