package receiver;

/**
 * @author VinYarD
 * <p>Interface moteur destiné à éditer un texte, sauvegarder un presse-papier et déplacer le curseur ou une sélection.</p>
 */
public interface Moteur {
	
	/**
	 * Retourne le texte actuel du moteur.
	 * @return le buffer (texte en cours d'édition).
	 */
	public String getBuffer();
	/**
	 * Retourne une chaîne de caractère représentant la sélection, peut être vide.
	 * @return la sélection si supérieure à zéro caractère, une chaîne vide sinon.
	 */
	public String getSelection();
	/**
	 * Retourne la position du curseur dans le buffeur.
	 * @return position du curseur dans le buffer.
	 */
	public int getDot();
	/**
	 * Retourne la position de la sélection dans le buffer (identique au curseur si aucune sélection).
	 * @return position du début de la sélection dans le buffer..
	 */
	public int getMark();
	/**
	 * Retourne l'état actuel du presse-papier.
	 * @return le buffer contenant le presse papier.
	 */
	public String getClipboard();
	/**
	 * Remplace le texte actuel du moteur par le texte souhaité.
	 * @param buffer Le texte à éditer
	 */
	public void setBuffer(String buffer);
	//V1
	/**
	 * Copie la sélection dans le presse-papier.
	 */
	public void copier();
	/**
	 * Copie la sélection dans le presse-papier puis la supprime.
	 */
	public void couper();
	/**
	 * Colle le presse papier à la position actuelle du curseur.
	 */
	public void coller();
	
	/**
	 * Place le curseur à la position souhaité.
	 * @param dot La position du curseur.
	 * @return Le moteur, permet d'enchaîner les méthodes setDot(..).moveDot(..) pour sélectionner rapidement du texte.
	 */
	public Moteur setDot(int dot);
	/**
	 * Insère du texte à l'endroit où est positionné le curseur.
	 * @param s Le texte à insérer.
	 */
	public void inserer(String s);
	/**
	 * Place le curseur à la position souhaité mais garde le début de la sélection au même emplacement qu'à la dernière utilisation de setDot.
	 * @param dot La position du curseur
	 */
	public void moveDot(int dot);
	/**
	 * Supprime la sélection, supprime le précédent caractère si la taille de la sélection est égale à zéro.
	 */
	public void delete();

	//V2
	
	//V3
}
