package Memento;

import commands.CopyCommand;
import receiver.Moteur;

public class CopyMemento extends CopyCommand implements CommandMementoable {

	private Gardian gardian;
	
	public CopyMemento(Moteur m, Gardian gardian) {
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
		super.execute();
	}

}
