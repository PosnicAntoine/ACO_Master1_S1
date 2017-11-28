package commands;

import Memento.Gardian;

public class StartRecordCommand implements Command {

	protected Gardian gardian;
	
	public StartRecordCommand(Gardian gardian) {
		this.gardian = gardian;
	}
	
	@Override
	public void execute() {
		if(this.gardian.isRecording()) {
			System.err.println("A macro is already recording ...");
			return;
		}
 		this.gardian.startRecord();
	}

}
