package commands;

import memento.Gardian;

public class PlayBackCommand implements Command {

	protected Gardian gardian;

	public PlayBackCommand(Gardian gardian) {
		this.gardian = gardian;
	}

	@Override
	public void execute() {
		if (this.gardian.isRecording()) {
			System.err.println("A macro is already recording ...");
			return;
		}
		
		if(this.gardian.isEmpty()) {
			System.err.println("There is no macro recorded yet ...");
			return;
		}
		
		this.gardian.playBack();
	}
}
