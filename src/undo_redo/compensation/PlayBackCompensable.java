package undo_redo.compensation;

import commands.PlayBackCommand;
import memento.Gardian;
import receiver.Moteur;

public class PlayBackCompensable extends PlayBackCommand implements CompensableCommand {

	private CompensableConversation conversation;
	private Moteur m;
	
	public PlayBackCompensable(Gardian gardian, Moteur m, CompensableConversation conversation) {
		super(gardian);
		this.m = m;
		this.conversation = conversation;
	}
	
	@Override
	public void execute() {
		String before = this.m.getBuffer();
		super.execute();
		String after = this.m.getBuffer();
		State state = new State(before, after);
		this.conversation.register(new CompensableCommand.Memento(this, state));
	}

	@Override
	public void compensate(Memento memento) {
		State state = (PlayBackCompensable.State) memento.getState();
		this.m.setBuffer(state.getBefore());
	}

	@Override
	public void execute(Memento memento) {
		State state = (PlayBackCompensable.State) memento.getState();
		this.m.setBuffer(state.getAfter());
	}
	
	private class State {
		@Override
		public String toString() {
			return "State [getBefore()=" + getBefore() + ", getAfter()=" + getAfter() + "]";
		}

		private String before;
		private String after;
		
		private State(String before, String after) {
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
