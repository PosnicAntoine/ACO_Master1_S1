package receiver;

public interface Moteur {
	
	//V1
	public void copier() throws Exception;
	public void couper() throws Exception;
	public void coller() throws Exception;
	
	public void inserer(String s) throws Exception;
	public void selectionner(int begin, int end) throws Exception;

	//V2
	
	//V3
}
