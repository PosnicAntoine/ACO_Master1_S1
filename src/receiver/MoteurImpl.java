package receiver;

import java.util.Observable;

public class MoteurImpl extends Observable implements Moteur  {
	
	private StringBuffer text;
	private Selection selection;
	private String clipboard;
	
	@Override
	public String getClipboard() {
		return this.clipboard;
	}
	
	@Override
	public String getBuffer(){
		return this.text.toString();
	}
	
	@Override
	public String getSelection(){
		return this.text.substring(this.selection.begin, this.selection.end);
	}	
	
	public MoteurImpl(){
		this.text = new StringBuffer();
		this.clipboard = "";
		try {
			this.selection = new Selection(0,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setBuffer(String buffer) {
		this.text.replace(0, this.text.length(), buffer);
		
//		this.setChanged();
//		this.notifyObservers();
	}
	
	@Override
	public void notifyObservers() {
		this.notifyObservers(this.text + this.selection.toString() + " ; Clipboard [" + this.clipboard + "]");
	}
	
	@Override
	public void copier() {
		if(this.selection.isEmpty()) return;
		
		this.clipboard = this.text.substring(this.selection.begin, this.selection.end);
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void delete() {
		this.text.delete(this.selection.begin, this.selection.end);
		
		try {
			this.selection.setAt(this.selection.begin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void couper() {
		this.copier();
		this.delete();
	}

	@Override
	public void coller() {
		this.inserer(this.clipboard);
	}
	
	@Override
	public void inserer(String s) {
		this.text = this.text.replace(this.selection.begin, this.selection.end, s);
		try {
			this.selection.setAt(this.selection.begin + s.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	

	@Override
	public void selectionner(int begin, int end) {
		
		try {
			this.selection = new Selection(begin, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		this.setChanged();
//		this.notifyObservers();
	}
	
	
	private class Selection {
		public int begin;
		public int end;
		
		public Selection(final int begin, final int end) throws Exception {
			if(begin < 0 || begin > end || end > text.length()){
				throw new Exception("IllegalArgumentSelection");
			}
			this.begin = begin;
			this.end = end;
		}
		
		public boolean isEmpty() {
			return this.size() == 0;
		}
		
		public int size() {
			return Math.subtractExact(this.begin, this.end);
		}
		
		public void setAt(int i) throws Exception{
			if(i < 0 || i > text.length()){
				throw new Exception("IllegalArgumentSelection");
			}
			this.begin = i;
			this.end = i;
		}
		
		public String toString() {
			return "\nSelection ["+begin+";"+end+"]";
		}
	}


	@Override
	public int getBeginSelection() {
		return this.selection.begin;
	}

	@Override
	public int getEndSelection() {
		return this.selection.end;
	}

}
