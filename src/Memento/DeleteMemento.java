package Memento;

import commands.DeleteCommand;
import receiver.Moteur;

public class DeleteMemento extends DeleteCommand implements CommandMementoable {

	private Gardian gardian;
	
	public DeleteMemento(Moteur m, Gardian gardian) {
		super(m);
		this.gardian = gardian;
	}

	@Override
	public void execute() {
		super.execute();
		this.gardian.register(new Memento(this, null));
	}

	@Override
	public void play(Memento memento) {
		// nothing to restore ...
		super.execute();
	}
}
