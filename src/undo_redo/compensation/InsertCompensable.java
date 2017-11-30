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
	public void execute(String insertion) {
		State state = new State(this.m.getDot(), this.m.getMark(), insertion, this.m.getSelection());
		super.execute(insertion);
		this.conversation.register(new CompensableCommand.Memento(this, state));
	}

	@Override
	public void compensate(CompensableCommand.Memento memento) {
		State state = (InsertCompensable.State) memento.getState();
		this.m.setDot(state.begin()).moveDot(state.begin() + state.getInsertion().length());
		this.m.inserer(state.getSelection());
	}

	@Override
	public void execute(CompensableCommand.Memento memento) {
		State state = (InsertCompensable.State) memento.getState();
		this.m.setDot(state.getMark()).moveDot(state.getDot());
		;
		this.m.inserer(state.getInsertion());
	}

	private class State {

		private int dot, mark;
		private String insertion, selection;

		private State(int dot, int mark, String insertion, String selection) {
			this.dot = dot;
			this.mark = mark;
			this.insertion = insertion;
			this.selection = selection;
		}

		private int getDot() {
			return this.dot;
		}

		private int getMark() {
			return this.mark;
		}

		private String getSelection() {
			return this.selection;
		}

		private int begin() {
			return Math.min(this.getDot(), this.getMark());
		}

		private String getInsertion() {
			return this.insertion;
		}
	}
}
