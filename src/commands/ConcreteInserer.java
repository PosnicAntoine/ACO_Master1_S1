package commands;

import java.io.IOException;

import invoker.Invoker;
import receiver.Moteur;

public class ConcreteInserer implements Command {

	private final Moteur m;
	private final Invoker ui;
	
	public ConcreteInserer(Moteur m, Invoker ui) {
		this.m = m;
		this.ui = ui;
	}

	
	@Override
	public void execute() {
		try {
			this.m.inserer(this.ui.askInsertion());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}