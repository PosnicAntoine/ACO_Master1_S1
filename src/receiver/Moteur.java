package receiver;

public interface Moteur {
	
	public String getBuffer();
	public String getSelection();
	public String getClipboard();
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
