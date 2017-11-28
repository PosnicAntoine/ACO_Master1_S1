package undo_redo.compensation;

import invoker.Invoker;
import memento.Gardian;
import memento.InsertMemento;
import receiver.Moteur;

public class InsertCompensable extends InsertMemento implements CompensableCommand {

	private CompensableConversation conversation;
	
	public InsertCompensable(Moteur m, Invoker ui, Gardian gardian, CompensableConversation conversation) {
		super(m, ui, gardian);
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		super.execute();
		State state = new State(this.m.getBeginSelection(), this.insertion);
		this.conversation.register(
				new CompensableCommand.Memento(this, state)); // TODO
	}

	@Override
	public void compensate(CompensableCommand.Memento memento) {
		State state = (InsertCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getEndSelection());
		this.m.delete();
	}

	@Override
	public void execute(CompensableCommand.Memento memento) {
		State state = (InsertCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getBeginSelection());
		this.m.inserer(state.getSelection());
	}
	
	private class State {

		@Override
		public String toString() {
			return "State [getSelection()=" + getSelection() + ", getBeginSelection()=" + getBeginSelection()
					+ ", getEndSelection()=" + getEndSelection() + ", getOffset()=" + getOffset() + "]";
		}

		private int endSelection;
		private String selection;

		private State(int endSelection, String selection) {
			this.endSelection = endSelection;
			this.selection = selection;
		}

		private String getSelection() {
			return this.selection;
		}

		private int getBeginSelection() {
			return this.getEndSelection() - this.getOffset();
		}

		private int getEndSelection() {
			return this.endSelection;
		}

		private int getOffset() {
			return this.getSelection().length();
		}
	}

}
