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
		super.execute();
		this.conversation.register(
				new CompensableCommand.Memento(this, new State(this.m.getBeginSelection(), this.m.getClipboard()))); // TODO
	}

	@Override
	public void compensate(CompensableCommand.Memento memento) {
		State state = (PasteCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getEndSelection());
		this.m.delete();
	}

	@Override
	public void execute(CompensableCommand.Memento memento) {
		State state = (PasteCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getBeginSelection());
		this.m.inserer(state.getClipboard());
	}

	private class State {

		private int endSelection;
		private String clipboard;

		private State(int endSelection, String clipboard) {
			this.endSelection = endSelection;
			this.clipboard = clipboard;
		}

		private String getClipboard() {
			return this.clipboard;
		}

		private int getBeginSelection() {
			return this.getEndSelection() - this.getOffset();
		}

		private int getEndSelection() {
			return this.endSelection;
		}

		private int getOffset() {
			return this.getClipboard().length();
		}
	}
}
