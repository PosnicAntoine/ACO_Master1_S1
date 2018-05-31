package commands.com;

import commands.Command;
import receiver.Moteur;

/**
 * @author VinYarD
 *         <h2>Commande supprimer.</h2>
 *         <p>
 * 		Si éxecuté sans sélection supprime le caractère précédant le curseur. Si aucun caractère ne précéde le curseur, ne fait rien.
 *         </p>
 *         <p>
 * 		Si la taille de la sélection est supérieure à zéro caractère, supprime la sélection.
 *         </p>
 */
public class DeleteCommand implements Command {

	protected Moteur m;

	/**
	 * @param m Le moteur
	 */
	public DeleteCommand(Moteur m) {
		this.m = m;
	}

	/* (non-Javadoc)
	 * @see commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (this.m.getSelection().isEmpty()) {
			this.m.moveDot(Math.max(this.m.getDot() - 1, 0));
		}
		this.m.delete();
	}
}
