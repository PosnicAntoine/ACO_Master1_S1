package commands;

import receiver.Moteur;

public class ConcreteInserer implements Command {

	private Moteur m;

	public ConcreteInserer(Moteur m) {
		this.m = m;
	}

	@Override
	public void execute() {
		m.coller();
	}

}