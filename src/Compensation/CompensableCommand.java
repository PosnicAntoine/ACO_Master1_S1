package Compensation;

import commands.Command;
import memento.AbstractMemento;

public interface CompensableCommand extends Command {

	public void compensate(Memento memento);
	public void execute(Memento memento);

	public final class Memento extends AbstractMemento<CompensableCommand, Object> {

		protected Memento(CompensableCommand c, Object state) {
			super(c, state);
		}
		
	}
}
