package Memento;

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
		try {
			String state = this.ui.askInsertion();
			this.m.inserer(state);
			this.gardian.register(new Memento(this, state));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void play(Memento memento) {
		if(!(memento.getState() instanceof String)) {
			throw new IllegalArgumentException("State of InsertCommand must be of type String");
		}
		
		this.m.inserer((String) memento.getState());
	}

}
