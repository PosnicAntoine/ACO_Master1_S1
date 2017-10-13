package invoker;

import commands.Command;

public interface Invoker {
	
	public void addCommand(String keyword, Command cmd);

}
