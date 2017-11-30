package commands.com;

import commands.Command;
import receiver.Moteur;

public class DeleteCommand implements Command {

	protected Moteur m;
	
	public DeleteCommand(Moteur m) {
		this.m = m;
	}

	@Override
	public void execute() {
		if(this.m.getSelection().isEmpty()) {
			this.m.moveDot(Math.max(this.m.getDot() - 1, 0));
		}
		this.m.delete();
	}
}
