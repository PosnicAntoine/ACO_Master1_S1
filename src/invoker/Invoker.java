package invoker;

import commands.Command;

public interface Invoker {
	
	public void addCommand(String keyword, Command cmd);
	public String askInsertion() throws Exception;
	public int askValue() throws Exception;
}
