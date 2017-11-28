package receiver;

public interface Moteur {
	
	public String getBuffer();
	public String getSelection();
	public int getBeginSelection();
	public int getEndSelection();
	public String getClipboard();
	public void setBuffer(String buffer);
	//V1
	public void copier();
	public void couper();
	public void coller();
	
	public void inserer(String s);
	public void selectionner(int begin, int end);
	public void delete();

	//V2
	
	//V3
}
