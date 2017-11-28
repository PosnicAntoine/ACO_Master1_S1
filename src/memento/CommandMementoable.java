package memento;

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
	
	public final class Memento extends AbstractMemento<CommandMementoable, Object>{

		/*
		 * L'état est entré dans le constructeur,
		 * l'état a une portée privée, ce qui assure bien
		 * le status immutuable du memento !
		 * 
		 * Ici le memento n'est pas un etat d'un document
		 * mais l'état d'une commande
		 */
		protected Memento(CommandMementoable c, Object state) {
			super(c, state);
		}
	
	}
	
}
