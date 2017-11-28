package invoker;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import commands.Command;

public class IHM implements Invoker, Observer {

	private HashMap<String, Command> hmCommands;

	private boolean loop;
	private BufferedReader reader;

	public IHM(InputStream is) {
		this.hmCommands = new HashMap<String, Command>();

		this.reader = new BufferedReader(new InputStreamReader(is));
		
	}

	public void beginLoop() {
		this.loop = true;
		try {
			this.loop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void terminateLoop() {
		this.loop = false;
	}
	
	private String listCmd = "";
	
	private void loop() throws IOException {
		
		while (this.loop) {
			
			System.out.print("Enter command ("+listCmd+") > ");

			
			String line = this.reader.readLine();

			
			Command c = this.hmCommands.get(line.toUpperCase());

			if (c != null) {
				c.execute();
			} else {
				System.err.println("Unknown command");
			}
		}
	}

	@Override
	public void addCommand(String key, Command cmd) {
		if (key == null)
			throw new IllegalArgumentException("null key");
		if (cmd == null)
			throw new IllegalArgumentException("null cmd");

		this.hmCommands.put(key.toUpperCase(), cmd);
		
		this.listCmd += (this.listCmd.length() == 0) ? key.toUpperCase() : "/"+key.toUpperCase() ;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}

	@Override
	public String askInsertion() throws IOException {
		System.out.print("Prompt : ");
		return this.reader.readLine();
	}
	
	@Override
	public Dimension askSelection() throws IOException {
		System.out.print("Begin selection :");
		int begin = Integer.valueOf(this.reader.readLine());
		
		System.out.print("End selection :");
		int end = Integer.valueOf(this.reader.readLine());
		
		return new Dimension(begin, end); 
	}

}
