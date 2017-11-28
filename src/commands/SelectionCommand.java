package commands;

import invoker.Invoker;

import java.awt.Dimension;
import java.io.IOException;

import receiver.Moteur;

public class SelectionCommand implements Command {

	protected final Moteur m;
	protected final Invoker ui;

	public SelectionCommand(Moteur m, Invoker ui) {
		this.m = m;
		this.ui = ui;
	}

	@Override
	public void execute() {
		try {
			Dimension d = this.ui.askSelection();
			this.m.selectionner(d.width, d.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}