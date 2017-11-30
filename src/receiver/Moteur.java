package receiver;

public interface Moteur {
	
	public String getBuffer();
	public String getSelection();
	public int getDot();
	public int getMark();
	public String getClipboard();
	public void setBuffer(String buffer);
	//V1
	public void copier();
	public void couper();
	public void coller();
	
	public Moteur setDot(int dot);
	public void inserer(String s);
	public void moveDot(int dot);
	public void delete();

	//V2
	
	//V3
}
