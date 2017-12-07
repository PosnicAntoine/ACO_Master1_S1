package invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import commands.Command;

/**
 * @author VinYarD
 * <p>Une interface permettant d'éditer du texte en saisissant le texte et le nom des commandes à éxecuté dans un InputStream.</p>
 */
@SuppressWarnings("deprecation")
public class IHM implements Invoker, Observer {

	private HashMap<String, Command> hmCommands;

	private boolean loop;
	private BufferedReader reader;

	/**
	 * Instancie une IHM qui lis les lignes ou entrée de l'inputstream fournis.
	 * @param is Le stream qui permet de lire le nom des commandes à éxécuter ainsi que les potentiels paramètres des commandes..
	 */
	public IHM(InputStream is) {
		this.hmCommands = new HashMap<String, Command>();

		this.reader = new BufferedReader(new InputStreamReader(is));
	}

	/**
	 * Ordonne le démarrage de l'IHM, lecture de l'inputstream jusqu'à l'appel de terminateLoop.
	 */
	public void beginLoop() {
		this.loop = true;
		try {
			this.loop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ordonne la de la lecture de l'inputstream.
	 */
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
				System.err.println("Unknown command\n");
			}
		}
	}

	/* (non-Javadoc)
	 * @see invoker.Invoker#addCommand(java.lang.String, commands.Command)
	 */
	@Override
	public void addCommand(String key, Command cmd) {
		if (key == null)
			throw new IllegalArgumentException("null key");
		if (cmd == null)
			throw new IllegalArgumentException("null cmd");

		this.hmCommands.put(key.toUpperCase(), cmd);
		
		this.listCmd += (this.listCmd.length() == 0) ? key.toUpperCase() : "/"+key.toUpperCase() ;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}

	/* (non-Javadoc)
	 * @see invoker.Invoker#askInsertion()
	 */
	@Override
	public String askInsertion() {
		System.out.print("Prompt : ");
		try {
			return this.reader.readLine();
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/* (non-Javadoc)
	 * @see invoker.Invoker#askValue()
	 */
	@Override
	public int askValue() {
		System.out.print("prompt >");
		try {
			return Integer.valueOf(this.reader.readLine());
		} catch (NumberFormatException | IOException e) {
			throw new IllegalArgumentException();
		}
	}

}
