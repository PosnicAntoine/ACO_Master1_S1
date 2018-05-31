/**
 * 
 */
package commands;

/**
 * @author VinYarD
 *
 * <p>Superclass de toute les commandes</p>
 * <p>interface permettant d'éxecuter une commande</p>
 */
public interface Command {

	/**
	 * Execute la commande.
	 */
	public void execute();
}
