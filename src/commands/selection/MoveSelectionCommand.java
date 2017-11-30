package commands.selection;

import commands.Command;
import invoker.Invoker;
import receiver.Moteur;

public class MoveSelectionCommand implements Command {

	protected Moteur m;
	protected Invoker ui;

	public MoveSelectionCommand(Moteur m, Invoker ui) {
		this.m = m;
		this.ui = ui;
	}

	public void execute(int move) {
		int dot = this.m.getDot() + move;
		dot = Math.max(dot, 0);
		dot = Math.min(dot, this.m.getBuffer().length());
		this.m.moveDot(dot);
	}

	@Override
	public void execute() {
		this.execute(this.ui.askValue());
	}

}
