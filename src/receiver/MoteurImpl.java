package receiver;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class MoteurImpl extends Observable implements Moteur {

	private StringBuffer text;
	private String clipboard;
	private Caret caret;

	/* (non-Javadoc)
	 * @see receiver.Moteur#getClipboard()
	 */
	@Override
	public String getClipboard() {
		return this.clipboard;
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#getBuffer()
	 */
	@Override
	public String getBuffer() {
		return this.text.toString();
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#getSelection()
	 */
	@Override
	public String getSelection() {
		return this.text.substring(this.caret.begin(), this.caret.end());
	}

	/**
	 * Initialise un buffer vide, un presse-papier vide, et place le curseur à l'indice 0.
	 */
	public MoteurImpl() {
		this.text = new StringBuffer();
		this.clipboard = "";
		this.caret = new Caret(0);
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#setBuffer(java.lang.String)
	 */
	@Override
	public void setBuffer(String buffer) {
		this.text.replace(0, this.text.length(), buffer);
		this.setDot(0);
		this.setChanged();
		this.notifyObservers();
	}
 
	/* (non-Javadoc)
	 * @see java.util.Observable#notifyObservers()
	 */
	@Override
	public void notifyObservers() {
		this.notifyObservers(this.text + this.caret.toString() +", <" + 0 + ">, Clipboard <" + this.clipboard + ">, Bounds [0;"+this.getBuffer().length()+"]");
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#copier()
	 */
	@Override
	public void copier() {
		if (this.caret.isEmpty())
			return;

		this.clipboard = this.text.substring(this.caret.begin(), this.caret.end());
		this.setChanged();
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#delete()
	 */
	@Override
	public void delete() {
		this.text.delete(this.caret.begin(), this.caret.end());

		this.caret.setDot(this.caret.begin());
		
		this.setChanged();
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#couper()
	 */
	@Override
	public void couper() {
		if(this.getSelection().isEmpty()) return;
		this.copier();
		this.delete();
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#coller()
	 */
	@Override
	public void coller() {
		this.inserer(this.clipboard);
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#inserer(java.lang.String)
	 */
	@Override
	public void inserer(String s) {
		this.text = this.text.replace(this.caret.begin(), this.caret.end(), s);
		
		this.caret.setDot(this.caret.begin() + s.length());

		this.setChanged();
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see receiver.Moteur#getDot()
	 */
	@Override
	public int getDot() {
		return this.caret.dot;
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#getMark()
	 */
	@Override
	public int getMark() {
		return this.caret.mark;
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#setDot(int)
	 */
	@Override
	public Moteur setDot(int dot) {
		this.caret.setDot(dot);
		this.setChanged();
		this.notifyObservers();
		return this;
	}

	/* (non-Javadoc)
	 * @see receiver.Moteur#moveDot(int)
	 */
	@Override
	public void moveDot(int dot) {
		this.caret.moveDot(dot); 
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * @author VinYarD
	 * <p>Classe encapsulant toute les méthodes et donnée nécessaire à la representation d'un curseur.</p>
	 * @see javax.swing.text.Caret
	 */
	private class Caret {
		/**
		 * La position du curseur.
		 */
		public int dot;
		/**
		 * La position du début de la sélection. Peut être égale à la position du curseur pour une sélection vide.
		 */
		public int mark;

		/**
		 * <p>Initialise un nouveau curseur à la position souhaité.</p>
		 * @param dot Position du curseur.
		 */
		public Caret(int dot) {
			this.setDot(dot);
		}
		
		/**
		 * @return dot si dot < mark, et mark sinon
		 */
		public int begin() {
			return Math.min(this.dot, this.mark);
		}
		
		/**
		 * @return dot si dot > mark, et mark sinon
		 */
		public int end() {
			return Math.max(this.dot, this.mark);
		}

		/**
		 * Retourne vrai si aucune sélection (mark == dot).
		 * @return true si dot == mark
		 */
		public boolean isEmpty() {
			return this.dot == this.mark;
		}

		/**
		 * Place le curseur et la sélection à la position souhaité.
		 * @param dot position du curseur
		 */
		public void setDot(int dot) {
			this.dot = dot;
			this.mark = dot;
		}
		 
		/**
		 * Place le curseur à la position souhaité et laisse le début de la sélection à la position du dernier setDot(..) appliqué.
		 * @param dot position du curseur.
		 */
		public void moveDot(int dot) {
			this.dot = dot;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "\nSelection [" + this.dot + ";" + this.mark + "]";
		}
	}
}
