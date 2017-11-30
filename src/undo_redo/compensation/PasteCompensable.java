package undo_redo.compensation;

import memento.Gardian;
import memento.PasteMemento;
import receiver.Moteur;

public class PasteCompensable extends PasteMemento implements CompensableCommand {

	private CompensableConversation conversation;

	public PasteCompensable(Moteur m, Gardian gardian, CompensableConversation conversation) {
		super(m, gardian);
		this.conversation = conversation;
	}

	@Override
	public void execute() {
		State state = new State(this.m.getDot(), this.m.getMark(), this.m.getClipboard(), this.m.getSelection());
		super.execute();
		this.conversation.register(new CompensableCommand.Memento(this, state));
	}

	@Override
	public void compensate(CompensableCommand.Memento memento) {
		State state = (PasteCompensable.State) memento.getState();
		this.m.setDot(state.begin()).moveDot(state.begin() + state.getClipboard().length());
		this.m.inserer(state.getSelection());
	}

	@Override
	public void execute(CompensableCommand.Memento memento) {
		State state = (PasteCompensable.State) memento.getState();
		this.m.setDot(state.begin());
		this.m.inserer(state.getClipboard());
	}

	private class State {

		private int dot, mark;
		private String clipboard, selection;

		private State(int dot, int mark, String clipboard, String selection) {
			this.dot = dot;
			this.mark = mark;
			this.clipboard = clipboard;
			this.selection = selection;
		}

		private int getDot() {
			return this.dot;
		}

		private int getMark() {
			return this.mark;
		}

		private int begin() {
			return Math.min(this.getDot(), this.getMark());
		}

		private String getClipboard() {
			return this.clipboard;
		}

		private String getSelection() {
			return this.selection;
		}
	}
}
