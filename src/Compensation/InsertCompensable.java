package Compensation;

import commands.InsertCommand;
import invoker.Invoker;
import receiver.Moteur;

public class InsertCompensable extends InsertCommand implements CompensableCommand {

	private CompensableConversation conversation;
	
	public InsertCompensable(Moteur m, Invoker ui, CompensableConversation conversation) {
		super(m, ui);
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		super.execute();
		this.conversation.register(
				new CompensableCommand.Memento(this, new State(this.m.getBeginSelection(), this.m.getSelection()))); // TODO
	}

	@Override
	public void compensate(Memento memento) {
		State state = (InsertCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getEndSelection());
		this.m.delete();
	}

	@Override
	public void execute(Memento memento) {
		State state = (InsertCompensable.State) memento.getState();
		this.m.selectionner(state.getBeginSelection(), state.getBeginSelection());
		this.m.inserer(state.getSelection());
	}
	
	private class State {

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
