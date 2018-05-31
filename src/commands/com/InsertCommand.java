package commands.com;

import commands.Command;
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

	public void execute(String insertion) {
		this.insertion = insertion;
		this.m.inserer(this.insertion);
	}

	@Override
	public void execute() {
		try {
			this.execute(this.ui.askInsertion());
		} catch (Exception e) {
		}
	}
}