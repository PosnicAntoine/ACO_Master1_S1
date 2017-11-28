package memento;

import commands.RedoCommand;
import undo_redo.Conversation;

public class RedoMemento extends RedoCommand implements CommandMementoable {

	private Gardian gardian;
	
	public RedoMemento(Conversation<?, ?, ?> conversation, Gardian gardian) {
		super(conversation);
		this.gardian = gardian;
	}
	
	@Override
	public void execute() {
		super.execute();
		this.gardian.register(new CommandMementoable.Memento(this, null));
	}

	@Override
	public void play(Memento memento) {
		super.execute();
	}

}
