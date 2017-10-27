package receiver;

import java.util.Observable;

public class MoteurImpl extends Observable implements Moteur  {
	
	private StringBuffer text;
	private Selection selection;
	private String clipboard;
	
	public StringBuffer getText(){
		return this.text;
	}
	
	public Selection getSelection(){
		return this.selection;
	}	
	
	public MoteurImpl(){
		this.text = new StringBuffer();
		try {
			this.selection = new Selection(0,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void notifyObservers() {
		this.notifyObservers(this.text + this.selection.toString());
	}
	
	@Override
	public void copier() {
		int begin = this.selection.begin;
		int end = this.selection.end;
		
		if(begin<end){
			this.clipboard = this.text.substring(begin, end);
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void couper() {
		int begin = this.selection.begin;
		int end = this.selection.end;
		
		if(begin < end){
			this.clipboard = this.text.substring(begin, end);
			this.text = this.text.delete(begin, end);
			
			try {
				this.selection.setAt(begin);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void coller() {
		int begin = this.selection.begin;
		int end = this.selection.end;
		
		this.text = this.text.delete(begin, end);
		this.text = this.text.insert(begin, this.clipboard);
		
		try {
			this.selection.setAt(begin + clipboard.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void inserer(String s) {
		int begin = this.selection.begin;
		int end = this.selection.end;

		this.text = this.text.delete(begin, end);
		this.text = this.text.insert(begin, s);
		
		try {
			this.selection.setAt(begin + s.length());
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
		
		
		this.setChanged();
		this.notifyObservers();
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
	
	
}
