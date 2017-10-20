package commands;

import receiver.Moteur;

public class ConcreteColler implements Command {

	private Moteur m;
	
	public ConcreteColler(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		try {
			this.m.couper();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
