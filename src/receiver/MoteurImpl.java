package receiver;

import java.util.Observable;

public class MoteurImpl extends Observable implements Moteur  {
	
	private StringBuffer text;
	private Selection selection = null;

	public MoteurImpl(){
		this.text = new StringBuffer();
	}
	
	@Override
	public void copier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void couper() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coller() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserer(String s) {
		this.text.append(s);
	}

	@Override
	public void selectionner(int begin, int end) {
		// TODO Auto-generated method stub
		
	}
	
	
	private class Selection {
		public final int begin, end;
		
		public Selection(final int begin, final int end) {
			this.begin = begin;
			this.end = end;
		}
	}

}
