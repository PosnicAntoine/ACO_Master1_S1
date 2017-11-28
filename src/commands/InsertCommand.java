package commands;

import java.io.IOException;

import invoker.Invoker;
import receiver.Moteur;

public class InsertCommand implements Command {

	protected final Moteur m;
	protected final Invoker ui;
	
	protected String insertion;
	
	public InsertCommand(Moteur m, Invoker ui) {
		this.m = m;
		this.ui = ui;
	}
	
	@Override
	public void execute() {
		try {
			this.insertion = this.ui.askInsertion();
			this.m.inserer(this.insertion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}