package receiver;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class MoteurImpl extends Observable implements Moteur {

	private StringBuffer text;
	private String clipboard;
	private Caret caret;

	@Override
	public String getClipboard() {
		return this.clipboard;
	}

	@Override
	public String getBuffer() {
		return this.text.toString();
	}

	@Override
	public String getSelection() {
		return this.text.substring(this.caret.begin(), this.caret.end());
	}

	public MoteurImpl() {
		this.text = new StringBuffer();
		this.clipboard = "";
		this.caret = new Caret(0);
	}

	@Override
	public void setBuffer(String buffer) {
		this.text.replace(0, this.text.length(), buffer);
		this.setDot(0);
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void notifyObservers() {
		this.notifyObservers(this.text + this.caret.toString() +", <" + this.getSelection() + ">, Clipboard <" + this.clipboard + ">, Bounds [0;"+this.getBuffer().length()+"]");
	}

	@Override
	public void copier() {
		if (this.caret.isEmpty())
			return;

		this.clipboard = this.text.substring(this.caret.begin(), this.caret.end());
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void delete() {
		this.text.delete(this.caret.begin(), this.caret.end());

		this.caret.setDot(this.caret.begin());
		
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void couper() {
		if(this.getSelection().isEmpty()) return;
		this.copier();
		this.delete();
	}

	@Override
	public void coller() {
		this.inserer(this.clipboard);
	}

	@Override
	public void inserer(String s) {
		this.text = this.text.replace(this.caret.begin(), this.caret.end(), s);
		
		this.caret.setDot(this.caret.begin() + s.length());

		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public int getDot() {
		return this.caret.dot;
	}

	@Override
	public int getMark() {
		return this.caret.mark;
	}

	@Override
	public Moteur setDot(int dot) {
		this.caret.setDot(dot);
		return this;
	}

	@Override
	public void moveDot(int dot) {
		this.caret.moveDot(dot); 
	}

	private class Caret {
		public int dot;
		public int mark;

		public Caret(int dot) {
			this.setDot(dot);
		}
		
		public int begin() {
			return Math.min(this.dot, this.mark);
		}
		
		public int end() {
			return Math.max(this.dot, this.mark);
		}

		public boolean isEmpty() {
			return this.dot == this.mark;
		}

		public void setDot(int dot) {
			this.dot = dot;
			this.mark = dot;
		}
		 
		public void moveDot(int dot) {
			this.dot = dot;
		}

		public String toString() {
			return "\nSelection [" + this.dot + ";" + this.mark + "]";
		}
	}
}
