package client;


import receiver.MoteurImpl;
import commands.ConcreteColler;
import commands.ConcreteCopier;
import commands.ConcreteCouper;
import commands.ConcreteInserer;
import commands.ConcreteSelectionner;
import invoker.IHM;

public class Editor {

	private static Editor e;
	private static MoteurImpl m;
	
	public static void main(String[] args) {
		e = new Editor();
		m = new MoteurImpl();
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
