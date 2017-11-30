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
		State state = new State(this.m.getDot(), this.m.getMark(), this.m.getSelection());
		super.execute();
		this.conversation.register(new CompensableCommand.Memento(this, state));
	}

	@Override
	public void compensate(CompensableCommand.Memento memento) {
		State state = (CutCompensable.State) memento.getState();
		this.m.setDot(state.begin());
		this.m.inserer(state.getSelection());
	}

	@Override
	public void execute(CompensableCommand.Memento memento) {
		State state = (CutCompensable.State) memento.getState();
		this.m.setDot(state.getMark()).moveDot(state.getDot());
		this.m.delete();
	}
	
	private class State {
		
		private int dot, mark;
		private String selection;
		
		private State(int dot, int mark, String selection) {
			this.dot = dot;
			this.mark = mark;
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
		
		private String getSelection() {
			return this.selection;
		}
	}
}
