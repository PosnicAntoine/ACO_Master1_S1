package commands;

import java.io.IOException;

import invoker.Invoker;
import receiver.Moteur;

public class InsertCommand implements Command {

	protected final Moteur m;
	protected final Invoker ui;
	
	public InsertCommand(Moteur m, Invoker ui) {
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