package commands;

import receiver.Moteur;

public class CopyCommand implements Command {

	protected final Moteur m;
	
	public CopyCommand(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		this.m.copier();
	}

}