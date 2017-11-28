package commands;

import receiver.Moteur;

public class DeleteCommand implements Command {

	protected Moteur m;
	
	public DeleteCommand(Moteur m) {
		this.m = m;
	}

	@Override
	public void execute() {
		this.m.delete();
	}
}
