package memento;

import java.io.IOException;

import commands.InsertCommand;
import invoker.Invoker;
import receiver.Moteur;

public class InsertMemento extends InsertCommand implements CommandMementoable {

	private Gardian gardian;

	public InsertMemento(Moteur m, Invoker ui, Gardian gardian) {
		super(m, ui);
		this.gardian = gardian;
	}

	@Override
	public void execute() {
		super.execute();
		this.gardian.register(new CommandMementoable.Memento(this, this.insertion));
	}

	@Override
	public void play(CommandMementoable.Memento memento) {
		if (!(memento.getState() instanceof String)) {
			throw new IllegalArgumentException("State of InsertCommand must be of type String");
		}

		this.m.inserer((String) memento.getState());
	}
}
