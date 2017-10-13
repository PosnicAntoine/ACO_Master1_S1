package commands;

import receiver.Moteur;

public class ConcreteSelectionner implements Command {

	private Moteur m;

	public ConcreteSelectionner(Moteur m) {
		this.m = m;
	}

	@Override
	public void execute() {
		m.coller();
	}

}