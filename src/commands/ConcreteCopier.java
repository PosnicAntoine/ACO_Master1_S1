package commands;

import receiver.Moteur;

public class ConcreteCopier implements Command {

	private final Moteur m;
	
	public ConcreteCopier(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		this.m.copier();
	}

}