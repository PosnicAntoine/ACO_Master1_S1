package memento;

import commands.selection.SelectAllCommand;
import receiver.Moteur;

public class SelectAllMemento extends SelectAllCommand implements CommandMementoable {

	private Gardian gardian;
	
	public SelectAllMemento(Moteur m, Gardian gardian) {
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
