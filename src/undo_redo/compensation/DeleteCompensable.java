package undo_redo.compensation;

import memento.DeleteMemento;
import memento.Gardian;
import receiver.Moteur;

public class DeleteCompensable extends DeleteMemento implements CompensableCommand {

	private CompensableConversation conversation;
	
	public DeleteCompensable(Moteur m, Gardian gardian, CompensableConversation conversation) {
		super(m, gardian);
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		this.conversation.register(new CompensableCommand.Memento(this, new State(this.m.getBeginSelection(), this.m.getSelection()))); // TODO
		super.execute();
	}

	@Override
	public void compensate(CompensableCommand.Memento memento) {
		State state = (DeleteCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getBeginSelection());
		this.m.inserer(state.getSelection());
	}

	@Override
	public void execute(CompensableCommand.Memento memento) {
		State state = (DeleteCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getEndSelection());
		this.m.delete();
	}
	
	private class State {

		private int beginSelection;
		private String selection;

		private State(int beginSelection, String selection) {
			this.beginSelection = beginSelection;
			this.selection = selection;
		}

		private String getSelection() {
			return this.selection;
		}

		private int getBeginSelection() {
			return this.beginSelection;
		}

		private int getEndSelection() {
			return this.getBeginSelection() + this.getOffset();
		}

		private int getOffset() {
			return this.getSelection().length();
		}
	}

}
