package Compensation;

import commands.PasteCommand;
import receiver.Moteur;

public class PasteCompensable extends PasteCommand implements CompensableCommand {

	private CompensableConversation conversation;

	public PasteCompensable(Moteur m, CompensableConversation conversation) {
		super(m);
		this.conversation = conversation;
	}

	@Override
	public void execute() {
		super.execute();
		this.conversation.register(
				new CompensableCommand.Memento(this, new State(this.m.getBeginSelection(), this.m.getClipboard()))); // TODO
	}

	@Override
	public void compensate(Memento memento) {
		State state = (PasteCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getEndSelection());
		this.m.delete();
	}

	@Override
	public void execute(Memento memento) {
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
