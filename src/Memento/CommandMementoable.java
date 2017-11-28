package Memento;

/*
 * The creator
 */
public interface CommandMementoable {
	
	/*
	 * C'est comme le pattern commande des JComponent
	 * On sauvegarde l'ActionEvent qui a déclanché la commande
	 * En on la rééxecuter avec la meme action.
	 */
	public void play(Memento memento);
	//public Memento save();
	
	public final class Memento {
		private final Object state;
		private final CommandMementoable command;
		
		/*
		 * L'état est entré dans le constructeur,
		 * l'état a une portée privée, ce qui assure bien
		 * le status immutuable du memento !
		 * 
		 * Ici le memento n'est pas un etat d'un document
		 * mais l'état d'une commande
		 */
		protected Memento(CommandMementoable command, Object state) {
			this.command = command;
			this.state = state;
		}
		
		protected final Object getState() {
			return this.state;
		}
		
		protected final CommandMementoable getCommand() {
			return this.command;
		}
	}
	
}
