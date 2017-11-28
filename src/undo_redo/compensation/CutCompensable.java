package undo_redo.compensation;

import memento.CutMemento;
import memento.Gardian;
import receiver.Moteur;

public class CutCompensable extends CutMemento implements CompensableCommand {

	private CompensableConversation conversation;
	
	public CutCompensable(Moteur m, Gardian gardian, CompensableConversation conversation) {
		super(m, gardian);
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		super.execute();
		this.conversation.register(new CompensableCommand.Memento(this, new State(this.m.getBeginSelection(), this.m.getClipboard())));
	}

	@Override
	public void compensate(CompensableCommand.Memento memento) {
		State state = (CutCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getBeginSelection());
		this.m.inserer(state.getClipboard());
	}

	@Override
	public void execute(CompensableCommand.Memento memento) {
		State state = (CutCompensable.State) memento.getState();
		this.m.selectionner(state.beginSelection, state.getEndSelection());
		this.m.delete();
	}
	
	private class State {
		
		private int beginSelection;
		private String clipboard;
		
		private State(int beginSelection, String clipboard) {
			this.beginSelection = beginSelection;
			this.clipboard = clipboard;
		}
		
		private String getClipboard() {
			return this.clipboard;
		}
		
		private int getBeginSelection() {
			return this.beginSelection;
		}
		
		private int getEndSelection() {
			return this.getBeginSelection() + this.getOffset();
		}
		
		private int getOffset() {
			return this.getClipboard().length();
		}
	}
}
