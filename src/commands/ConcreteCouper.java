package commands;

import receiver.Moteur;

public class ConcreteCouper implements Command {

	private Moteur m;
	
	public ConcreteCouper(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		try {
			m.coller();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
