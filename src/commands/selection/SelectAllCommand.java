package commands.selection;

import commands.Command;
import receiver.Moteur;

public class SelectAllCommand implements Command {

	protected Moteur m;
	
	public SelectAllCommand(Moteur m) {
		this.m = m;
	}
	
	@Override
	public void execute() {
		this.m.setDot(0);
		this.m.moveDot(this.m.getBuffer().length());
	}
}
