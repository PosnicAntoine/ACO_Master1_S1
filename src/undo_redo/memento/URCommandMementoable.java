package undo_redo.memento;

import commands.Command;
import memento.AbstractMemento;

public interface URCommandMementoable extends Command {

	public void undo(Memento memento);
	public void redo(Memento memento);

	public final class Memento extends AbstractMemento<URCommandMementoable, State> {

		protected Memento(URCommandMementoable c, State state) {
			super(c, state);
		}
	}
	
	public class State {
		@Override
		public String toString() {
			return "State [getBefore()=" + getBefore() + ", getAfter()=" + getAfter() + "]";
		}

		private String before;
		private String after;
		
		State(String before, String after) {
			this.before = before;
			this.after = after;
		}
		
		public String getBefore() {
			return this.before;
		}
		
		public String getAfter() {
			return this.after;
		}
	}
}

