package client;


import invoker.IHM;
import receiver.Moteur;
import receiver.MoteurImpl;

import commands.ConcreteColler;
import commands.ConcreteCopier;
import commands.ConcreteCouper;
import commands.ConcreteInserer;
import commands.ConcreteSelectionner;

public class Editor {

	
	public static void main(String[] args) {
		new Editor();
	}
	
	public Editor() {
		Moteur m = new MoteurImpl();
		IHM ihm = new IHM(System.in);
		((MoteurImpl) m).addObserver(ihm);
		//V1
		ihm.addCommand("V", new ConcreteColler(m));
		ihm.addCommand("C", new ConcreteCopier(m));
		ihm.addCommand("X", new ConcreteCouper(m));
		ihm.addCommand("I", new ConcreteInserer(m, ihm));
		ihm.addCommand("S", new ConcreteSelectionner(m, ihm));
		
		
		
		
		
		//V2
		
		//v3
		
		ihm.beginLoop();
	}

}
