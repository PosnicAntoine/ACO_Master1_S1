package client;

import invoker.IHM;

public class Editor {
	
	public static void main(String[] args) {
		new Editor();
	}
	
	public Editor() {
		IHM ihm = new IHM(System.in);
		//V1
		ihm.addCommand("C", new ConcreteColler(m));
		ihm.addCommand("C", new ConcreteCopier(m));
		ihm.addCommand("C", new ConcreteCouper(m));
		ihm.addCommand("C", new ConcreteInserer(m));
		ihm.addCommand("C", new ConcreteSelectionner(m));
		
		//V2
		
		//v3
	}

}
