package invoker;

import java.awt.Dimension;
import java.io.IOException;

import commands.Command;

public interface Invoker {
	
	public void addCommand(String keyword, Command cmd);
	public String askInsertion() throws IOException;
	public Dimension askSelection() throws IOException;

}
