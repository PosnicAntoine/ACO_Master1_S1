package commands.com;

import commands.Command;
import receiver.Moteur;

public class PasteCommand implements Command {

	protected final Moteur m;

	public PasteCommand(Moteur m) {
		this.m = m;
	}

	@Override
	public void execute() {
		this.m.coller();
	}
}
