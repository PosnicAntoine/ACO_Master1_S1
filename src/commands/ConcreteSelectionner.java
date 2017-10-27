package commands;

import invoker.Invoker;

import java.awt.Dimension;
import java.io.IOException;

import receiver.Moteur;

public class ConcreteSelectionner implements Command {

	private final Moteur m;
	private final Invoker ui;

	public ConcreteSelectionner(Moteur m, Invoker ui) {
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