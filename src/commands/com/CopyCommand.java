package commands.com;

import commands.Command;
import receiver.Moteur;

/**
 * @author VinYarD
 * <h2>Commande copier.</h2>
 * <p>Si éxecuté sans sélection, garde le presse-papier inchangé par rapport à l'éxecution d'une précédente commande copier ou couper</p>
 * <p>Si la taille de la sélection est supérieure à zéro caractères, copie la sélection dans le presse-papier.</p>
 */
public class CopyCommand implements Command {

	protected final Moteur m;
	
	/**
	 * @param m Le moteur
	 */
	public CopyCommand(Moteur m) {
		this.m = m;
	}
	
	/* (non-Javadoc)
	 * @see commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.m.copier();
	}
}