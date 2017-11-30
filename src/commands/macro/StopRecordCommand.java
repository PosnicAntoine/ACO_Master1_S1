package commands.macro;

import commands.Command;
import memento.Gardian;

public class StopRecordCommand implements Command {
	
	protected Gardian gardian;

	public StopRecordCommand(Gardian gardian) {
		this.gardian = gardian;
	}

	@Override
	public void execute() {
		if (!this.gardian.isRecording()) {
			throw new RuntimeException("There is no macro to stop ...");
		}
		this.gardian.stopRecord();
	}
}
