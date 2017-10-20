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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	public void copier() throws Exception {
		// TODO Auto-generated method stub
		int begin = selection.begin;
		int end = selection.end;
		
		if(begin<end){
			this.clipboard = text.substring(begin, end);
		}
	}

	
	
	
	@Override
	public void couper() {
		// TODO Auto-generated method stub
		int begin = selection.begin;
		int end = selection.end;
		
		if(begin < end){
			this.clipboard = text.substring(begin, end);
			this.text = text.delete(begin, end);
			
			
			
			try {
				selection.setAt(begin);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

	
	
	
	
	
	@Override
	public void coller() {
		// TODO Auto-generated method stub
		int begin = selection.begin;
		int end = selection.end;
		
		this.text = text.delete(begin, end);
		this.text = text.insert(begin, clipboard);
		
		try {
			this.selection.setAt(begin + clipboard.length());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	
	
	@Override
	public void inserer(String s) {
	}

	
	
	
	
	
	@Override
	public void selectionner(int begin, int end) throws Exception {
		
		this.selection = new Selection(begin, end);
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
	}

}
