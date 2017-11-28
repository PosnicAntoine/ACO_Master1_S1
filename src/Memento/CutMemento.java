package Memento;

import commands.CutCommand;
import receiver.Moteur;

public class CutMemento extends CutCommand implements CommandMementoable {

	private Gardian gardian;
	
	public CutMemento(Moteur m, Gardian gardian) {
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
