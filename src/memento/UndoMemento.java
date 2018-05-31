package memento;

import commands.undo_redo.UndoCommand;
import undo_redo.Conversation;

public class UndoMemento extends UndoCommand implements CommandMementoable {

	private Gardian gardian;
	
	public UndoMemento(Conversation<?, ?, ?> conversation, Gardian gardian) {
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