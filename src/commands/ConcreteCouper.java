package commands;

import receiver.Moteur;

public class ConcreteCouper implements Command {

	private Moteur m;
	
	public ConcreteCouper(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		m.coller();
	}

}
