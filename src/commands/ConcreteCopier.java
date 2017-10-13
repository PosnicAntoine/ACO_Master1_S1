package commands;

import receiver.Moteur;

public class ConcreteCopier implements Command {

	private Moteur m;
	
	public ConcreteCopier(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		m.coller();
	}

}