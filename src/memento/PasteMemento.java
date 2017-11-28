package memento;

import commands.PasteCommand;
import receiver.Moteur;

public class PasteMemento extends PasteCommand implements CommandMementoable {

	private Gardian gardian;
	
	public PasteMemento(Moteur m, Gardian gardian) {
		super(m);
		this.gardian = gardian;
	}
	
	@Override
	public void execute() {
		super.execute();
		this.gardian.register(new CommandMementoable.Memento(this, null));
	}

	@Override
	public void play(CommandMementoable.Memento memento) {
		super.execute();
	}
}
