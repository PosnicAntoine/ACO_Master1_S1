package commands;

import memento.Gardian;

public class StopRecordCommand implements Command {
	
	protected Gardian gardian;

	public StopRecordCommand(Gardian gardian) {
		this.gardian = gardian;
	}

	@Override
	public void execute() {
		if (!this.gardian.isRecording()) {
			System.err.println("There is no macro to stop ...");
			return;
		}
		this.gardian.stopRecord();
	}
}
