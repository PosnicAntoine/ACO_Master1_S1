package memento;

import commands.com.CopyCommand;
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
		this.gardian.register(new CommandMementoable.Memento(this, null));
	}

	@Override
	public void play(CommandMementoable.Memento memento) {
		super.execute();
	}

}
