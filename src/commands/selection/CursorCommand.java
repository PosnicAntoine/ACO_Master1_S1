package commands.selection;

import commands.Command;
import invoker.Invoker;
import receiver.Moteur;

public class CursorCommand implements Command {

	protected final Moteur m;
	protected final Invoker ui;

	public CursorCommand(Moteur m, Invoker ui) {
		this.m = m;
		this.ui = ui;
	}

	public void execute(int cursor) {
		cursor = Math.max(cursor, 0);
		cursor = Math.min(cursor, this.m.getBuffer().length());
		this.m.setDot(cursor);
	}

	@Override
	public void execute() {
		this.execute(this.ui.askValue());
	}
}