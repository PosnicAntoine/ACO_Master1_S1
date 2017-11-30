package commands.selection;

import commands.Command;
import invoker.Invoker;
import receiver.Moteur;

public class MoveCursorCommand implements Command {

	protected Moteur m;
	protected Invoker invoker;

	public MoveCursorCommand(Moteur m, Invoker invoker) {
		this.m = m;
	}

	public void execute(int move) {
		int dot = this.m.getDot() + move;
		dot = Math.max(dot, 0);
		dot = Math.min(dot, this.m.getBuffer().length());
		this.m.setDot(dot);
	}

	@Override
	public void execute() {
		this.execute(this.invoker.askValue());
	}
}
