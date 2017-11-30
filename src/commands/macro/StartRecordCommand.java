package commands.macro;

import commands.Command;
import memento.Gardian;

public class StartRecordCommand implements Command {

	protected Gardian gardian;
	
	public StartRecordCommand(Gardian gardian) {
		this.gardian = gardian;
	}
	
	@Override
	public void execute() {
		if(this.gardian.isRecording()) {
			throw new RuntimeException("A macro is already recording ...");
		}
 		this.gardian.startRecord();
	}

}
