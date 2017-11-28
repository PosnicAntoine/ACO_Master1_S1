package commands;

import receiver.Moteur;

public class CutCommand implements Command {

	protected final Moteur m;
	
	public CutCommand(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		this.m.couper();
	}
}
