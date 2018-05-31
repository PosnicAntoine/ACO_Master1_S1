package commands.macro;

import commands.Command;
import memento.Gardian;

public class PlayBackCommand implements Command {

	protected Gardian gardian;

	public PlayBackCommand(Gardian gardian) {
		this.gardian = gardian;
	}

	@Override
	public void execute() {
		if (this.gardian.isRecording()) {
			throw new RuntimeException("A macro is already recording ...");
		}
		
		if(this.gardian.isEmpty()) {
			throw new RuntimeException("There is no macro recorded yet ...");
		}
		
		this.gardian.playBack();
	}
}
