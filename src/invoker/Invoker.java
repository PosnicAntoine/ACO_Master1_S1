package invoker;

import commands.Command;

/**
 * @author VinYarD
 * <p>Définis toute les méthodes nécessaire pour l'implémentation d'une IHM pour un éditeur de texte avec des commandes</p>
 */
public interface Invoker {
	
	/**
	 * Ajoute une commande à l'éditeur déclanché par le mot clé keyword.
	 * @param keyword Le mot clé utilisé pour lancer la commande.
	 * @param cmd La commande à éxecuter.
	 */
	public void addCommand(String keyword, Command cmd);
	/**
	 * @return Le text inséré par l'utilisateur
	 * @throws Exception lève une exception en cas de saisie invalide (ne doit pas retourner null).
	 */
	public String askInsertion() throws Exception;
	/**
	 * @return une valeur entière, peut être utilisé pour saisir l'emplacement du curseur, etc.
	 * @throws Exception lève une exception en cas de saisie invalide (ne doit pas retourner null).
	 */
	public int askValue() throws Exception;
}
